<template>
  <div>
    <meta
      http-equiv="Content-Security-Policy"
      content="upgrade-insecure-requests"
    />
    <div id="map" style="height: 1000px" class="map">
      <div id="selectedApt_wrap" style="display: block">
        <Plan />
      </div>
    </div>
  </div>
  <div id="webrtc-wrapper">
    <WebRTC />
  </div>
</template>

<script>
import { reactive } from "@vue/reactivity";
import { onMounted } from "vue";
import WebRTC from "@/views/WebRTC.vue";
import Plan from "@/components/Plan.vue";
import axios from "axios";
export default {
  name: "Map",
  components: {
    WebRTC,
    Plan,
  },
  setup() {
    const sido_json = require("../assets/sido.json");
    const sigungu_json = require("../assets/sigungu.json");
    const state = reactive({
      num: null,
      map: null,
      infowindow: null,
      listFlag: false,
      areas: [],
      customOverlay: [], //overlay, tourSpotId, tourSpotName
      sido_polygon: [],
      sigungu_polygon: [],
      tourspot: [],
      tourspot_top100: [],
    });
    onMounted(() => {
      window.kakao && window.kakao.maps ? initMap() : addKakaoMapScript();
      const target = document.getElementById("selectedApt_wrap");
      target.onmouseenter = function () {
        state.listFlag = true;
      };
      target.onmouseleave = function () {
        state.listFlag = false;
      };
    });
    const initMap = () => {
      var container = document.getElementById("map"); //지도를 담을 영역의 DOM 레퍼런스
      var options = {
        //지도를 생성할 때 필요한 기본 옵션
        center: new kakao.maps.LatLng(37.565400364768095, 126.97723780998845), //지도의 중심좌표.
        level: 13, //지도의 레벨(확대, 축소 정도)
      };
      state.map = new kakao.maps.Map(container, options); //지도 생성 및 객체 리
      kakao.maps.event.addListener(
        state.map,
        "rightclick",
        function (mouseEvent) {
          if (state.map.getLevel() < 10) {
            state.map.setLevel(10);
          } else {
            state.map.setLevel(13);
          }
          var mousePoint = mouseEvent.latLng;
          state.map.setCenter(
            new kakao.maps.LatLng(mousePoint.getLat(), mousePoint.getLng())
          );
        }
      );
      kakao.maps.event.addListener(state.map, "zoom_changed", function () {
        if (state.map.getLevel() <= 10) {
          // 10->9 9->8
          for (var i = 0; i < state.sido_polygon.length; i++) {
            state.sido_polygon[i][2].setMap(null);
          }
        } else {
          state.num = 0;
          for (i = 0; i < state.sigungu_polygon.length; i++) {
            state.sigungu_polygon[i][2].setMap(null);
          }
          for (i = 0; i < state.tourspot_top100.length; i++) {
            state.tourspot_top100[i][0].setMap(null);
          }
          for (i = 0; i < state.tourspot.length; i++) {
            state.tourspot[i][0].setMap(null);
          }
          for (i = 0; i < state.sido_polygon.length; i++) {
            state.sido_polygon[i][2].setMap(state.map);
          }
        }
      });
      axios({
        method: "get",
        url: "http://i6a105.p.ssafy.io:8081/tourspot",
      }).then((res) => {
        makeMarker(res.data.tourSpotList);
      });
      makePolygonDepth1(sido_json);
      makePolygonDepth2(sigungu_json);
    };
    const addKakaoMapScript = () => {
      const script = document.createElement("script");
      /* global kakao */
      script.onload = () => kakao.maps.load(initMap);
      script.src =
        "https://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=d8a883df5abd036e1c9ac7857a408e64";
      document.head.appendChild(script);
    };
    const makeMarker = (tourSpotList) => {
      for (var i = 0; i < tourSpotList.length; i++) {
        var position = new kakao.maps.LatLng(
          tourSpotList[i].tourSpotLatitude,
          tourSpotList[i].tourSpotLongitude
        );

        var overlay_info = document.createElement("div");
        overlay_info.className = "overlay_info";

        var title = document.createElement("div");
        title.className = "title";
        title.innerText = tourSpotList[i].tourSpotName;
        overlay_info.appendChild(title);

        var close = document.createElement("div");
        close.onclick = function () {
          state.customOverlay[0].setMap(null);
        };
        close.className = "close";
        close.setAttribute("title", "닫기");
        title.appendChild(close);

        var desc = document.createElement("div");
        desc.className = "desc";
        overlay_info.appendChild(desc);

        var address = document.createElement("div");
        address.className = "address";
        address.innerText = "주소 : " + tourSpotList[i].tourSpotAddress;
        desc.appendChild(address);

        if (tourSpotList[i].tourSpotHoliday) {
          var holiday = document.createElement("div");
          holiday.className = "holiday";
          holiday.innerText = "쉬는날 : " + tourSpotList[i].tourSpotHoliday;
          desc.appendChild(holiday);
        }

        if (tourSpotList[i].tourSpotTime) {
          var time = document.createElement("div");
          time.className = "time";
          time.innerText = "이용시간 : " + tourSpotList[i].tourSpotTime;
          desc.appendChild(time);
        }

        if (tourSpotList[i].tourSpotParking) {
          var parking = document.createElement("div");
          parking.className = "parking";
          parking.innerText = "주차시설 : " + tourSpotList[i].tourSpotParking;
          desc.appendChild(parking);
        }

        var bottom = document.createElement("div");
        bottom.className = "bottom";
        desc.appendChild(bottom);

        var selectbar = document.createElement("select");
        selectbar.className = "selectbar";

        for (var day = 1; day <= 4; day++) {
          var option = document.createElement("option");
          option.value = day;
          option.innerHTML = day;
          selectbar.appendChild(option);
        }
        selectbar.onchange = function () {
          console.log("asdasd");
          console.log(selectbar.value);
          selectbar.value = option.value;
        };
        bottom.appendChild(selectbar);

        var register = document.createElement("div");
        register.className = "register";
        register.onclick = function () {
          console.log(selectbar.value);
        };
        register.setAttribute("title", "추가하기");
        bottom.appendChild(register);

        displayMarker(
          position,
          overlay_info,
          tourSpotList[i].tourSpotIsTop100,
          tourSpotList[i].tourSpotTwoDepthCode,
          tourSpotList[i].tourSpotId,
          tourSpotList[i].tourSpotName
        );
      }
    };
    const displayMarker = (
      position,
      content,
      isTop100,
      twoDepthCode,
      tourSpotId,
      tourSpotName
    ) => {
      var marker = new kakao.maps.Marker({
        position: position,
        clickable: true,
      });
      if (isTop100 === true) {
        var markerImage = new kakao.maps.MarkerImage(
          "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_red.png",
          new kakao.maps.Size(31, 35),
          new kakao.maps.Point(13, 34)
        );
        marker.setImage(markerImage);
        state.tourspot_top100.push([marker, twoDepthCode]);
      } else {
        state.tourspot.push([marker, twoDepthCode]);
      }
      var overlay = new kakao.maps.CustomOverlay({
        content: content,
        position: marker.getPosition(),
        zIndex: 100,
      });
      kakao.maps.event.addListener(marker, "click", function () {
        if (state.customOverlay[0]) {
          state.customOverlay[0].setMap(null);
        }
        overlay.setMap(state.map);
        state.customOverlay = [overlay, tourSpotId, tourSpotName];
      });
    };
    const makePolygonDepth1 = (json_data) => {
      for (var i = 0; i < json_data.features.length; i++) {
        var area = {
          name: json_data.features[i].properties.CTP_KOR_NM,
          path: [],
          num: json_data.features[i].properties.CTPRVN_CD,
        };
        if (json_data.features[i].geometry.type === "Polygon") {
          let path_tmp = [];
          for (
            var j = 0;
            j < json_data.features[i].geometry.coordinates[0].length;
            j++
          ) {
            path_tmp.push(
              new kakao.maps.LatLng(
                json_data.features[i].geometry.coordinates[0][j][1],
                json_data.features[i].geometry.coordinates[0][j][0]
              )
            );
          }
          area.path = path_tmp;
          displayPolygonDepth1(area);
        } else {
          for (
            j = 0;
            j < json_data.features[i].geometry.coordinates.length;
            j++
          ) {
            for (
              var k = 0;
              k < json_data.features[i].geometry.coordinates[j].length;
              k++
            ) {
              let path_tmp = [];
              for (
                var t = 0;
                t < json_data.features[i].geometry.coordinates[j][k].length;
                t++
              ) {
                path_tmp.push(
                  new kakao.maps.LatLng(
                    json_data.features[i].geometry.coordinates[j][k][t][1],
                    json_data.features[i].geometry.coordinates[j][k][t][0]
                  )
                );
              }
              area.path = path_tmp;
              displayPolygonDepth1(area);
            }
          }
        }
      }
    };
    const displayPolygonDepth1 = (area) => {
      var polygon = new kakao.maps.Polygon({
        path: area.path,
        strokeWeight: 2,
        strokeColor: "#004c80",
        strokeOpacity: 0.8,
        fillColor: "#004c80",
        fillOpacity: 0.1,
      });
      polygon.setMap(state.map);
      state.sido_polygon.push([area.name, area.num, polygon]);
      kakao.maps.event.addListener(polygon, "click", function (mouseEvent) {
        state.num = area.num;
        state.map.setLevel(10);
        for (var i = 0; i < state.sigungu_polygon.length; i++) {
          if (state.sigungu_polygon[i][1].substr(0, 2) !== area.num) {
            continue;
          }
          state.sigungu_polygon[i][2].setMap(state.map);
        }
        for (i = 0; i < state.tourspot_top100.length; i++) {
          if (String(state.tourspot_top100[i][1]).substr(0, 2) !== area.num) {
            continue;
          }
          state.tourspot_top100[i][0].setMap(state.map);
        }
        var mousePoint = mouseEvent.latLng;
        state.map.setCenter(
          new kakao.maps.LatLng(mousePoint.getLat(), mousePoint.getLng())
        );
      });
      kakao.maps.event.addListener(polygon, "mouseover", function () {
        polygon.setOptions({ fillColor: "#09f" });
      });
      kakao.maps.event.addListener(polygon, "mouseout", function () {
        polygon.setOptions({ fillColor: "#004c80" });
      });
    };
    const makePolygonDepth2 = (json_data) => {
      for (var i = 0; i < json_data.features.length; i++) {
        var area = {
          name: json_data.features[i].properties.SIG_KOR_NM,
          path: [],
          num: json_data.features[i].properties.SIG_CD,
        };
        if (json_data.features[i].geometry.type === "Polygon") {
          let path_tmp = [];
          for (
            var j = 0;
            j < json_data.features[i].geometry.coordinates[0].length;
            j++
          ) {
            path_tmp.push(
              new kakao.maps.LatLng(
                json_data.features[i].geometry.coordinates[0][j][1],
                json_data.features[i].geometry.coordinates[0][j][0]
              )
            );
          }
          area.path = path_tmp;
          displayPolygonDepth2(area);
        } else {
          for (
            j = 0;
            j < json_data.features[i].geometry.coordinates.length;
            j++
          ) {
            for (
              var k = 0;
              k < json_data.features[i].geometry.coordinates[j].length;
              k++
            ) {
              let path_tmp = [];
              for (
                var t = 0;
                t < json_data.features[i].geometry.coordinates[j][k].length;
                t++
              ) {
                path_tmp.push(
                  new kakao.maps.LatLng(
                    json_data.features[i].geometry.coordinates[j][k][t][1],
                    json_data.features[i].geometry.coordinates[j][k][t][0]
                  )
                );
              }
              area.path = path_tmp;
              displayPolygonDepth2(area);
            }
          }
        }
      }
    };
    const displayPolygonDepth2 = (area) => {
      var polygon = new kakao.maps.Polygon({
        path: area.path,
        strokeWeight: 2,
        strokeColor: "#00CC00",
        strokeOpacity: 0.8,
        fillColor: "#00CC00",
        fillOpacity: 0.1,
      });
      kakao.maps.event.addListener(polygon, "click", function (mouseEvent) {
        for (var i = 0; i < state.tourspot.length; i++) {
          if (String(state.tourspot[i][1]) !== area.num) {
            continue;
          }
          state.tourspot[i][0].setMap(state.map);
        }
        state.map.setLevel(8);
        var mousePoint = mouseEvent.latLng;
        state.map.setCenter(
          new kakao.maps.LatLng(mousePoint.getLat(), mousePoint.getLng())
        );
      });
      state.sigungu_polygon.push([area.name, area.num, polygon]);
      kakao.maps.event.addListener(polygon, "mouseover", function () {
        polygon.setOptions({ fillColor: "#0033CC" });
      });
      kakao.maps.event.addListener(polygon, "mouseout", function () {
        polygon.setOptions({ fillColor: "#00CC00" });
      });
    };
    return {
      state,
      initMap,
      addKakaoMapScript,
      makeMarker,
      makePolygonDepth1,
      makePolygonDepth2,
      displayPolygonDepth1,
      displayPolygonDepth2,
    };
  },
};
</script>

