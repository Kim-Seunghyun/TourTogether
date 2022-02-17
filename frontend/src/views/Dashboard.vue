<template>
  <div class="pb-4 container-fluid">
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
              style="background-color: rgb(180 211 245)"
            >
              <h2 class="text-white font-weight-bolder mb-4 pt-2">
                🗓️👭✈️👬🚢
              </h2>
              <p class="" style="font-weight: bold">
                친구들과 실시간으로 소통하며 여행 계획을 짜고 싶은 사람!<br />
                다른 사람들의 기깔나는 여행 계획을 추천받고 싶은 사람! <br />
                여행지는 정했지만 어디 가야할지 도저히 모르겠는 사람!
                <br /><br />
                Tour Together을 통해 실시간으로 소통하고,<br />
                인기있는 여행계획을 둘러보고, 관광지를 추천받으세요!
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
                    class="btn btn-primary text-black"
                    data-bs-toggle="modal"
                    data-bs-target="#modal"
                    :style="goToBoardBtn"
                  >
                    일정짜러 가기
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
        <h4 style="text-align: left; margin-left: 60px">🌏 추천</h4>
        <div class="card z-index-2">
          <div class="p-3 card-body d-flex">
            <div
              class="col-lg-7 col-sm-8"
              style="text-align: left; margin-left: 40px"
            >
              <board-category />
            </div>
            <br />
          </div>
          <div>
            <div
              v-if="boards.length == 0"
              style="text-align: center"
              class="d-flex justify-content-center"
            >
              <h5 style="text-align: center; margin: 7px">
                선택하신 유형에 해당되는 여행 계획이 없습니다.😭
              </h5>
            </div>
            <div v-else class="row card-container" style="padding: 0 33px">
              <BoardByCategory
                v-for="board in boards"
                :board="board"
                :key="board"
                @click="showBoard(board)"
                data-bs-toggle="modal"
                data-bs-target="#boardModal"
              />
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- Modal -->
    <div
      class="modal fade"
      id="boardModal"
      tabindex="-1"
      aria-labelledby="exampleModalLabel"
      aria-hidden="true"
    >
      <div class="modal-dialog modal-dialog-centered modal-lg">
        <div class="modal-content">
          <div class="modal-header" style="border-bottom: none">
            <h5 class="modal-title" id="exampleModalLabel">
              {{ this.selectBoardName }}
            </h5>
            <div>
              <img
                class="cursur-pointer"
                style="margin: 0 3px"
                v-if="this.favoriteBoardId.includes(this.selectBoard)"
                src="@/assets/img/full_heart.png"
                width="30"
                @click="likeCancel(this.selectBoard)"
              />
              <img
                class="cursur-pointer"
                style="margin: 0 3px"
                v-if="!this.favoriteBoardId.includes(this.selectBoard)"
                src="@/assets/img/empty_heart.png"
                width="30"
                @click="likeClick(this.selectBoard)"
              />
            </div>
          </div>
          <div class="modal-body">
            <div class="d-flex p-2 bd-highlight select-title">일차별</div>
            <div class="nav-wrapper position-relative end-0">
              <ul
                class="nav nav-pills nav-fill p-1"
                role="tablist"
                data-id="hi"
              >
                <li
                  class="nav-item"
                  v-for="(item, index) in dayArr"
                  :key="item"
                  @click="selectDay(index)"
                >
                  <a
                    class="nav-link mb-0 px-0 py-1 active"
                    data-bs-toggle="tab"
                    role="tab"
                    aria-selected="true"
                    style="cursor: pointer"
                  >
                    {{ item }}
                  </a>
                </li>
              </ul>
              <ul id="schedule_list">
                <li
                  class="tmp_list"
                  v-for="value in this.tmp[this.day]"
                  :key="value"
                >
                  <div class="info">
                    <span class="name">
                      {{ value.name }}
                    </span>
                    <span class="add"> 주소 : {{ value.add }} </span>
                  </div>
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
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import BoardByCategory from "@/components/BoardByCategory.vue";
import BoardCategory from "./components/BoardCategory.vue";
import { API_BASE_URL } from "@/config/index.js";
import { mapMutations, mapState } from "vuex";
import { onMounted } from "vue";
import axios from "axios";

const userStore = "userStore";
const boardStore = "boardStore";

export default {
  setup() {
    // const store = useStore();
    // const getters = store.getters;
    onMounted(() => {});
  },
  name: "dashboard",
  data() {
    return {
      boards: [],
      selectBoard: null,
      selectBoardName: null,
      selectSchedule: [],
      selectAddress: [],
      tmp: [],
      dayArr: [],
      day: 0,
      favoriteBoardId: [],
      tourspot: [],
      boardName: "",
      boardRandom: "",
      // userId: "",
      // propTitle: "mypdf",
      stats: {
        iconBackground: "bg-gradient-success",
      },
      goToBoardBtn: {
        backgroundColor: "#fffff0",
        color: "#1D2225",
      },
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
    axios({
      method: "get",
      url: API_BASE_URL + "tourspot",
    }).then((res) => {
      this.tourspot = res.data.tourSpotList;
    });
    // this.getAllBoards();
    this.getUserFavoriteBoards();
  },
  // mounted() {
  //   setNavPills();
  // },
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
    likeClick(boardId) {
      if (!this.userId) {
        alert("로그인 해주세요!");
      } else {
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
      }
    },
    likeCancel(boardId) {
      if (!this.userId) {
        alert("로그인 해주세요!");
      } else {
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
      }
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
      if (!this.userId) {
        alert("로그인 해주세요!");
      } else {
        axios({
          method: "post",
          url: API_BASE_URL + "board/create",
          data: {
            boardName: this.boardName,
            userId: this.userId,
          },
        }).then((res) => {
          this.boardRandom = res.data.boardRandom;
          location.href = `/board/${this.boardRandom}`;
        });
      }
    },
    showBoard(board) {
      this.day = 0;
      this.dayArr = [];
      this.tmp = [];
      this.selectBoard = board.boardId;
      this.selectBoardName = board.boardName;
      axios({
        method: "post",
        url: API_BASE_URL + "schedule/searchByBoardId",
        data: {
          boardId: this.selectBoard,
        },
      }).then((res) => {
        this.selectSchedule = res.data.schedule;
        var date =
          this.selectSchedule[this.selectSchedule.length - 1].scheduleDay;
        for (var i = 0; i < this.selectSchedule.length; i++) {
          let day = this.selectSchedule[i].scheduleDay - 1;
          let name = this.selectSchedule[i].scheduleAdditional;
          let id = this.selectSchedule[i].scheduleTourSpotId;
          let add;
          for (var j = 0; j < this.tourspot.length; j++) {
            if (id === this.tourspot[j].tourSpotId) {
              add = this.tourspot[j].tourSpotAddress;
              break;
            }
          }
          let aaa = {
            name: name,
            add: add,
          };
          if (this.tmp[day] === undefined) {
            this.tmp[day] = [];
          }
          this.tmp[day].push(aaa);
        }
        for (i = 1; i <= date; i++) {
          var str = i + " 일차";
          this.dayArr.push(str);
        }
      });
    },
    selectDay(index) {
      this.selectAddress = [];
      this.day = index;
    },
  },
  components: {
    BoardCategory,
    BoardByCategory,
  },
};
</script>
<style scoped>
.card-container {
  /* width: 2040px; */
  margin: 0 auto;
}
#schedule_list {
  background-color: #96b7e8ba;
}

.select-title {
  font-weight: bold;
}
</style>
