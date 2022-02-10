<template>
  <div class="py-4 container-fluid">
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
            <form @submit.prevent="startBoard">
              <div class="modal-body">
                <input
                  type="text"
                  class="form-control"
                  v-model="boardName"
                  placeholder="여행 제목을 입력해주세요"
                  required
                  @keyup.enter="startBoard"
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
            <div class="col-lg-4 col-sm-8">
              <category-with-whom />
            </div>
            <br />
            <div class="col-lg-4 col-sm-8">
              <category-season />
            </div>
            <br />

            <div class="col-lg-4 col-sm-8">
              <category-area />
            </div>
            <br />
            <div class="col-lg-4 col-sm-8">
              <category-theme />
            </div>
          </div>
          <div v-for="(board, index) in boards" :key="index">
            보드 이름 : {{ board.boardName }} // 좋아요 수 :
            {{ board.boardLikesCount }}
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import CategoryWithWhom from "./components/CategoryWithWhom.vue";
import CategorySeason from "./components/CategorySeason.vue";
import CategoryArea from "./components/CategoryArea.vue";
import CategoryTheme from "./components/CategoryTheme.vue";
import { mapMutations, mapState } from "vuex";
import axios from "axios";

const userStore = "userStore";
const boardStore = "boardStore";

export default {
  name: "dashboard",
  data() {
    return {
      boards: [],
      boardName: "",
      // userId: "",
      stats: {
        iconBackground: "bg-gradient-success",
      },
      sales: {},
    };
  },
  computed: {
    ...mapState(userStore, ["userId"]),
    // ...mapState(boardStore, ["boards"]),
  },
  created() {
    this.getAllBoards();
  },
  methods: {
    ...mapMutations(boardStore, ["setAllBoards"]),
    getAllBoards() {
      axios({
        method: "get",
        url: "https://i6a105.p.ssafy.io:8081/board",
      }).then((res) => {
        this.boards = res.data.boards;
        this.setAllBoards(res.data.boards);
      });
    },
    startBoard() {
      console.log("createBoard!!!");
      console.log("userId는 ", this.userId);
      console.log("boardName은 ", this.boardName);
      this.createBoard();
      location.href = "/map";
    },
    createBoard() {
      // alert("send create board data");
      axios({
        method: "post",
        url: "https://i6a105.p.ssafy.io:8081/board/create",
        data: {
          boardName: this.boardName,
          userId: this.userId,
        },
      }).then((res) => {
        console.log(res);
      });
    },
  },
  components: {
    CategoryWithWhom,
    CategorySeason,
    CategoryArea,
    CategoryTheme,
  },
};
</script>
