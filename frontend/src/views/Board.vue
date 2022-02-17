<template>
  <div id="whole_wrapper">
    <div id="map_wrapper">
      <Map />
    </div>
    <draggable-div v-show="memovisible" id="memo_wrapper" class="resize">
      <template v-slot:header> 메모장 헤더 </template>
      <template v-slot:main> <memo /> </template>
      <!-- <template v-slot:footer> footer </template> -->
    </draggable-div>

    <div class="container">
      <div id="webRTC">
        <webRTC />
      </div>
      <div id="buttons">
        <board-buttons />
      </div>
    </div>
  </div>
</template>

<script>
import Map from "@/views/Map.vue";
import Memo from "@/views/Memo.vue";
import webRTC from "@/views/WebRTC.vue";
import BoardButtons from "@/views/components/BoardButtons.vue";
import DraggableDiv from "@/components/DraggableDiv.vue";
import axios from "axios";
import { API_BASE_URL } from "@/config/index.js";
import { mapGetters } from "vuex";
import store from "@/store";
const userStore = "userStore";
export default {
  beforeMount() {
    this.$store.state.hideConfigButton = true;
    this.$store.state.showSidenav = false;
    this.$store.state.showFooter = false;
    // body.classList.remove("bg-gray-100");
  },
  beforeUnmount() {
    this.$store.state.hideConfigButton = false;
    this.$store.state.showSidenav = true;
    this.$store.state.showFooter = true;
    // body.classList.add("bg-gray-100");
  },
  data() {
    return {
      boardName: "",
      boardId: "",
      popup: false,
    };
  },
  components: {
    Map,
    Memo,
    webRTC,
    BoardButtons,
    DraggableDiv,
  },

  computed: {
    memovisible() {
      const memovisible = store.state.memovisible;
      return memovisible;
    },
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
        if (!res.data.included) {
          this.addParticipant();
        }
      });
    },
    addParticipant() {
      axios({
        method: "post",
        url: API_BASE_URL + "board/addParticipant",
        data: {
          boardId: this.boardId,
          userId: this.getUserId(),
        },
      }).then(() => {});
    },
  },
};
</script>

<style scoped>
#whole_wrapper {
  width: 100%;
  /* height: 100%; */
  /* margin-bottom: 35px; */
  /* display: flex; */
  /* flex-direction: column; */
  /* background: #999; */
  z-index: 15001;
}
#map_wrapper {
  /* left: 2%; */
  width: 100%;
  /* height: 100%; */
  /* display: flex; */
  position: relative;
  /* margin: 0 0; */
  /* flex: 1; */
}

#memo_wrapper {
  width: 20%;
  height: 30%;
  overflow: scroll;
  position: absolute;
  bottom: 0;
  right: 0;
  border: solid 1px #888;
  z-index: 51;
  background-color: white;
}
.resize {
  overflow: hidden;
  resize: both;
  background-color: #c3e2ce;
}
.container {
  padding-left: 0;
  padding-right: 0;
  display: flex;
  position: FIXED;
  bottom: 0;
  height: 120px;
  width: 100%;
  /* background: white; */
  z-index: 15002;
}
#buttons {
  height: 120px;
  width: 240px;
  position: FIXED;
  right: 0;
  bottom: 0;
  /* background: white; */
  z-index: 15003;
}
</style>
