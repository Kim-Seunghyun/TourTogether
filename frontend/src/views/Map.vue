<template>
  <div>
    <div id="map" style="height: 1000px" class="map">
      <div id="selectedApt_wrap" style="display: block">
        <Plan
          v-on:getLine="emitList"
          :tourData="state.tmp"
          v-on:getDay="emitDay"
        />
      </div>
    </div>
    <div id="menu_wrap" class="bg_white">
      <ul id="placesList"></ul>
      <div id="pagination"></div>
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
      polyline: null,
      customOverlay: [], //overlay, tourSpotId, tourSpotName
      sido_polygon: [],
      sigungu_polygon: [],
      tourspot: [],
      tourspot_top100: [], // {marker, 2depthcode}
      ps: null,
      tmp: Object,
      day: null,
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
        url: "https://i6a105.p.ssafy.io:8081/tourspot",
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
        "https://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=d8a883df5abd036e1c9ac7857a408e64&libraries=services";
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
        // var name = tourSpotList[i].tourSpotName;/
        var close = document.createElement("div");
        close.onclick = function () {
          state.customOverlay[0][0].setMap(null);
        };
        close.className = "close";
        close.setAttribute("title", "닫기");
        title.appendChild(close);

        var desc = document.createElement("div");
        desc.className = "desc";
        overlay_info.appendChild(desc);

        var address = document.createElement("div");
        address.className = "address";
        address.innerHTML = "주소 : " + tourSpotList[i].tourSpotAddress;
        desc.appendChild(address);

        if (tourSpotList[i].tourSpotHoliday) {
          var holiday = document.createElement("div");
          holiday.className = "holiday";
          holiday.innerHTML = "쉬는날 : " + tourSpotList[i].tourSpotHoliday;
          desc.appendChild(holiday);
        }

        if (tourSpotList[i].tourSpotTime) {
          var time = document.createElement("div");
          time.className = "time";
          time.innerHTML = "이용시간 : " + tourSpotList[i].tourSpotTime;
          desc.appendChild(time);
        }

        if (tourSpotList[i].tourSpotParking) {
          var parking = document.createElement("div");
          parking.className = "parking";
          parking.innerHTML = "주차시설 : " + tourSpotList[i].tourSpotParking;
          desc.appendChild(parking);
        }

        var bottom = document.createElement("div");
        bottom.className = "bottom";
        desc.appendChild(bottom);

        var selectbar = document.createElement("select");
        selectbar.className = "selectbar";

        for (var j = 1; j <= 3; j++) {
          var tmp = document.createElement("option");
          tmp.value = j;
          tmp.innerHTML = j;
          selectbar.appendChild(tmp);
        }
        selectbar.onchange = function () {
          selectbar.value = this.options[this.selectedIndex].value;
        };
        bottom.appendChild(selectbar);

        var register = document.createElement("div");
        register.className = "register";

        register.onclick = () => {
          state.tmp = {
            name: state.customOverlay[0][2],
            day: Number(selectbar.value),
            lat: state.customOverlay[0][0].getPosition().getLat(),
            lng: state.customOverlay[0][0].getPosition().getLng(),
          };
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
      kakao.maps.event.addListener(marker, "click", function () {
        makeCustomOverlay(
          content,
          marker.getPosition(),
          tourSpotId,
          tourSpotName
        );
        displayCustomOverlay();
      });
    };
    const removeMarker = () => {};
    const makeCustomOverlay = (content, position, tourSpotId, tourSpotName) => {
      var overlay = new kakao.maps.CustomOverlay({
        content: content,
        position: position,
        zIndex: 100,
      });
      state.customOverlay.push([overlay, tourSpotId, tourSpotName]);
    };
    const displayCustomOverlay = () => {
      if (state.customOverlay.length === 1) {
        state.customOverlay[0][0].setMap(state.map);
      } else {
        state.customOverlay[0][0].setMap(null);
        state.customOverlay[1][0].setMap(state.map);
        state.customOverlay.shift();
      }
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
        removeAllChildNods(); // list 초기화
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
          displayPlaces(state.tourspot_top100[i][0]);
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
        displayPolyline();
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
    const makePolyline = () => {
      var polyline = new kakao.maps.Polyline({
        path: [],
        strokeWeight: 5,
        strokeOpacity: 1,
        strokeStyle: "solid",
      });
      state.polyline = polyline;
    };
    const displayPolyline = () => {
      state.polyline.setMap(state.map);
    };
    const nondisplayPolyline = () => {
      state.polyline.setMap(null);
    };
    const removePolyline = () => {
      state.polyline = null;
    };
    const addPolyline = (path) => {
      state.polyline.setPath(path);
    };
    const makeSearchBar = () => {
      var ps = new kakao.maps.services.Places();
      state.ps = ps;
      ps.setMap(state.map);
      var infowindow = new kakao.maps.InfoWindow({ zIndex: 1 });
      state.infowindow = infowindow;
      infowindow.setMap(state.map);
      searchPlaces();
    };
    const searchPlaces = () => {
      var keyword = document.getElementById("keyword").value;

      if (!keyword.replace(/^\s+|\s+$/g, "")) {
        alert("키워드를 입력해주세요!");
        return false;
      }

      state.ps.keywordSearch(keyword, placesearch);
    };
    const placesearch = (data, sta, pagination) => {
      console.log(data);
      console.log(sta);
      console.log(pagination);
      if (sta === kakao.maps.services.Status.OK) {
        displayPlaces(data);
        displayPagination(pagination);
      } else if (sta === kakao.maps.services.Status.ZERO_RESULT) {
        alert("검색 결과가 존재하지 않습니다.");
        return;
      } else if (sta === kakao.maps.services.Status.ERROR) {
        alert("검색 결과 중 오류가 발생했습니다.");
        return;
      }
    };
    const displayPlaces = (tourspot) => {
      var listEl = document.getElementById("placesList");
      var menuEl = document.getElementById("menu_wrap");
      //var fragment = document.createDocumentFragment();
      //var bounds = new kakao.maps.LatLngBounds();
      //var listStr = '';
      removeMarker();
      //var placePosition = new kakao.maps.LatLng(tourspot.getPosition().getLat(), tourspot.getPosition().getLng());
      //var marker = addMarker(placePosition, i);
      var itemEl = getListItem(3, tourspot);
      itemEl.onmouseover = function () {};
      //bounds.extend(placePosition);
      listEl.appendChild(itemEl);
      //listEl.appendChild(fragment);
      menuEl.scrollTop = 0;

      //state.map.setBounds(bounds);
    };
    const getListItem = (index, tourspot) => {
      var el = document.createElement("li");

      var itemStr = document.createElement("span");
      itemStr.className = "markerbg marker_" + (index + 1);

      var info = document.createElement("div");
      info.className = "info";
      itemStr.appendChild(info);

      var htag = document.createElement("h5");
      htag.innerText = tourspot.getPosition();
      info.appendChild(htag);

      var spantag = document.createElement("span");
      spantag.innerText = tourspot.getPosition();
      info.appendChild(spantag);
      el.appendChild(itemStr);
      el.className = "item";
      return el;
    };
    const displayPagination = (pagination) => {
      var paginationEl = document.getElementById("pagination");
      var fragment = document.createDocumentFragment();

      while (paginationEl.hasChildNodes()) {
        paginationEl.removeChild(paginationEl.lastChild);
      }
      for (var i = 1; i <= pagination.last; i++) {
        var el = document.createElement("a");
        el.href = "#";
        el.innerHTML = i;

        if (i === pagination.current) {
          el.className = "on";
        } else {
          el.onclick = (function (i) {
            return function () {
              pagination.gotoPage(i);
            };
          })(i);
        }
        fragment.appendChild(fragment);
      }
      paginationEl.appendChild(fragment);
    };
    const removeAllChildNods = () => {
      var listEl = document.getElementById("placesList");
      while (listEl.firstChild) {
        listEl.removeChild(listEl.firstChild);
      }
    };
    const emitList = function (abc) {
      if (state.polyline) {
        console.log(state.polyline);
        nondisplayPolyline();
        removePolyline();
      }
      if (abc) {
        makePolyline();
        var path = [];
        for (var i = 0; i < abc.list.length; i++) {
          path.push(new kakao.maps.LatLng(abc.list[i].lat, abc.list[i].lng));
        }
        addPolyline(path);
        displayPolyline();
      }
    };
    const emitDay = (day) => {
      state.day = day;
    };
    return {
      state,
      initMap,
      addKakaoMapScript,
      makeMarker,
      displayMarker,
      removeMarker,
      makeCustomOverlay,
      displayCustomOverlay,
      makePolygonDepth1,
      makePolygonDepth2,
      displayPolygonDepth1,
      displayPolygonDepth2,
      makePolyline,
      displayPolyline,
      nondisplayPolyline,
      removePolyline,
      addPolyline,
      makeSearchBar,
      searchPlaces,
      placesearch,
      displayPlaces,
      getListItem,
      displayPagination,
      removeAllChildNods,
      emitList,
      emitDay,
    };
  },
};
</script>

