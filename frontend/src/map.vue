<template>
  <div>
    <div id="map" class="map">
      <div id="selectedApt_wrap" style="display: block"></div>
    </div>
  </div>
</template>

<script>
import { reactive } from "@vue/reactivity";
import { onMounted } from "vue";
export default {
  name: "Map",
  setup() {
    const state = reactive({
      map: null,
      infowindow: null,
      customOverlay: null,
      listFlag: false,
      areas: [],
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
        level: 6, //지도의 레벨(확대, 축소 정도)
      };

      state.map = new kakao.maps.Map(container, options); //지도 생성 및 객체 리턴

      kakao.maps.event.addListener(
        state.map,
        "rightclick",
        function (mouseEvent) {
          const latlng = mouseEvent.latLng;
          const marker = new kakao.maps.Marker({
            position: latlng,
          });
          marker.setMap(state.map);
          marker.setDraggable(true);
          kakao.maps.event.addListener(marker, "dragend", function () {
            if (state.listFlag) {
              const target = document.getElementById("selectedApt_wrap");
              const el = document.createElement("li");
              el.innerText = "목록추가";
              target.append(el);
            }
          });
        }
      );
      const area = {
        name: "광진구",
        path: [
          new kakao.maps.LatLng(37.5225138223162, 127.076942368502),
          new kakao.maps.LatLng(37.5238703115522, 127.068663355722),
          new kakao.maps.LatLng(37.5283170310248, 127.056218188555),
          new kakao.maps.LatLng(37.5594136266974, 127.073735987985),
          new kakao.maps.LatLng(37.5599594627312, 127.072385143217),
          new kakao.maps.LatLng(37.5646030699021, 127.074091919609),
          new kakao.maps.LatLng(37.5679289176431, 127.07687896164),
          new kakao.maps.LatLng(37.5718692847761, 127.078201125013),
          new kakao.maps.LatLng(37.5708460967375, 127.088177293384),
          new kakao.maps.LatLng(37.5701811961495, 127.08839613212),
          new kakao.maps.LatLng(37.5696974528031, 127.090493133795),
          new kakao.maps.LatLng(37.5705709002847, 127.093753268912),
          new kakao.maps.LatLng(37.5706087946306, 127.09560458024),
          new kakao.maps.LatLng(37.5713407149009, 127.095838006407),
          new kakao.maps.LatLng(37.5727373317492, 127.099439391385),
          new kakao.maps.LatLng(37.5737644577368, 127.100851662398),
          new kakao.maps.LatLng(37.5724690112402, 127.101702312713),
          new kakao.maps.LatLng(37.5723522115425, 127.10317293759),
          new kakao.maps.LatLng(37.5714510813358, 127.104295257186),
          new kakao.maps.LatLng(37.5701099698592, 127.103506100869),
          new kakao.maps.LatLng(37.5616050611678, 127.101251098522),
          new kakao.maps.LatLng(37.5591856398945, 127.102168860323),
          new kakao.maps.LatLng(37.5575662329601, 127.104276501906),
          new kakao.maps.LatLng(37.5564128812677, 127.104941263313),
          new kakao.maps.LatLng(37.5564597042712, 127.106393428785),
          new kakao.maps.LatLng(37.5582449596377, 127.108544023122),
          new kakao.maps.LatLng(37.5585534806966, 127.10952884479),
          new kakao.maps.LatLng(37.5582526255242, 127.110349563654),
          new kakao.maps.LatLng(37.5590002663295, 127.11229722938),
          new kakao.maps.LatLng(37.5583914765126, 127.113918265533),
          new kakao.maps.LatLng(37.5568342454959, 127.113316871422),
          new kakao.maps.LatLng(37.5567599830526, 127.11522585379),
          new kakao.maps.LatLng(37.5540845741686, 127.114088523974),
          new kakao.maps.LatLng(37.5505121704936, 127.111549852489),
          new kakao.maps.LatLng(37.5469263810068, 127.11147102834),
          new kakao.maps.LatLng(37.541407498653, 127.1082819954),
          new kakao.maps.LatLng(37.5270065970975, 127.090161269954),
          new kakao.maps.LatLng(37.5247586024644, 127.085633059433),
          new kakao.maps.LatLng(37.522951532581, 127.079974864816),
          new kakao.maps.LatLng(37.5225138223162, 127.076942368502),
        ],
      };
      state.areas.push(area);
      displayArea(area);
    };
    const addKakaoMapScript = () => {
      const script = document.createElement("script");
      /* global kakao */
      script.onload = () => kakao.maps.load(initMap);
      script.src =
        "http://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=d8a883df5abd036e1c9ac7857a408e64";
      document.head.appendChild(script);
    };

    const displayArea = (area) => {
      // 다각형을 생성합니다
      var polygon = new kakao.maps.Polygon({
        map: state.map, // 다각형을 표시할 지도 객체
        path: area.path,
        strokeWeight: 2,
        strokeColor: "#004c80",
        strokeOpacity: 0.8,
        fillColor: "#fff",
        fillOpacity: 0.7,
      });

      // 다각형에 mouseover 이벤트를 등록하고 이벤트가 발생하면 폴리곤의 채움색을 변경합니다
      // 지역명을 표시하는 커스텀오버레이를 지도위에 표시합니다
      kakao.maps.event.addListener(polygon, "mouseover", function (mouseEvent) {
        polygon.setOptions({ fillColor: "#09f" });

        state.customOverlay.setContent(
          '<div class="area">' + area.name + "</div>"
        );

        state.customOverlay.setPosition(mouseEvent.latLng);
        state.customOverlay.setMap(state.map);
      });

      // 다각형에 mousemove 이벤트를 등록하고 이벤트가 발생하면 커스텀 오버레이의 위치를 변경합니다
      kakao.maps.event.addListener(polygon, "mousemove", function (mouseEvent) {
        state.customOverlay.setPosition(mouseEvent.latLng);
      });

      // 다각형에 mouseout 이벤트를 등록하고 이벤트가 발생하면 폴리곤의 채움색을 원래색으로 변경합니다
      // 커스텀 오버레이를 지도에서 제거합니다
      kakao.maps.event.addListener(polygon, "mouseout", function () {
        polygon.setOptions({ fillColor: "#fff" });
        state.customOverlay.setMap(null);
      });

      // 다각형에 click 이벤트를 등록하고 이벤트가 발생하면 다각형의 이름과 면적을 인포윈도우에 표시합니다
      kakao.maps.event.addListener(polygon, "click", function (mouseEvent) {
        var content =
          '<div class="info">' +
          '   <div class="title">' +
          area.name +
          "</div>" +
          '   <div class="size">총 면적 : 약 ' +
          Math.floor(polygon.getArea()) +
          " m<sup>2</sup></area>" +
          "</div>";

        state.infowindow.setContent(content);
        state.infowindow.setPosition(mouseEvent.latLng);
        state.infowindow.setMap(state.map);
      });
    };

    const getXY = () => {
      let element2 = document.getElementById("selectedApt_wrap");
      let elementTop2 =
        window.pageYOffset + element2.getBoundingClientRect().top;
      console.log(elementTop2); // 1000
    };

    const isCollide = () => {};

    return {
      state,
      initMap,
      addKakaoMapScript,
      displayArea,
      getXY,
      isCollide,
    };
  },
};
</script>

<style>
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
</style>
