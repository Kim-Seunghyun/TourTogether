<template>
  <div id="root">
    <ul id="day_wrap" class="list">
      <div v-for="item in state.tourList" v-bind:key="item.list">
        <div
          class="day_item day sort"
          @click="showPlan(item.index)"
          @mouseover="showComponent($event)"
          @mouseout="blockComponent($event)"
        >
          <img
            src="https://user-images.githubusercontent.com/63468607/153530715-d5123829-4dc5-4a63-86d0-6b156fa0bd38.png"
            class="close_image"
            @click="deleteDay(item.index)"
            @mouseover="mouseOverImg($event)"
            @mouseout="mouseOutImg($event)"
            style="display: none"
          />
          {{ item.index + 1 }}일차
          <div v-if="state.selectedIndex === item.index" class="selected">
            <div
              v-for="tourItem in item.list"
              v-bind:key="tourItem"
              class="relative"
            >
              <div
                class="tour_item"
                @mouseenter="showComponent($event)"
                @mouseleave="blockComponent($event)"
              >
                {{ tourItem.name }}
                <img
                  src="https://user-images.githubusercontent.com/63468607/153530715-d5123829-4dc5-4a63-86d0-6b156fa0bd38.png"
                  class="close_image"
                  @click="deleteTour(item.index, tourItem.id)"
                  style="display: none"
                />
              </div>
              <div
                v-if="
                  tourItem.id !== state.tourList[item.index].list.length - 1
                "
              >
                <img
                  src="https://user-images.githubusercontent.com/63468607/153804094-71229922-2e1e-4186-ba14-a4c6db03ae19.png"
                  class="swap_image"
                  @click="listSwap(item.index, tourItem.id)"
                  @mouseenter="chageColorImage($event)"
                  @mouseleave="reChangeImage($event)"
                />
              </div>
            </div>
          </div>
        </div>
      </div>
    </ul>
    <div class="button_wrapper">
      <div>
        <button
          @click="addDay()"
          class="btn"
          style="
            background-color: rgb(125 185 233);
            color: white;
            margin-left: 0px;
          "
        >
          일정 추가
        </button>
      </div>
      <div>
        <button
          @click="deleteDay(0)"
          class="btn btn-secondary"
          style="margin-left: 0px"
        >
          일정 전체 삭제
        </button>
      </div>
    </div>
  </div>
  <div id="my_context" style="display: none; z-index: 500">
    <button class="btn btn-danger btn-sm">삭제</button>
  </div>
</template>

<script>
import { reactive } from "vue";
import { onMounted, watch } from "vue";
import axios from "axios";
import $ from "jquery";
import "jquery-ui/ui/widgets/sortable";
import SockJS from "sockjs-client";
import Stomp from "stomp-websocket";
import { API_BASE_URL } from "@/config/index.js";
import { useStore } from "vuex";

let sock = new SockJS(API_BASE_URL + "ws-stomp");

