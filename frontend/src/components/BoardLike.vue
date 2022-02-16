<template>
  <div class="card col-3">
    <img src="@/assets/trip-route.jpg" alt="trip-route" style="margin-top: 30px;">
    <div class="board-info d-flex justify-content-between">
      <div>{{ board.boardName }}</div>
      <div>
        <!-- <img
          class="delete-button"
          src="../assets/delete_button.png"
          alt="delete-button"
          @click="deleteBoard"> -->
        <img
          class="heart cursur-pointer"
          src="@/assets/img/full_heart.png"
          width="30"
          @click="likeCancel()"
        />
        <span>{{ board.boardLikesCount }}</span>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { useStore } from "vuex";
import { API_BASE_URL } from "@/config/index.js";
import { computed } from "vue";

export default {
  name: 'BoardIng',
  props: {
    board: Object,
  },
  setup(props) {
    const store = useStore();
    const computedGetters = computed(() => store.getters);
    const getters = store.getters;
    const likeCancel = () => {
      axios({
        method: "patch",
        url: API_BASE_URL + "board/cancelBoardLike",
        data: {
          boardId: props.board.boardId,
          userId: getters["userStore/getUserId"],
        },
      }).then(() => {
        store.commit("boardStore/cancelBoardLike", props.board)
        axios({
          method: "post",
          url: 
          API_BASE_URL + "board/searchLikeBoardByUserId",
          data: {
            userId: getters["userStore/getUserId"],
          },
        }).then(res => {
          store.commit("boardStore/setBoardsLike", res.data.myBoards)
          });
        });
      }
    return {
      likeCancel,
      computedGetters,
    };
  }
}
</script>
<style scoped>
.card {
  border: 1px solid gray;
  margin: 25px;
  width: 250px;
}
.cursur-pointer {
  cursor: pointer;
}
.heart {
  margin: 0 3px;
}
.board-info {
  margin: 5px;
}

</style>