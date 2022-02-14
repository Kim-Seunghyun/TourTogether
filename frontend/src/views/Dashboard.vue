<template>
  <div class="py-4 container-fluid" ref="pdfarea">
    <div class="row">
      <div class="col-lg-7 mb-lg-0 mb-4">
        <div class="card">
          <div class="card-body p-3">
            <div class="row">
              <div class="d-flex flex-column h-100">
                <!-- 관광지 이미지 carousel 부분 start-->
                <div
                  id="carouselExampleSlidesOnly"
                  class="carousel slide"
                  data-bs-ride="carousel"
                >
                  <div class="carousel-inner">
                    <div class="carousel-item active">
                      <img
                        src="../assets/img/carousel_image1.jpeg"
                        class="d-block w-100 rounded"
                      />
                    </div>
                    <div class="carousel-item">
                      <img
                        src="../assets/img/carousel_image2.jpeg"
                        class="d-block w-100 rounded"
                      />
                    </div>
                    <div class="carousel-item">
                      <img
                        src="../assets/img/carousel_image3.jpeg"
                        class="d-block w-100 rounded"
                      />
                    </div>
                  </div>
                </div>
                <!-- 관광지 이미지 carousel 부분 end-->
              </div>
            </div>
          </div>
        </div>
      </div>
      <!-- 일정 짜러 가기 -->
      <div class="col-lg-5">
        <div class="card h-100 p-3">
          <div
            class="overflow-hidden position-relative border-radius-lg bg-cover h-100"
          >
            <span class="mask bg-gradient-dark"></span>
            <div
              class="card-body position-relative z-index-1 d-flex flex-column h-100 p-3"
            >
              <h1 class="text-white font-weight-bolder mb-4 pt-2">✈️</h1>
              <p class="text-white">
                친구들과 실시간으로 소통하며 여행 계획을 짜고 싶은 사람!<br />
                다른 사람들의 기깔나는 여행 계획을 추천받고 싶은 사람! <br />
                여행지는 정했지만 어디 가야할지 도저히 모르겠는 사람!
                <br /><br /><br />
                Tour Together을 통해 실시간으로 소통하고, 인기있는 여행계획을
                둘러보고, 관광지를 추천받으세요!
                <br />
                당신의 완벽한 여행을 위해 Tour Together가 도와줍니다.
              </p>
              <a
                class="text-white text-sm font-weight-bold mb-0 icon-move-right mt-auto"
                href="javascript:;"
              >
                <h4>
                  <!-- <router-link
                    class="px-0 text-white nav-link font-weight-bold"
                    data-bs-toggle="modal"
                    data-bs-target="#startModal"
                    >일정 짜기 START</router-link
                  > -->

                  <button
                    type="button"
                    class="btn btn-primary"
                    data-bs-toggle="modal"
                    data-bs-target="#modal"
                  >
                    일정 짜기 START
                  </button>
                </h4>
              </a>
            </div>
          </div>
        </div>
      </div>
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
    </div>
    <!-- 유형 선택하기 -->
    <div class="mt-4 row">
      <div class="mb-4 col-lg-12 mb-lg-0">
        <h4>여행지 추천</h4>
        <div class="card z-index-2">
          <div class="p-3 card-body">
            <!-- <div data-html2canvas-ignore="true">
              출력하지 않고 싶은 영역은 태그에 'data-html2canvas-ignore'
              attribute를 넣어주면된다.
            </div> -->

            <div class="col-lg-4 col-sm-8">
              <board-category />
            </div>
            <br />
          </div>
          <div v-for="(board, index) in boards" :key="index">
            <div class="col-lg-6 col-sm-8">
              {{ board.boardId }} // {{ board.boardName }} //
              {{ board.boardLikesCount }}
              <img
                v-if="this.favoriteBoardId.includes(board.boardId)"
                src="@/assets/img/full_heart.png"
                width="30"
                @click="likeCancel(board.boardId)"
              />
              <img
                v-if="!this.favoriteBoardId.includes(board.boardId)"
                src="@/assets/img/empty_heart.png"
                width="30"
                @click="likeClick(board.boardId)"
              />
            </div>
            <div class="col-lg-6 col-sm-8"></div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import BoardCategory from "./components/BoardCategory.vue";
