<template>
  <div id="whole_wrapper">
    <div id="map_wrapper">
      <Map />
    </div>
    <div id="memo_wrapper">
      <Memo />
    </div>
    <div id="webrtc_wrapper">
      <webRTC />
      <board-buttons />
    </div>
  </div>
</template>

<script>
import Map from "@/views/Map.vue";
import Memo from "@/views/Memo.vue";
import webRTC from "@/views/WebRTC.vue";
import BoardButtons from "@/views/components/BoardButtons.vue";
import { mapState } from "vuex";
import axios from "axios";
import { API_BASE_URL } from "@/config/index.js";

const userStore = "userStore";
export default {
  data() {
    return {
      boardId: "",
    };
  },
  components: {
    Map,
    Memo,
    webRTC,
    BoardButtons,
  },
  created() {
    var boardRandom = window.location.pathname.substr(7);
    this.findBoardId(boardRandom);
    // this.checkUser();
  },
  methods: {
    ...mapState(userStore, "userId"),
    findBoardId(boardRandom) {
      axios({
        method: "post",
        url: API_BASE_URL + "board/searchBoardIdByBoardRandom",
        data: {
          boardRandom: boardRandom,
        },
      }).then((res) => {
        // console.log(res.data);
        this.boardId = res.data.boardId;
        this.checkUser();
      });
    },
    checkUser() {
      axios({
        method: "post",
        url: API_BASE_URL + "board/searchParticipant",
        data: {
          boardId: this.boardId,
          userId: this.userId,
        },
      }).then((res) => {
        console.log(res.data.isIncluded);
      });
    },
  },
};
</script>

<style>
#whole_wrapper {
  width: 100%;
  height: 100%;
}
#map_wrapper {
  width: 95%;
  height: 60%;
  left: 2%;
  position: relative;
}
#memo_wrapper {
  width: 20%;
  height: 30%;
  overflow: scroll;
  position: absolute;
  top: 75%;
  left: 77%;
  border: solid 1px #888;
  z-index: 3;
  background-color: white;
}
#webrtc_wrapper {
}
</style>
