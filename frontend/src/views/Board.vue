<template>
  <div id="whole_wrapper">
    <div id="map_wrapper">

      <Map />

    </div>
    <!-- <div id="memo_wrapper">
      <Memo />
    </div> -->
    <div class="container" style="padding: 0;">
      <div id="webRTC" class="col-9">
        <webRTC />
      </div>
      <div id="buttons" class="col-3">
        <board-buttons />
      </div>
    </div>
  </div>
</template>

<script>
import Map from "@/views/Map.vue";
// import Memo from "@/views/Memo.vue";
import webRTC from "@/views/WebRTC.vue";
import BoardButtons from "@/views/components/BoardButtons.vue";
import axios from "axios";
import { API_BASE_URL } from "@/config/index.js";
import { mapGetters } from "vuex";
const userStore = "userStore";
export default {
  data() {
    return {
      boardName: "",
      boardId: "",
      popup: false,
    };
  },
  components: {
    Map,
    // Memo,
    webRTC,
    BoardButtons,
  },
  created() {
    var boardRandom = window.location.pathname.substr(7);
    this.findBoardId(boardRandom);
    // this.checkUser();
  },

  methods: {
    ...mapGetters(userStore, ["getUserId"]),
    findBoardId(boardRandom) {
      axios({
        method: "post",
        url: API_BASE_URL + "board/searchBoardIdByBoardRandom",
        data: {
          boardRandom: boardRandom,
        },
      }).then((res) => {
        // console.log(res.data);
        this.boardId = res.data.board.boardId;
        this.boardName = res.data.board.boardName;
        this.checkUser();
      });
    },
    checkUser() {
      axios({
        method: "post",
        url: API_BASE_URL + "board/searchParticipant",
        data: {
          boardId: this.boardId,
          userId: this.getUserId(),
        },
      }).then((res) => {
        console.log(res.data.included);
        if (!res.data.included) {
          this.addParticipant();
        }
      });
    },
    addParticipant() {
      console.log("add Participant!!!");
      axios({
        method: "post",
        url: API_BASE_URL + "board/addParticipant",
        data: {
          boardId: this.boardId,
          userId: this.getUserId(),
        },
      }).then((res) => {
        console.log(res);
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
  /* left: 2%; */
  position: relative;
  margin: 0 auto;
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

.container {
  display: flex;
}
</style>
