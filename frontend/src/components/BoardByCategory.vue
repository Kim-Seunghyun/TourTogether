<template>
  <div class="card col-xxl-12">
    <img
      class="modal_img"
      src="@/assets/trip-route.jpg"
      alt="trip-route"
      style="margin-top: 13px"
    />
    <div class="board-info d-flex justify-content-between">
      <div>{{ board.id }}</div>
      <div>
        <img
          style="margin: 0 3px"
          v-if="this.favoriteBoardId.includes(board.boardId)"
          src="@/assets/img/full_heart.png"
          width="30"
          @click="likeCancel(board.boardId)"
        />
        <img
          style="margin: 0 3px"
          v-if="!this.favoriteBoardId.includes(board.boardId)"
          src="@/assets/img/empty_heart.png"
          width="30"
          @click="likeClick(board.boardId)"
        />
        <span>{{ board.boardLikesCount }}</span>
      </div>
    </div>
  </div>
</template>

<script>
import { API_BASE_URL } from "@/config/index.js";
import axios from "axios";
import { mapMutations, mapState } from "vuex";

const userStore = "userStore";
const boardStore = "boardStore";
export default {
  name: "BoardCategory",
  props: {
    board: Object,
  },
  data() {
    return {
      boards: [],
      favoriteBoardId: [],
      dayArr: [],
      day: null,
    };
  },
  computed: {
    ...mapState(userStore, ["userId"]),
    ...mapState(boardStore, ["searchByCategoryBoards"]),
    ...mapState(boardStore, ["withWhom", "season", "area", "theme"]),
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
    ...mapMutations(boardStore, ["setSearchByCategoryBoards"]),
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
    likeClick(boardId) {
      axios({
        method: "patch",
        url: API_BASE_URL + "board/clickBoardLike",
        data: {
          boardId: boardId,
          userId: this.userId,
        },
      }).then((res) => {
        this.favoriteBoardId = res.data.favoriteBoardId;
        console.log(this.favoriteBoardId);
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
    selectDay(index) {
      this.withWhom = index;
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
    clickBoard(boardId) {
      console.log(boardId);
      axios({
        method: "post",
        url: API_BASE_URL + "tourspot",
      }).then((res) => {
        console.log(res);
      });
    },
  },
};
</script>
<style scoped>
.delete-button {
  width: 30px;
  height: 30px;
  object-fit: contain;
  position: relative;
}

.delete-button-div {
  position: relative;
  left: 125px;
  bottom: 12px;
}

.cursur-pointer {
  cursor: pointer;
}

.card {
  border: 1px solid gray;
  margin: 23px;
  width: 280px;
}

.board-info {
  margin: 5px;
}

.heart {
  margin: 0 3px;
}
</style>