<style>
.bg_white {
  background: #fff;
}
#menu_wrap hr {
  display: block;
  height: 1px;
  border: 0;
  border-top: 2px solid #5f5f5f;
  margin: 3px 0;
}
#menu_wrap .option {
  text-align: center;
}
#menu_wrap .option p {
  margin: 10px 0;
}
#menu_wrap .option button {
  margin-left: 5px;
} /*
#placesList li {list-style: none;}
#placesList .item {position:relative;border-bottom:1px solid #888;overflow: hidden;cursor: pointer;min-height: 65px;}
#placesList .item span {display: block;margin-top:4px;}
#placesList .item h5, #placesList .item .info {text-overflow: ellipsis;overflow: hidden;white-space: nowrap;}
#placesList .item .info{padding:10px 0 10px 55px;}
#placesList .info .gray {color:#8a8a8a;}
#placesList .info .jibun {padding-left:26px;background:url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/places_jibun.png) no-repeat;}
#placesList .info .tel {color:#009900;}
#placesList .item .markerbg {float:left;position:absolute;width:36px; height:37px;margin:10px 0 0 10px;background:url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png) no-repeat;}
#placesList .item .marker_1 {background-position: 0 -10px;}
#placesList .item .marker_2 {background-position: 0 -56px;}
#placesList .item .marker_3 {background-position: 0 -102px}
#placesList .item .marker_4 {background-position: 0 -148px;}
#placesList .item .marker_5 {background-position: 0 -194px;}
#placesList .item .marker_6 {background-position: 0 -240px;}
#placesList .item .marker_7 {background-position: 0 -286px;}
#placesList .item .marker_8 {background-position: 0 -332px;}
#placesList .item .marker_9 {background-position: 0 -378px;}
#placesList .item .marker_10 {background-position: 0 -423px;}
#placesList .item .marker_11 {background-position: 0 -470px;}
#placesList .item .marker_12 {background-position: 0 -516px;}
#placesList .item .marker_13 {background-position: 0 -562px;}
#placesList .item .marker_14 {background-position: 0 -608px;}
#placesList .item .marker_15 {background-position: 0 -654px;}
#pagination {margin:10px auto;text-align: center;}
#pagination a {display:inline-block;margin-right:10px;}
#pagination .on {font-weight: bold; cursor: default;color:#777;}*/
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
