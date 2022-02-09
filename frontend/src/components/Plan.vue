<template>
  <div>
    <button @click="addTour()">관광지 추가</button>
    <button @click="addDay()">일정 하루 추가</button>
    <button @click="deleteDay()">일정 하루 삭제</button>
    <button @click="deleteDay()">일정 전체 삭제</button>
  </div>
</template>

<script>
import { reactive } from "@vue/reactivity";
import { onMounted, watch } from "@vue/runtime-core";
export default {
  name: "Plan",
  props: {
    tourData: Object,
    //아이디 장소 날짜
  },
  setup() {
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
    let totalSize = 0;
    const state = reactive({
      tourList: null,
      td: null,
    });
    const addTour = () => {
      state.td = tour[size];
      size++;
    };
    const addDay = () => {
      totalSize++;
      state.tourList[totalSize] = new Array();
    };
    const deleteDay = () => {};

    onMounted(() => {
      state.tourList = [];
      addDay();
    });
    watch(
      () => state.td,
      () => {
        const day = state.td.day;
        const name = state.td.tour_spot_name;
        if (state.tourList[day] === undefined) {
          state.tourList[day] = new Array();
        }
        const len = state.tourList[day].length;
        state.tourList[day][len] = name;
        console.log(state.tourList);
      }
    );
    return {
      state,
      addTour,
      addDay,
      deleteDay,
    };
  },
};
</script>

<style></style>
