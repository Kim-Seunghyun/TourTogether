<template>
  <div class="d-flex flex-column">
    <div class="d-flex flex-row-reverse">
      <button
        class="c-btn btn-green"
        data-bs-toggle="modal"
        data-bs-target="#inviteModal"
      >
        친구 초대 👭
      </button>
      <button class="c-btn btn-green" @click="togglePlan()">
        일정 확인 🗓️
      </button>
    </div>
    <div class="d-flex flex-row-reverse">
      <button
        class="c-btn btn-green"
        data-bs-toggle="modal"
        data-bs-target="#finishModal"
      >
        일정 완료 💾
      </button>
      <button type="button" class="c-btn btn-green" @click="writeMemo()">
        메모 작성 📄
      </button>
    </div>
    <div class="d-flex flex-row-reverse">
      <button type="button" class="c-btn btn-red" @click="goToDashboard()">
        회의 종료 🚪
      </button>
    </div>
  </div>
  <!-- Modal -->
  <div
    class="modal fade"
    id="inviteModal"
    tabindex="-1"
    aria-labelledby="inviteModal"
    aria-hidden="true"
  >
    <div
      class="modal-dialog modal-dialog-centered modal-dialog-scrollable modal-lg"
    >
      <div class="modal-content">
        <div
          class="modal-header"
          style="border-bottom: none; margin-left: 10px"
        >
          <h5 class="modal-title" id="inviteModal">
            📎 링크를 복사해서 친구에게 전송하세요!
          </h5>
        </div>
        <div
          id="myInputDiv"
          style="text-align: left; margin-left: 25px; margin-right: 25px"
        >
          <input
            id="myInput"
            class="form-control input-lg"
            :value="this.curURL"
            readonly
          />
        </div>
        <div class="modal-footer" style="border-top: none">
          <button
            type="button"
            class="btn btn-secondary"
            data-bs-dismiss="modal"
          >
            Close
          </button>
          <button
            type="button"
            class="btn"
            @click="copyToClipboard()"
            style="background-color: rgb(125 185 233); color: white"
          >
            COPY
          </button>
        </div>
      </div>
    </div>
  </div>
  <!-- Modal -->
  <div
    class="modal fade"
    id="finishModal"
    tabindex="-1"
    aria-labelledby="exampleModalLabel"
    aria-hidden="true"
  >
    <div class="modal-dialog modal-dialog-centered modal-lg">
      <div class="modal-content">
        <div class="modal-header" style="border-bottom: none">
          <h5 class="modal-title" id="exampleModalLabel">
            🙂 여행 유형을 선택하고 일정을 마무리하세요!
          </h5>
          <button
            type="button"
            class="btn-close"
            data-bs-dismiss="modal"
            aria-label="Close"
          ></button>
        </div>
        <div class="modal-body">
          <div class="d-flex p-2 bd-highlight select-title">동행인 👬</div>
          <div class="nav-wrapper position-relative end-0">
            <!-- 사람유형 -->
            <ul class="nav nav-pills nav-fill p-1" role="tablist" data-id="hi">
              <li
                class="nav-item"
                v-for="(item, index) in withWhomArr"
                :key="item"
                @click="selectWithWhom(index)"
              >
                <a
                  class="nav-link mb-0 px-0 py-1 active"
                  data-bs-toggle="tab"
                  role="tab"
                  aria-selected="true"
                  style="cursor: pointer"
                >
                  # {{ item }}
                </a>
              </li>
            </ul>
          </div>
          <div class="d-flex p-2 bd-highlight select-title">계절 🌸</div>
          <div class="nav-wrapper position-relative end-0">
            <!-- 계절유형 -->
            <ul class="nav nav-pills nav-fill p-1" role="tablist" data-id="hi">
              <li
                class="nav-item"
                v-for="(item, index) in seasonArr"
                :key="item"
                @click="selectSeason(index)"
              >
                <a
                  class="nav-link mb-0 px-0 py-1 active"
                  data-bs-toggle="tab"
                  role="tab"
                  aria-selected="true"
                  style="cursor: pointer"
                >
                  # {{ item }}
                </a>
              </li>
            </ul>
          </div>
          <div class="d-flex p-2 bd-highlight select-title">지역 🏙️</div>
          <div class="nav-wrapper position-relative end-0">
            <!-- 지역유형 -->
            <ul class="nav nav-pills nav-fill p-1" role="tablist">
              <li
                class="nav-item"
                v-for="(item, index) in areaArr"
                :key="item"
                @click="selectArea(index)"
              >
                <a
                  class="nav-link mb-0 px-0 py-1 active"
                  data-bs-toggle="tab"
                  role="tab"
                  aria-selected="true"
                  style="cursor: pointer"
                >
                  # {{ item }}
                </a>
              </li>
            </ul>
          </div>
          <div class="d-flex p-2 bd-highlight select-title">테마 🌄</div>
          <div class="nav-wrapper position-relative end-0">
            <!-- 여행 유형 -->
            <ul class="nav nav-pills nav-fill p-1" role="tablist">
              <li
                class="nav-item"
                v-for="(item, index) in themeArr"
                :key="item"
              >
                <a
                  class="nav-link mb-0 px-0 py-1 active"
                  data-bs-toggle="tab"
                  role="tab"
                  aria-selected="true"
                  @click="selectTheme(index)"
                  style="cursor: pointer"
                >
                  # {{ item }}
                </a>
              </li>
            </ul>
          </div>
        </div>
        <div class="modal-footer" style="border-top: none">
          <button
            type="button"
            class="btn btn-secondary"
            data-bs-dismiss="modal"
          >
            Close
          </button>
          <button
            type="button"
            class="btn"
            @click="setBoardCategory()"
            style="background-color: rgb(125 185 233); color: white"
          >
            완료하기
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import setNavPills from "@/assets/js/nav-pills.js";
import axios from "axios";
import { API_BASE_URL } from "@/config/index.js";
import store from "@/store";
// import html2pdf from "html2pdf.js";

