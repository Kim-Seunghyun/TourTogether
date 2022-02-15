<template>
  <div id="root">
    <button @click="addDay()">일정 하루 추가</button>
    <button @click="deleteDay(0)">일정 전체 삭제</button>
    <div>
      <ul id="day_wrap" class="list">
        <div v-for="item in state.tourList" v-bind:key="item.list">
          <div class="day_item day sort" @click="showPlan(item.index)">
            {{ item.index + 1 }}일차
            <div v-if="state.selectedIndex === item.index">
              <div v-for="tourItem in item.list" v-bind:key="tourItem">
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
                  <img
                    src="https://user-images.githubusercontent.com/63468607/153790617-0311fbca-8b5e-44ed-a30b-3415bf1c45d1.png"
                    class="up_image"
                    @click="listUp(item.index, tourItem.id)"
                    style="display: none"
                  />
                  <img
                    src="https://user-images.githubusercontent.com/63468607/153790551-4d34a662-f7f9-47f9-953a-837ffcde6a1a.png"
                    class="down_image"
                    @click="listDown(item.index, tourItem.id)"
                    style="display: none"
                  />
                </div>
              </div>
            </div>
          </div>
        </div>
      </ul>
    </div>
  </div>
</template>

<script>
import { reactive } from "vue";
import { onMounted, watch } from "vue";
import $ from "jquery";
import "jquery-ui/ui/widgets/sortable";

export default {
  name: "Plan",
  props: {
    tourData: Object,
    //아이디 장소 날짜
  },
  // emits: ["emitLine"],
  setup(props, { emit }) {
    const state = reactive({
      tourList: [],
      selectedIndex: null,
      propsData: null,
    });
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
      state.tourList[0] = { list: new Array(), index: 0 };
      // addDay();
      emit("getDay", state.tourList.length);
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
    };
    const showComponent = (event) => {
      const target = event.target;
      $(target).children("img").css("display", "");
    };
    const blockComponent = (event) => {
      const target = event.target;
      $(target).children("img").css("display", "none");
    };
    const listUp = (day, index) => {
      if (index === 0) return;
      let len = state.tourList[day].list.length;
      [state.tourList[day].list[index], state.tourList[day].list[index - 1]] = [
        state.tourList[day].list[index - 1],
        state.tourList[day].list[index],
      ];
      reIndexingTour(day, len);
    };
    const listDown = (day, index) => {
      let len = state.tourList[day].list.length;
      if (index + 1 === len) return;
      [state.tourList[day].list[index], state.tourList[day].list[index + 1]] = [
        state.tourList[day].list[index + 1],
        state.tourList[day].list[index],
      ];
      reIndexingTour(day, len);
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
        goEmit();
        // console.log(state.tourList);
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
      listUp,
      listDown,
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
  border-radius: 5px;
  margin: 1%;
  z-index: 3;
  background-color: rgb(238, 231, 231);
  /* box-shadow: 1px 1px 1px 1px gray; */
  border-collapse: collapse;
}
#root {
  width: 100%;
  height: 100%;
}
.close_image {
  z-index: 5;
  width: 25px;
  height: 25px;
  right: 1px;
  top: 1px;
  float: right;
  position: relative;
}
.down_image {
  z-index: 5;
  width: 25px;
  height: 25px;
  top: 1%;
  float: left;
  position: relative;
}
.up_image {
  z-index: 5;
  width: 25px;
  height: 25px;
  top: 99%;
  float: left;
  position: relative;
}
/* .left {
  border-radius: 50% 0 0 50%;
  border: 1px solid #888;
  background-color: rgb(238, 231, 231);
}
.right {
  border-radius: 0 50% 50% 0;
  border: 1px solid #888;
  background-color: rgb(238, 231, 231);
} */
</style>
