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
  <button
    type="button"
    class="btn btn-primary text-black"
    data-bs-toggle="modal"
    data-bs-target="#modal"
  >
    참여 확인
  </button>
  <!-- Modal -->
  <div
    class="modal fade"
    id="modal"
    tabindex="-1"
    aria-labelledby="modalLabel"
    aria-hidden="true"
  >
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="modalLabel">
            당신의 일정에 제목을 정해주세요.
          </h5>
          <button
            type="button"
            class="btn-close"
            data-bs-dismiss="modal"
            aria-label="Close"
          ></button>
        </div>
        <form @submit.prevent="createBoard">
          <div class="modal-body">
            <input
              type="text"
              class="form-control"
              v-model="boardName"
              placeholder="여행 제목을 입력해주세요"
              required
              @keyup.enter="createBoard"
            />
          </div>
          <div class="modal-footer">
            <button
              type="button"
              class="btn btn-secondary"
              data-bs-dismiss="modal"
            >
              Close
            </button>
            <button type="submit" class="btn btn-primary">START</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import Map from "@/views/Map.vue";
import Memo from "@/views/Memo.vue";
import webRTC from "@/views/WebRTC.vue";
import BoardButtons from "@/views/components/BoardButtons.vue";
import axios from "axios";
import { API_BASE_URL } from "@/config/index.js";
import { mapGetters } from "vuex";

const userStore = "userStore";
export default {
  data() {
    return {
      boardId: "",
      popup: false,
    };
  },
  components: {
    Map,
    Memo,
    webRTC,
    BoardButtons,
  },
  created() {
    console.log(this.$refs.Map);
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
          userId: this.getUserId(),
        },
      }).then((res) => {
        console.log(res.data.included);
        if (!res.data.included) {
          // 일정에 새로 들어온 사람이면 같이 일정을 짤건지 물어보는 팝업 띄움
          this.popup = true;
        }
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