import { API_BASE_URL } from "@/config/index.js";
import html2pdf from "html2pdf.js";

import { mapMutations, mapState } from "vuex";
// import { useStore } from "vuex";
import { onMounted } from "vue";
import axios from "axios";

const userStore = "userStore";
const boardStore = "boardStore";

export default {
  setup() {
    // const store = useStore();
    // const getters = store.getters;
    onMounted(() => {

    })
  },
  name: "dashboard",
  data() {
    return {
      boards: [],
      favoriteBoardId: [],
      boardName: "",
      boardRandom: "",
      // userId: "",
      propTitle: "mypdf",
      stats: {
        iconBackground: "bg-gradient-success",
      },
      sales: {},
    };
  },
  computed: {
    ...mapState(userStore, ["userId"]),
    ...mapState(boardStore, ["searchByCategoryBoards"]),
    ...mapState(boardStore, ["withWhom", "season", "area", "theme"]),
    // ...mapState(boardStore, ["boards"]),
  },
  watch: {
    searchByCategoryBoards() {
      this.boards = this.searchByCategoryBoards;
    },
  },
  created() {
    // this.getAllBoards();
    this.getUserFavoriteBoards();
  },
  methods: {
    ...mapMutations(boardStore, ["setAllBoards", "setSearchByCategoryBoards"]),
    // getAllBoards() {
    //   axios({
    //     method: "get",
    //     url: API_BASE_URL + "board",
    //   }).then((res) => {
    //     this.boards = res.data.boards;
    //     this.setAllBoards(res.data.boards);
    //   });
    // },
    getUserFavoriteBoards() {
      axios({
        method: "post",
        url: API_BASE_URL + "board/searchLikeBoardByUserId",
        data: {
          userId: this.userId,
        },
      }).then((res) => {
        for (var i = 0; i < res.data.myBoards.length; i++) {
          this.favoriteBoardId[i] = res.data.myBoards[i].boardId;
        }
      });
    },
    createBoard() {
      // alert("send create board data");
      axios({
        method: "post",
        url: API_BASE_URL + "board/create",
        data: {
          boardName: this.boardName,
          userId: this.userId,
        },
      }).then((res) => {
        console.log(res.data);
        this.boardRandom = res.data.boardRandom;
        console.log(this.boardRandom);
        location.href = `/board/${this.boardRandom}`;
      });
    },
    likeClick(boardId) {
      console.log("좋아요 누르기 ");
      axios({
        method: "patch",
        url: API_BASE_URL + "board/clickBoardLike",
        data: {
          boardId: boardId,
          userId: this.userId,
        },
      }).then((res) => {
        this.favoriteBoardId = res.data.favoriteBoardId;
        this.getListByCategory(
          this.withWhom,
          this.season,
          this.area,
          this.theme
        );
      });
    },
    likeCancel(boardId) {
      console.log("좋아요 취소하기");
      axios({
        method: "patch",
        url: API_BASE_URL + "board/cancelBoardLike",
        data: {
          boardId: boardId,
          userId: this.userId,
        },
      }).then((res) => {
        this.favoriteBoardId = res.data.favoriteBoardId;
        this.getListByCategory(
          this.withWhom,
          this.season,
          this.area,
          this.theme
        );
      });
    },
    getListByCategory(withWhom, season, area, theme) {
      axios({
        method: "post",
        url: API_BASE_URL + "board/searchByCategory",
        data: {
          categoryArea: area,
          categorySeason: season,
          categoryTheme: theme,
          categoryWithWhom: withWhom,
        },
      }).then((res) => {
        this.setSearchByCategoryBoards(res.data.boards);
      });
    },
  },
  components: {
    BoardCategory,
  },
};
</script>