export default {
  name: "Plan",
  props: {
    tourData: Object,
    //아이디 장소 날짜 인덱스
  },

  setup(props, { emit }) {
    const store = useStore();
    const getters = store.getters;

    const state = reactive({
      tourList: [], //일정 배열
      selectedIndex: null, //현재 보고 있는 Day
      user: getters["userStore/getUserNickname"], //유저 정보
      my: true, // 실험중
      ws: Stomp.over(sock), //webSocket
      id: window.location.pathname.split("/")[2], //sessionId
    });
    /*
      addDay : 일정 하루 추가
      설명 : 일정 하루 추가 후 Map.vue으로 emit요청
    */
    const addDay = () => {
      let len = state.tourList.length;
      state.tourList[len] = { list: new Array(), index: len };
      goEmit();
    };
    const deleteDay = (num) => {
      if (num == 0) {
        let len = state.tourList.length;
        state.tourList.splice(0, len);
        addDay();
      } else {
        state.tourList.splice(num, 1);
        let len = state.tourList.length;
        reIndexingDay(len);
      }
      goEmit();
    };
    const showPlan = (num) => {
      state.selectedIndex = num;
      goEmit();
      // makeSortable();
    };
    const blockRightClick = () => {
      document
        .getElementById("root")
        .addEventListener("contextmenu", (event) => event.preventDefault());
    };
    const makeSortable = () => {
      let prev = 0;
      $(".list").sortable({
        item: $(".sort"),
        start: function (event, ui) {
          prev = ui.item.index();
        },
        stop: function (event, ui) {
          let next = ui.item.index();
          let arr = JSON.parse(JSON.stringify(state.tourList[prev]));
          if (prev < next) {
            state.tourList.splice(next + 1, 0, arr);
            state.tourList.splice(prev, 1);
          } else {
            state.tourList.splice(prev, 1);
            state.tourList.splice(next, 0, arr);
          }
          let len = state.tourList.length;
          reIndexingDay(len);
          state.selectedIndex = next;
          goEmit();
        },
      });
    };
    const makeTourSortable = () => {
      let prev = 0;
      $(".sort").sortable({
        item: $(".tour_item"),
        start: function (event, ui) {
          prev = ui.item.index();
        },
        stop: function (event, ui) {
          let index = state.selectedIndex;
          let next = ui.item.index();
          let arr = JSON.parse(JSON.stringify(state.tourList[index].list));
          if (prev < next) {
            state.tourList[index].list.splice(next + 1, 0, arr);
            state.tourList[index].list.splice(prev, 1);
          } else {
            state.tourList[index].list.splice(prev, 1);
            state.tourList[index].list.splice(next, 0, arr);
          }
          let len = state.tourList[index].list.length;
          reIndexingTour(index, len);
        },
      });
    };
    const reIndexingDay = (len) => {
      for (let i = 0; i < len; i++) {
        state.tourList[i].index = i;
      }
      goEmit();
    };
    const reIndexingTour = (day, len) => {
      for (let i = 0; i < len; i++) {
        state.tourList[day].list[i].id = i;
      }
      goEmit();
    };
    const emitLine = () => {
      emit("getLine", state.tourList[state.selectedIndex]);
    };
    onMounted(() => {
      state.tourList = [];
      state.selectedIndex = 0;
      // state.tourList[0] = { list: new Array(), index: 0 };/
      // addDay();
      init();
      blockRightClick();
      makeSortable();
    });
    const emitDay = () => {
      emit("getDay", state.tourList.length);
    };
    const deleteTour = (day, index) => {
      state.tourList[day].list.splice(index, 1);
      let len = state.tourList[day].list.length;
      reIndexingTour(day, len);
    };
    const goEmit = () => {
      emitDay();
      emitLine();
      sendMessage();
      setStateTour();
      //redis로 보내기
    };
    //redis 에서 받기
    const showComponent = (event) => {
      const target = event.target;
      $(target).children("img:eq(0)").css("display", "");
    };
    const blockComponent = (event) => {
      const target = event.target;
      $(target).children("img:eq(0)").css("display", "none");
    };
    const listSwap = (day, index) => {
      let len = state.tourList[day].list.length;
      [state.tourList[day].list[index], state.tourList[day].list[index + 1]] = [
        state.tourList[day].list[index + 1],
        state.tourList[day].list[index],
      ];
      reIndexingTour(day, len);
    };
    const chageColorImage = (event) => {
      const target = event.target;
      $(target).attr(
        "src",
        "https://user-images.githubusercontent.com/63468607/153891222-2de71a0e-b047-43e4-a467-ad6779db5c94.png"
      );
    };
    const reChangeImage = (event) => {
      const target = event.target;
      $(target).attr(
        "src",
        "https://user-images.githubusercontent.com/63468607/153804094-71229922-2e1e-4186-ba14-a4c6db03ae19.png"
      );
    };
    const sendMessage = function () {
      state.ws.send(
        "/api/pub/schedule",
        {},
        JSON.stringify({
          roomId: state.id,
          userId: state.user,
          content: JSON.stringify(state.tourList),
        })
      );
    };
    const updateList = (response) => {
      state.tourList = JSON.parse(response.content);
      emitDay();
      emitLine();
    };
    const init = () => {
      axios({
        method: "post",
        url: API_BASE_URL + "schedule/room",
        params: {
          id: window.location.pathname.split("/")[2],
          user: state.user,
        },
      })
        .then((response) => {
          if (!response.data.schedule.scheduleContent) {
            addDay();
            return;
          }
          let res = {};
          res.content = response.data.schedule.scheduleContent;
          updateList(res);
        })
        .catch((error) => {
          console.log(error);
        });
      //시작하면 DB에서 기존에 작업 했던 것 불러 와야함
      var subUrl = "/api/sub/schedule/" + state.id;
      var ws = Stomp.over(sock);
      state.ws = ws;
      ws.connect(
        {
          userNickname: state.user,
        },
        function () {
          ws.subscribe(subUrl, function (message) {
            var resMessage = JSON.parse(message.body);
            updateList(resMessage);
          });
        },
        function (error) {
          console.log(error);
        }
      );
    };
    const mouseOverImg = (event) => {
      const target = event.target;
      $(target).css("display", "");
    };
    const mouseOutImg = (event) => {
      const target = event.target;
      $(target).css("display", "none");
    };
    const setStateTour = () => {
      let tourList = [];
      //양식에 맞게 tourList에 저장
      tourList = state.tourList;
      store.commit("boardStore/setTourList", tourList);
    };
    watch(
      () => props.tourData,
      () => {
        var day = props.tourData.day - 1;
        var name = props.tourData.name;
        if (state.tourList[day] === undefined) {
          state.tourList[day] = { list: new Array(), index: day };
        }
        var len = state.tourList[day].list.length;
        state.tourList[day].list.push({
          name: name,
          id: len,
          lat: props.tourData.lat,
          lng: props.tourData.lng,
          index: props.tourData.index,
        });
        state.selectedIndex = day;
        goEmit();
      }
    );
    return {
      state,
      addDay,
      deleteDay,
      showPlan,
      blockRightClick,
      makeSortable,
      emitLine,
      emitDay,
      deleteTour,
      reIndexingDay,
      reIndexingTour,
      goEmit,
      makeTourSortable,
      showComponent,
      blockComponent,
      listSwap,
      chageColorImage,
      reChangeImage,
      sendMessage,
      init,
      updateList,
      setStateTour,
      mouseOverImg,
      mouseOutImg,
    };
  },
};
</script>

