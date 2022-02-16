<template>
  <button
    class="c-btn btn-green"
    data-bs-toggle="modal"
    data-bs-target="#inviteModal"
  >
    친구 초대 👭
  </button>
  &nbsp;
  <button
    class="c-btn btn-green"
    data-bs-toggle="modal"
    data-bs-target="#finishModal"
  >
    일정 완료 💾
  </button>
  &nbsp;
  <button type="button" class="c-btn btn-green" @click="exportToPDF()">
    PDF 저장 📄
  </button>
  &nbsp;
  <button type="button" class="c-btn btn-green">
    회의 종료 🚪
  </button>
  &nbsp;
  <!-- Modal -->
  <div
    class="modal fade"
    id="inviteModal"
    tabindex="-1"
    aria-labelledby="exampleModalLabel"
    aria-hidden="true"
  >
    <div
      class="modal-dialog modal-dialog-centered modal-dialog-scrollable modal-lg"
    >
      <div class="modal-content">
        <div class="modal-header" style="border-bottom: none; margin-left: 10px;" >
          <h5 class="modal-title" id="exampleModalLabel">
            링크를 복사해서 친구에게 전송하세요!
          </h5>
        </div>
        <div id="myInputDiv" style="text-align: left; margin-left: 25px;">
          <input id="myInput" :value="this.curURL" readonly />
        </div>
        <div class="modal-footer" style="border-top: none;">
          <button
            type="button"
            class="btn btn-secondary"
            data-bs-dismiss="modal"
          >
            Close
          </button>
          <button
            type="button"
            class="btn btn-primary"
            @click="copyToClipboard()"
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
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
        <div class="modal-header" style="border-bottom: none;">
          <h5 class="modal-title" id="exampleModalLabel">
            여행 유형을 선택하고 일정을 마무리하세요!
          </h5>
          <button
            type="button"
            class="btn-close"
            data-bs-dismiss="modal"
            aria-label="Close"
          ></button>
        </div>
        <div class="modal-body">
          <div class="d-flex p-2 bd-highlight">동행인</div>
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
                >
                  #{{ item }}
                </a>
              </li>
            </ul>
          </div>
          <div class="d-flex p-2 bd-highlight">계절</div>
          <div class="nav-wrapper position-relative end-0">
            <!-- 계절 유형 -->
            <ul class="nav nav-pills nav-fill p-1" role="tablist">
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
                >
                  #{{ item }}
                </a>
              </li>
            </ul>
          </div>
          <div class="d-flex p-2 bd-highlight">지역</div>
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
                >
                  #{{ item }}
                </a>
              </li>
            </ul>
          </div>
          <div class="d-flex p-2 bd-highlight">테마</div>
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
                >
                  #{{ item }}
                </a>
              </li>
            </ul>
          </div>
        </div>
        <div class="modal-footer" style="border-top: none;">
          <button
            type="button"
            class="btn btn-secondary"
            data-bs-dismiss="modal"
          >
            Close
          </button>
          <button
            type="button"
            class="btn btn-primary"
            @click="setBoardCategory()"
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
import html2pdf from "html2pdf.js";

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
      withWhom: "0",
      season: "0",
      area: "0",
      theme: "0",
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
    findBoardId(boardRandom) {
      axios({
        method: "post",
        url: API_BASE_URL + "board/searchBoardIdByBoardRandom",
        data: {
          boardRandom: boardRandom,
        },
      }).then((res) => {
        console.log(res.data);
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
      }).then((res) => {
        console.log(res);
        this.finishBoard();
      });
    },
    finishBoard() {
      axios({
        method: "patch",
        url: API_BASE_URL + "board/finish",
        data: {
          boardId: this.boardId,
        },
      }).then((res) => {
        console.log(res);
        location.href = `/dashboard`;
      });
    },
    copyToClipboard() {
      const copyText = document.getElementById("myInput");
      copyText.select();
      document.execCommand("copy");
      // alert(copyText.value + "을 복사했습니다.");
    },
    exportToPDF() {
      //window.scrollTo(0, 0);
      html2pdf(this.$refs.pdfarea, {
        margin: 0,
        filename: "document.pdf",
        image: { type: "jpg", quality: 0.95 },
        //	allowTaint 옵션추가
        html2canvas: {
          useCORS: true,
          scrollY: 0,
          scale: 1,
          dpi: 300,
          letterRendering: true,
          allowTaint: false, //useCORS를 true로 설정 시 반드시 allowTaint를 false처리 해주어야함
        },
        jsPDF: {
          orientation: "portrait",
          unit: "mm",
          format: "a4",
          compressPDF: true,
        },
      });
    },
  },
};
</script>

<style>
#myInput {
  border: none;
  border-radius: 5px;
  padding: 5px;
  background-color: #e7e7e8;
  font-size: 20px;
}

.modal-content {
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

.btn-green {
  background-color: #77af9c;
  color: #d7fff1;
}
</style>
