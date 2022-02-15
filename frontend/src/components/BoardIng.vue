<template>
  <div>
    <p>{{ board }}</p>
    <img
      class="delete-button"
      v-if="board"
      src="../assets/delete_button.png"
      alt="delete-button"
      @click="deleteBoard">
  </div>
</template>

<script>
import axios from "axios";
import { useStore } from "vuex";
import { API_BASE_URL } from "@/config/index.js";

export default {
  name: 'BoardIng',
  props: {
    board: Object,
  },
  setup(props) {
    const store = useStore();
    const deleteBoard = () => {
      axios({
        method: 'delete',
        url: API_BASE_URL + 'board/delete',
        data: {
          boardId: props.board.boardId
        }
      }).then(res => {
          console.log(res)
          store.commit("boardStore/deleteBoardIng", props.board.boardId)
        })
    };

    return {
      deleteBoard
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