<style scoped>
.day {
  list-style: none;
  padding-top: 5%;
  background-color: rgba(255, 255, 255, 0.849);
  border-radius: 5px;
  border-top: 1px solid rgb(187, 187, 187);
  border-left: 1px solid rgb(187, 187, 187);
}
.day_item {
  position: relative;
  border-bottom: 1px solid #888;
  /* overflow: hidden; */
  cursor: pointer;
  min-height: 5vh;
  z-index: 2;
  margin: auto;
}
.tour_item {
  border: 1px solid rgb(187, 187, 187);
  cursor: pointer;
  border-radius: 5px;
  margin: 3px;
  padding-right: 0;
  z-index: 3;
  background-color: rgba(250, 250, 250, 0.95);
  /* box-shadow: 1px 1px 1px 1px gray; */
  /* border-collapse: collapse; */
  /* position: relative; */
}
#root {
  width: 100%;
  height: 100%;
  overflow: hidden;
}
.close_image {
  z-index: 5;
  width: 20px;
  height: 20px;
  right: -3px;
  top: -5px;
  margin-top: -15 px;
  float: right;
  position: absolute;
}
.swap_image {
  position: absolute;
  width: 25px;
  height: 25px;
  bottom: 0;
  margin-bottom: -12px;
  left: 10%;
  cursor: pointer;
  z-index: 15000;
  overflow: visible;
  opacity: 0.8;
}
.relative {
  position: relative;
  overflow: visible;
}

.button_wrapper {
  position: absolute;
  bottom: 0;
  left: 25%;
}

.selected {
  border-radius: 1px solid black;
}
#day_wrap {
  margin-top: 5px;
  margin-bottom: 5px;
  padding-left: 16px;
  padding-right: 16px;
  height: 60%;
  overflow-y: scroll;
}
</style>
