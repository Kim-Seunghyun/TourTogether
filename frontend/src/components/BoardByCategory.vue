<template>
  <div class="card col-xxl-12">
    <img
      src="@/assets/trip-route.jpg"
      alt="trip-route"
      style="margin-top: 13px; cursor: pointer;"
    />
    <div class="board-info d-flex justify-content-between">
      <div>{{ board.boardName }}</div>
      <div>
        <span>💝{{ board.boardLikesCount }}</span>
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

    selectDay(index) {
      this.withWhom = index;
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
.cursur-pointer {
  cursor: pointer;
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