<style>
.overlay_info {
  border-radius: 6px;
  margin-bottom: 12px;
  float: left;
  position: relative;
  border: 1px solid #ccc;
  border-bottom: 2px solid #ddd;
  background-color: #fff;
}
.selectbar {
  background-color: #fff;
}
.selectbar li:hover {
  color: #fff;
  background: #d24545;
  cursor: pointer;
}
.selectbar li:hover .up {
  background-position: 0 0px;
}
.selectbar li:hover .down {
  background-position: 0 -20px;
}
.overlay_info:nth-of-type(n) {
  border: 0;
  box-shadow: 0px 1px 2px #888;
}
.overlay_info .title {
  display: block;
  background: #d95050;
  text-decoration: none;
  color: #fff;
  padding: 12px 36px 12px 14px;
  font-size: 14px;
  border-radius: 6px 6px 0 0;
}
.overlay_info .close {
  position: absolute;
  top: 14px;
  right: 10px;
  width: 17px;
  height: 17px;
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/overlay_close.png");
}
.overlay_info .close:hover {
  cursor: pointer;
}
.overlay_info .desc {
  padding: 14px;
  position: relative;
  min-width: 250px;
  min-height: 50px;
}
.overlay_info img {
  vertical-align: top;
}
.desc div {
  text-align: left;
  font-size: 12px;
}
.desc .register {
  width: 17px;
  height: 17px;
  background: url("../assets/kakao_login_large_wide.png");
}
.desc .register:hover {
  cursor: pointer;
}
/* .overlay_info .address {
  font-size: 12px;
  color: #333;
  position: absolute;
  left: 10px;
  right: 10px; 
  top: 10px;
  white-space: normal;
} */
/* .map {
  width: 1900px;
  height: 800px;
} */
#selectedApt_wrap {
  position: absolute;
  top: 0;
  right: 2%;
  bottom: 0;
  width: 16%;
  height: 50%;
  /* margin: 10px 0 30px 0px; */
  padding: 5px;
  overflow-y: auto;
  background: rgba(245, 245, 245, 1);
  z-index: 3;
  /* font-size: 12px; */
  border-radius: 10px;
}
#webrtc-wrapper {
  width: 100vw;
  height: 100vh;
  top: 90%;
}
</style>
