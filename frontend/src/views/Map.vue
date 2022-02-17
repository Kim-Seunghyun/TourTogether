<template>
  <div id="zzz">
    <div id="map" class="map" style="width: 100%">
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
</template>

<script>
import { reactive } from "vue";
import { onMounted } from "vue";

import Plan from "@/components/Plan.vue";
import { API_BASE_URL } from "@/config/index.js";
import axios from "axios";
import html2pdf from "html2pdf.js";
import html2canvas from "html2canvas";
export default {
  name: "Map",
  components: {
    Plan,
  },
  setup() {
    const sido_json = require("../assets/sido.json");
    const sigungu_json = require("../assets/sigungu.json");
    const state = reactive({
      index: 0,
      page: 0,
      num: null,
      map: null,
      depthlevel: 0,
      src: [
        "https://tour-together--s3.s3.ap-northeast-2.amazonaws.com/%EA%B2%BD%EB%B3%B5%EA%B6%81.jfif",
        "https://tour-together--s3.s3.ap-northeast-2.amazonaws.com/%EB%82%99%EC%82%B0%ED%95%B4%EC%88%98%EC%9A%95%EC%9E%A5.jfif",
        "https://tour-together--s3.s3.ap-northeast-2.amazonaws.com/%EB%8F%84%EB%9D%BC%EC%97%90%EB%AA%BD.jfif",
        "https://tour-together--s3.s3.ap-northeast-2.amazonaws.com/%EC%9A%A9%EC%9D%B8%EC%8B%9C%EB%B0%95%EB%AC%BC%EA%B4%80.jfif",
        "https://tour-together--s3.s3.ap-northeast-2.amazonaws.com/%EC%B2%A8%EC%84%B1%EB%8C%80.jfif",
        "https://tour-together--s3.s3.ap-northeast-2.amazonaws.com/%ED%95%9C%EB%9D%BC%EC%82%B0.jfif",
        "https://tour-together--s3.s3.ap-northeast-2.amazonaws.com/%EB%86%80%EC%9D%B4%EA%B3%B5%EC%9B%90.png",
        "https://tour-together--s3.s3.ap-northeast-2.amazonaws.com/%EB%82%A8%EC%82%B0%ED%83%80%EC%9B%8C.png",
        "https://tour-together--s3.s3.ap-northeast-2.amazonaws.com/%EB%9D%BC%EC%9D%B4%EC%96%B8.jpg",
        "https://tour-together--s3.s3.ap-northeast-2.amazonaws.com/%EB%8F%84%EB%9D%BC%EC%97%90%EB%AA%BD.jfif",
      ],
      infowindow: null,
      listFlag: false,
      areas: [],
      bounds: [],
      polyline: null,
      customOverlay: [], //overlay, tourSpotId, tourSpotName
      sido_polygon: [],
      sigungu_polygon: [],
      spot: [], // {marker, tourspotList}
      tourspot: [], // {marker, tourSpotList[i]} index,
      tourspot_top100: [], // {marker, tourSpotList[i]}
      ps: null,
      tmp: Object,
      day: null,
      emitflag: false,
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
      kakao.maps.event.addListener(state.map, "rightclick", function () {
        if (state.depthlevel === 1) {
          showDepth0();
        } else if (state.depthlevel === 2) {
          showDepth1();
        }
      });
      axios({
        method: "get",
        url: API_BASE_URL + "tourspot",
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
        var marker = new kakao.maps.Marker({
          position: position,
          clickable: true,
        });
        var index = tourSpotList[i].tourSpotId;
        if (tourSpotList[i].tourSpotIsTop100 === true) {
          var markerImage = new kakao.maps.MarkerImage(
            "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_red.png",
            new kakao.maps.Size(31, 35),
            new kakao.maps.Point(13, 34)
          );
          marker.setImage(markerImage);
          state.tourspot_top100.push(index); // 그 i를 저장
        } else {
          state.tourspot.push(index);
        }
        state.spot[index] = [marker, tourSpotList[i]];
        let event = click(marker, tourSpotList[i], index);
        kakao.maps.event.addListener(marker, "click", event);
      }
    };
    const click = (marker, tourlist, index) => {
      kakao.maps.event.addListener(marker, "click", function () {
        state.index++;
        console.log(tourlist.tourSpotId);
        console.log(index);
        makeCustomOverlay(marker.getPosition(), tourlist, index);
        displayCustomOverlay();
      });
    };
    const displayMarker = (depth, areaNum) => {
      removeAllChildNods(); // list 초기화
      var index = 0;
      if (depth > 1) {
        removeMarker(depth);
        for (i = 0; i < state.tourspot.length; i++) {
          var tmp = state.tourspot[i];
          if (!state.spot[tmp]) {
            continue;
          }
          if (String(state.spot[tmp][1].tourSpotTwoDepthCode) !== areaNum) {
            continue;
          }
          index++;
          if (index === 16) {
            index = 1;
          }
          displayPlaces(index, state.spot[tmp][1], tmp);
          state.spot[tmp][0].setMap(state.map);
        }
      } else if (depth > 0) {
        for (var i = 0; i < state.tourspot_top100.length; i++) {
          tmp = state.tourspot_top100[i];
          if (!state.spot[tmp]) {
            continue;
          }
          if (
            String(state.spot[tmp][1].tourSpotTwoDepthCode).substr(0, 2) !==
            areaNum
          ) {
            continue;
          }
          index++;
          if (index === 16) {
            index = 1;
          }
          displayPlaces(index, state.spot[tmp][1], tmp); //4700번 더돌고
          state.spot[tmp][0].setMap(state.map);
        }
      }
    };
    const removeMarker = (depth) => {
      if (depth === 2) {
        for (var i = 0; i < state.tourspot.length; i++) {
          var index = state.tourspot[i];
          if (!state.spot[index]) {
            continue;
          }
          state.spot[index][0].setMap(null);
        }
      }
    };
    const makeCustomOverlay = (position, tourSpot, index) => {
      var overlay_info = document.createElement("div");
      overlay_info.className = "overlay_info";

      var title = document.createElement("div");
      title.className = "title";
      title.innerText = tourSpot.tourSpotName;
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

      var img = document.createElement("img");
      var i = index % 10;
      img.src = state.src[i];
      desc.appendChild(img);

      var address = document.createElement("div");
      address.className = "address";
      address.innerHTML = "주소 : " + tourSpot.tourSpotAddress;
      desc.appendChild(address);

      if (tourSpot.tourSpotHoliday) {
        var holiday = document.createElement("div");
        holiday.className = "holiday";
        holiday.innerHTML = "쉬는날 : " + tourSpot.tourSpotHoliday;
        desc.appendChild(holiday);
      }

      if (tourSpot.tourSpotTime) {
        var time = document.createElement("div");
        time.className = "time";
        time.innerHTML = "이용시간 : " + tourSpot.tourSpotTime;
        desc.appendChild(time);
      }

      if (tourSpot.tourSpotParking) {
        var parking = document.createElement("div");
        parking.className = "parking";
        parking.innerHTML = "주차시설 : " + tourSpot.tourSpotParking;
        desc.appendChild(parking);
      }

      var bottom = document.createElement("div");
      bottom.className = "bottom";
      desc.appendChild(bottom);

      var selectbar = document.createElement("select");
      selectbar.className = "selectbar";

      for (var j = 1; j <= state.day; j++) {
        var tmp = document.createElement("option");
        tmp.value = j;
        tmp.innerHTML = j + "일차";
        selectbar.appendChild(tmp);
      }
      selectbar.onchange = function () {
        selectbar.value = this.options[this.selectedIndex].value;
      };
      bottom.appendChild(selectbar);
      var register = document.createElement("div");
      register.className = "register";
      register.setAttribute("title", "추가하기");
      bottom.appendChild(register);
      var overlay = new kakao.maps.CustomOverlay({
        content: overlay_info,
        position: position,
        zIndex: 100,
      });
      state.customOverlay.push([overlay, index, tourSpot.tourSpotName]);
      register.onclick = () => {
        state.tmp = {
          name: state.customOverlay[0][2],
          // 관광지 이름
          day: Number(selectbar.value),
          lat: state.customOverlay[0][0].getPosition().getLat(),
          lng: state.customOverlay[0][0].getPosition().getLng(),
          index: state.customOverlay[0][1],
        };
      };
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
      kakao.maps.event.addListener(polygon, "click", function () {
        var bounds = new kakao.maps.LatLngBounds();
        for (var i = 0; i < state.sido_polygon.length; i++) {
          if (state.sido_polygon[i][1] === area.num) {
            for (
              var j = 0;
              j < state.sido_polygon[i][2].getPath().length;
              j++
            ) {
              bounds.extend(state.sido_polygon[i][2].getPath()[j]);
            }
          }
        }
        state.bounds = bounds;
        state.num = area.num;
        displayMarker(1, area.num);
        showDepth1();
        for (i = 0; i < state.sigungu_polygon.length; i++) {
          if (state.sigungu_polygon[i][1].substr(0, 2) !== area.num) {
            continue;
          }
          state.sigungu_polygon[i][2].setMap(state.map);
        }
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
      kakao.maps.event.addListener(polygon, "click", function () {
        state.depthlevel = 2;
        var bounds = new kakao.maps.LatLngBounds();
        for (var i = 0; i < area.path.length; i++) {
          bounds.extend(area.path[i]);
        }
        state.map.setBounds(bounds);
        displayMarker(2, area.num);
      });
      state.sigungu_polygon.push([area.name, area.num, polygon]);
      kakao.maps.event.addListener(polygon, "mouseover", function () {
        polygon.setOptions({ fillColor: "#0033CC" });
      });
      kakao.maps.event.addListener(polygon, "mouseout", function () {
        polygon.setOptions({ fillColor: "#00CC00" });
      });
    };
    const showDepth0 = () => {
      state.depthlevel = 0;
      state.map.setLevel(13);
      state.map.setCenter(
        new kakao.maps.LatLng(37.565400364768095, 126.97723780998845)
      );
      state.num = 0;
      for (var i = 0; i < state.sigungu_polygon.length; i++) {
        state.sigungu_polygon[i][2].setMap(null);
      }
      for (i = 0; i < state.tourspot_top100.length; i++) {
        var index = state.tourspot_top100[i];
        if (!state.spot[index]) {
          continue;
        }
        state.spot[index][0].setMap(null);
      }
      for (i = 0; i < state.sido_polygon.length; i++) {
        state.sido_polygon[i][2].setMap(state.map);
      }
    };
    const showDepth1 = () => {
      state.depthlevel = 1;
      state.map.setBounds(state.bounds);
      for (var i = 0; i < state.sido_polygon.length; i++) {
        state.sido_polygon[i][2].setMap(null);
      }
      for (i = 0; i < state.tourspot.length; i++) {
        var index = state.tourspot[i];
        if (!state.spot[index]) {
          continue;
        }
        state.spot[index][0].setMap(null);
      }
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
    const displayPlaces = (listindex, tourspot, markerindex) => {
      var listEl = document.getElementById("placesList");
      var menuEl = document.getElementById("menu_wrap");
      removeMarker();
      var itemEl = getListItem(listindex, tourspot);
      itemEl.onclick = function () {
        state.map.setCenter(state.spot[markerindex][0].getPosition());
        makeCustomOverlay(
          state.spot[markerindex][0].getPosition(),
          state.spot[markerindex][1],
          markerindex
        );
        displayCustomOverlay();
      };
      listEl.appendChild(itemEl);
      menuEl.scrollTop = 0;
    };
    const getListItem = (index, tourspot) => {
      var el = document.createElement("li");
      el.className = "item";

      var itemStr = document.createElement("span");
      var img = document.createElement("img");
      img.className = "img";
      img.setAttribute('style', 'width:120px; height:120px');
      img.src = state.src[tourspot.tourSpotId%10];
      itemStr.appendChild(img);

      var info = document.createElement("div");
      info.className = "info";
      itemStr.appendChild(info);

      var htag = document.createElement("h5");
      htag.className = "htag";
      htag.innerText = tourspot.tourSpotName;
      info.appendChild(htag);

      var spantag = document.createElement("span");
      spantag.className = "spantag";
      spantag.innerText = tourspot.tourSpotAddress;
      info.appendChild(spantag);

      el.appendChild(itemStr);
      return el;
    };
    const removeAllChildNods = () => {
      var listEl = document.getElementById("placesList");
      while (listEl.firstChild) {
        listEl.removeChild(listEl.firstChild);
      }
    };
    const emitList = function (abc) {
      console.log(abc);
      if (state.polyline) {
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
    const aaa = () => {
      console.log("aaa");
      //state.emitflag = !state.emitflag;
      var element = document.getElementById("zzz");
      html2canvas(element).then(function (canvas) {
        saveAs(canvas.toDataURL("image/png"), "capture-test.png");
      });
      html2pdf()
        .from(element)
        .set({
          margin: 10,
          filename: "filename.pdf",
          html2canvas: { scale: 1 },
          jsPDF: {
            orientation: "landscape",
            unit: "mm",
            format: "a4",
            compressPDF: true,
          },
        })
        .save();
    };
    const saveAs = (url, filename) => {
      var link = document.createElement("a");
      if (typeof link.download === "string") {
        link.href = url;
        link.download = filename;
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
      } else {
        window.open(url);
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
      displayPlaces,
      getListItem,
      removeAllChildNods,
      emitList,
      emitDay,
      click,
      showDepth0,
      showDepth1,
      aaa,
    };
  },
};
</script>

<style>
.bg_white {
  background: #fff;
}
#zzz{
  
}
#menu_wrap hr {
  display: block;
  height: 1px;
  border: 0;
  border-top: 2px solid #5f5f5f;
  margin: 3px 0;
}
#placesList li {
  list-style: none;
}
#placesList .item {
  position: relative;
  border-bottom: 1px solid #888;
  overflow: hidden;
  cursor: pointer;
  min-height: 100px;
}
#placesList .item span {
  display: block;
  margin-top: 4px;
}
#placesList .item h5,
#placesList .item .info {
  text-overflow: ellipsis;
  /* overflow: hidden; */
  white-space: nowrap;
}
#placesList .item .info {
  padding: 10px 0 10px 55px;
}
#placesList .info .spantag {
  color: #009900;
}
#placesList .item .img {
  float: left;
  position: relative;
  width: 37px;
  height: 37px;
  margin: 10px 0 0 10px;
  /* //background: url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png)
    no-repeat; */
}
/* #placesList .item .marker_1 {
  background-position: 0 -10px;
}
#placesList .item .marker_2 {
  background-position: 0 -56px;
}
#placesList .item .marker_3 {
  background-position: 0 -102px;
}
#placesList .item .marker_4 {
  background-position: 0 -148px;
}
#placesList .item .marker_5 {
  background-position: 0 -194px;
}
#placesList .item .marker_6 {
  background-position: 0 -240px;
}
#placesList .item .marker_7 {
  background-position: 0 -286px;
}
#placesList .item .marker_8 {
  background-position: 0 -332px;
}
#placesList .item .marker_9 {
  background-position: 0 -378px;
}
#placesList .item .marker_10 {
  background-position: 0 -423px;
}
#placesList .item .marker_11 {
  background-position: 0 -470px;
}
#placesList .item .marker_12 {
  background-position: 0 -516px;
}
#placesList .item .marker_13 {
  background-position: 0 -562px;
}
#placesList .item .marker_14 {
  background-position: 0 -608px;
}
#placesList .item .marker_15 {
  background-position: 0 -654px;
} */

#pagination {
  margin: 10px auto;
  text-align: center;
}
#pagination a {
  display: inline-block;
  margin-right: 10px;
}
#pagination .on {
  font-weight: bold;
  cursor: default;
  color: #777;
}
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
  width: 128px;
  height: 128px;
  vertical-align: top;
}
.desc div {
  text-align: left;
  font-size: 12px;
}
.desc .register {
  width: 32px;
  height: 32px;
  background: url("../assets/circularplusbutton_121982.png");
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

.map {
  margin: 0 auto;
}

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
#map {
  width: 100vw;
  height: 70vh;
  position: relative;
}
</style>
