<template>
  <div>
    <p>{{ board }}</p>
    <!-- <img
      class="delete-button"
      src="../assets/delete_button.png"
      alt="delete-button"
      @click="deleteBoard"> -->
    <img
      src="@/assets/img/full_heart.png"
      width="30"
      @click="likeCancel()"
    />
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
.delete-button {
  width: 30px;
  height: 30px;
  object-fit: contain;
}
</style>