export default {
  name: "boardbuttons",
  data() {
    return {
      boardId: "",
      curURL: "",
      withWhomArr: [
        "상관없음",
        "남자끼리",
        "여자끼리",
        "연인끼리",
        "부모님과",
        "아이들과",
        "혼자",
        "기타",
      ],
      seasonArr: ["상관없음", "봄", "여름", "가을", "겨울"],
      areaArr: ["상관없음", "서울", "부산", "여수", "대구", "제주도", "기타"],
      themeArr: ["상관없음", "바다,강", "산", "액티비티", "호캉스", "기타"],
      withWhom: null,
      season: null,
      area: null,
      theme: null,
    };
  },
  created() {
    var boardRandom = window.location.pathname.substr(7);
    this.findBoardId(boardRandom);
    this.curURL = window.location.href;
  },
  mounted() {
    setNavPills();
  },
  computed() {
    this.boardFinish;
  },
  methods: {
    goToDashboard() {
      location.href = `/dashboard`;
    },
    findBoardId(boardRandom) {
      axios({
        method: "post",
        url: API_BASE_URL + "board/searchBoardIdByBoardRandom",
        data: {
          boardRandom: boardRandom,
        },
      }).then((res) => {
        this.boardId = res.data.board.boardId;
      });
    },
    selectWithWhom(index) {
      this.withWhom = index;
    },
    selectSeason(index) {
      this.season = index;
    },
    selectArea(index) {
      this.area = index;
    },
    selectTheme(index) {
      this.theme = index;
    },
    setBoardCategory() {
      axios({
        method: "post",
        url: API_BASE_URL + "board/category",
        data: {
          boardId: this.boardId,
          categoryWithWhom: this.withWhom,
          categorySeason: this.season,
          categoryArea: this.area,
          categoryTheme: this.theme,
        },
      }).then(() => {
        this.finishBoard();
      });
    },
    finishBoard() {
      const getters = store.getters;
      let tourList = getters["boardStore/getTourListFromStore"];
      let schdeuleList = [];
      let dayLen = tourList.length;
      for (let i = 0; i < dayLen; i++) {
        let schdeuleLen = tourList[i].list.length;
        for (let j = 0; j < schdeuleLen; j++) {
          let obj = {};
          obj.scheduleDay = i + 1;
          obj.scheduleOrd = j + 1;
          obj.scheduleBoardId = this.boardId;
          obj.scheduleTourSpotId = tourList[i].list[j].index;
          obj.scheduleAdditional = tourList[i].list[j].name;
          schdeuleList.push(obj);
        }
      }
      axios({
        method: "patch",
        url: API_BASE_URL + "board/finish",
        data: {
          boardId: this.boardId,
          scheduleList: schdeuleList,
        },
      }).then(() => {
        location.href = `/dashboard`;
      });
    },
    copyToClipboard() {
      const copyText = document.getElementById("myInput");
      copyText.select();
      document.execCommand("copy");
      alert(copyText.value + "을 복사했습니다.");
    },
    // exportToPDF() {
    //   //여기서 map.vue clickPDF함수 실행시키고 map.vue에서 맵그리고 html2pdf다운로드 하게하기 => best => 다른component의 함수 호출하기
    //   //Map.clickPdf() 호출 하면
    //   //emit 이부분을 div(초기화 한 후 핀만 찍은 새로운 맵)해서 받아오자 새창띄워서 div넘기고 사진찍기
    //   //window.scrollTo(0, 0);
    //   html2pdf(this.$refs.pdfarea, {
    //     margin: 0,
    //     filename: "document.pdf",
    //     image: { type: "jpg", quality: 0.95 },
    //     //	allowTaint 옵션추가
    //     html2canvas: {
    //       useCORS: true,
    //       scrollY: 0,
    //       scale: 1,
    //       dpi: 300,
    //       letterRendering: true,
    //       allowTaint: false, //useCORS를 true로 설정 시 반드시 allowTaint를 false처리 해주어야함
    //     },
    //     jsPDF: {
    //       orientation: "portrait",
    //       unit: "mm",
    //       format: "a4",
    //       compressPDF: true,
    //     },
    //   });
    // },

    writeMemo() {
      // 작업장 workspace 나중에 지우기
      // 1. 누르면 해당 div가 생겼다 없어졌다
      // 2. 되고나면 위치, 사이즈 조절 추가해보기
      // $("memo_wrapper").fadeOut(1000);;
      store.commit("setMemovisible");
    },
    togglePlan() {
      let flag = !store.getters["boardStore/getPlanFlag"];
      store.commit("boardStore/setPlanFlag", flag);
    },
  },
};
</script>

<style scoped>
#myInput {
  border: none;
  border-radius: 5px;
  padding: 5px;
  background-color: #e7e7e8;
  font-size: 20px;
}

.modal-content {
  width: 700px;
  padding: 2px;
}

.c-btn {
  border: none;
  display: inline-block;
  padding: 5px 10px;
  margin: 5px;
  border-radius: 5px;
  font-family: "paybooc-Light", sans-serif;
  text-decoration: none;
  font-weight: 600;
  transition: 0.25s;
  font-size: 13px;
  width: 100px;
  height: 30px;
}
.btn-red {
  background-color: red;
}
.btn-green {
  background-color: #77af9c;
  color: #d7fff1;
}
.select-title {
  font-weight: bold;
}
</style>
