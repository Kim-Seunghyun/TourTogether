<template>
  <div>
    <p>{{ board }}</p>
    <img
      class="delete-button"
      src="../assets/delete_button.png"
      alt="delete-button"
      @click="deleteBoard">
    <img
      v-show="computedGetters['boardStore/getBoardsLikeId'].includes(board.boardId)"
      src="@/assets/img/full_heart.png"
      width="30"
      @click="likeCancel()"
    />
    <img
      v-show="!computedGetters['boardStore/getBoardsLikeId'].includes(board.boardId)"
      src="@/assets/img/empty_heart.png"
      width="30"
      @click="like()"
    />
  </div>
</template>

<script>
import axios from "axios";
import { useStore } from "vuex";
import { computed } from "vue";
import { API_BASE_URL } from "@/config/index.js";

export default {
  name: 'BoardDone',
  props: {
    board: Object,
  },
  setup(props) {
    const store = useStore();
    const computedGetters = computed(() => store.getters);
    const getters = store.getters;
    // const state = reactive({
    //   isLiked: getters["boardStore/getBoardsLike"].includes(props.board),
    // })
    const deleteBoard = () => {
      axios({
        method: 'delete',
        url: '/api/board/delete',
        data: {
          boardId: props.board.boardId
        }
      }).then(res => {
          console.log(res)
          store.commit("boardStore/deleteBoardIng", props.board.boardId)
        })
    };
    const like = () => {
      axios({
        method: "patch",
        url: API_BASE_URL + "board/clickBoardLike",
        data: {
          boardId: props.board.boardId,
          userId: getters["userStore/getUserId"],
        },
      }).then(() => {
        store.commit("boardStore/addBoardLike", props.board)
        getBoardsLike()
        // isLiked()
      });
    }
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
        getBoardsLike()
        // isLiked()
      });
    }
    const getBoardsLike = () => {
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
    }
    // const isLiked = () => {
    //   computedGetters.value["boardStore/getBoardsLike"].forEach(board => {
    //     if (board.boardId === props.board.boardId) {
    //       state.isLiked = true
    //     } else {
    //       state.isLiked = false
    //     }
    //   });
    // }

    return {
      deleteBoard,
      getters,
      computedGetters,
      like,
      likeCancel,
      // state,
      // isLiked,
      getBoardsLike
    };
  }
}
</script>
<style scoped>
.delete-button {
  width: 30px;
  height: 30px;
  object-fit: contain;
}
</style>