<template>
  <div id="root">
    <button @click="addTour()">관광지 추가</button>
    <button @click="addDay()">일정 하루 추가</button>
    <button @click="deleteDay(0)">일정 전체 삭제</button>
    <button @click="emitTest()">emitTest</button>
    <button @click="emitDay()">현재 Day 정보</button>
    <div>
      <ul id="day_wrap" class="list">
        <div v-for="item in state.tourList" v-bind:key="item.list">
          <!-- <li v-if="item" class="day">
            <div class="day_item">
              {{ item }}
            </div>
          </li> -->
          <ul class="day_item day sort" @click="showPlan(item.index)">
            {{
              item.index + 1
            }}일차
            <div v-if="state.selectedIndex === item.index">
              <p
                class="tour_item"
                v-for="tourItem in item.list"
                v-bind:key="tourItem"
              >
                {{ tourItem.name }}
              </p>
            </div>
          </ul>
        </div>
      </ul>
    </div>
  </div>
</template>

<script>
import { reactive } from "@vue/reactivity";
import { onMounted, watch } from "@vue/runtime-core";
import $ from "jquery";
import "jquery-ui/ui/widgets/sortable";

export default {
  name: "Plan",
  props: {
    tourData: Object,
    //아이디 장소 날짜
  },
  // emits: ["emitTest"],
  setup(props, { emit }) {
    const tour = [
      {
        tour_spot_name: "강동몽돌해변",
        day: 1,
      },
      {
        tour_spot_name: "감자꽃 스튜디오",
        day: 2,
      },
      {
        tour_spot_name: "광나루한강공원",
        day: 3,
      },
      {
        tour_spot_name: "광명동굴",
        day: 1,
      },
      {
        tour_spot_name: "한강",
        day: 2,
      },
      {
        tour_spot_name: "북한강",
        day: 2,
      },
    ];
    let size = 0;
    // const $ = jQuery;

    const state = reactive({
      tourList: null,
      td: null,
      selectedIndex: null,
      propsData: null,
    });
    const addTour = () => {
      state.td = tour[size];
      size++;
      if (size >= 6) size = 0;
    };
    const addDay = () => {
      let len = state.tourList.length;
      state.tourList[len] = { list: new Array(), index: len };
    };
    const deleteDay = (num) => {
      if (num == 0) {
        let len = state.tourList.length;
        state.tourList.splice(0, len);
      } else {
        state.tourList.splice(num, 1);
      }
    };
    const showPlan = (num) => {
      state.selectedIndex = num;
      makeSortable();
    };
    const blockRightClick = () => {
      document
        .getElementById("root")
        .addEventListener("contextmenu", (event) => event.preventDefault());
      // document.body.addEventListener("contextmenu", (event) =>
      //   event.preventDefault()
      // );
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
          reIndexing(len);
          state.selectedIndex = next;
        },
      });
    };
    const reIndexing = (len) => {
      for (let i = 0; i < len; i++) {
        state.tourList[i].index = i;
      }
    };
    const emitTest = () => {
      emit("getLine", state.tourList[state.selectedIndex]);
    };
    onMounted(() => {
      state.tourList = [];
      state.selectedIndex = 0;
      blockRightClick();
      makeSortable();
      reIndexing();
      // addDay();
    });
    const emitDay = () => {
      emit("getDay", state.tourList.length);
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
        });
        //console.log(state.tourList);
        /*
        TODO
        Redis 로 보낼때
        schdeule: {
          scheduleId: 1,
          boardId: 1,
          day: 1,
          userSpotId: 1,
          tourSpotId: 1,
          order: 1,
          additional: "변경",
        }
        형식으로 보내줘야함..... 1개씩 따로 for문 돌려서 보낼지
        뭔가 캐싱 할 수 있는 방법이 있을지 고민 해봐야됨. 근데 캐싱하면 다른사람이 바꿨을 때 내 캐싱값이 바껴야되는데 저게 되나?
        캐싱이 안된다면 Redis DB값을 먼저 조회 해서 달라진 값들 비교해서 다를것들 모아서 다시 select 걸어주는 방법도 있는데 이건 또 생각 해봐야됨 뭔가 되게 복잡해질듯

        */
      }
    );
    return {
      state,
      addTour,
      addDay,
      deleteDay,
      showPlan,
      blockRightClick,
      makeSortable,
      emitTest,
      emitDay,
    };
  },
};
</script>

<style>
.day {
  list-style: none;
  padding-top: 10%;
}
.day_item {
  position: relative;
  border-bottom: 1px solid #888;
  overflow: hidden;
  cursor: pointer;
  min-height: 5vh;
  z-index: 2;
}
.tour_item {
  border: 1px solid #888;
  cursor: pointer;
  border-radius: 15%;
  margin: 1%;
  z-index: 3;
  color: rgb(180, 179, 179);
}
#root {
  width: 100%;
  height: 100%;
}
</style>
