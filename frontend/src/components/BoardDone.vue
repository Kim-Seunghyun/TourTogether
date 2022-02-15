<template>
  <div>
    <p>{{ board }}</p>
    <img
      class="delete-button"
      v-if="board"
      src="../assets/delete_button.png"
      alt="delete-button"
      @click="deleteBoard"
    />
    <img
      v-if="computedGetters['boardStore/getBoardsLike'].includes(board)"
      src="@/assets/img/full_heart.png"
      width="30"
      @click="likeCancel(board.boardId)"
    />
    <img
      v-if="!computedGetters['boardStore/getBoardsLike'].includes(board)"
      src="@/assets/img/empty_heart.png"
      width="30"
      @click="like(board.boardId)"
    />
  </div>
</template>

<script>
import axios from "axios";
import { useStore } from "vuex";
import { reactive, computed } from "vue";
import { onMounted } from "vue";
import { API_BASE_URL } from "@/config/index.js";

export default {
  name: "BoardDone",
  props: {
    board: Object,
  },
  setup(props) {
    const store = useStore();
    const computedGetters = computed(() => store.getters);
    const getters = store.getters;
    const state = reactive({
      likes: null,
    });
    const deleteBoard = () => {
      axios({
        method: "delete",
        url: "/api/board/delete",
        data: {
          boardId: props.board.boardId,
        },
      }).then((res) => {
        console.log(res);
        store.commit("boardStore/deleteBoardIng", props.board.boardId);
      });
    };
    const like = () => {
      axios({
        method: "patch",
        url: API_BASE_URL + "board/clickBoardLike",
        data: {
          boardId: props.board.boardId,
          userId: getters["userStore/getUserId"],
        },
      }).then((res) => {
        store.commit("boardStore/addBoardLike", props.board);
        console.log(res);
        console.log(getters["boardStore/getBoardsLike"]);
      });
    };
    const likeCancel = () => {
      axios({
        method: "patch",
        url: API_BASE_URL + "board/cancelBoardLike",
        data: {
          boardId: props.board.boardId,
          userId: getters["userStore/getUserId"],
        },
      }).then(() => {
        // store.commit("boardStore/addBoardLike", props.board)
        axios({
          method: "post",
          url:
            // API_BASE_URL +
            "/api/board/searchLikeBoardByUserId",
          data: {
            userId: 98,
          },
        }).then((res) => {
          store.commit("boardStore/setBoardsLike", res.data.myBoards);
          console.log(getters["boardStore/getBoardsLike"]);
        });
      });
    };

    onMounted(() => {});

    return {
      deleteBoard,
      getters,
      computedGetters,
      like,
      likeCancel,
      state,
    };
  },
};
</script>
<style scoped>
.delete-button {
  width: 30px;
  height: 30px;
  object-fit: contain;
}
</style>
