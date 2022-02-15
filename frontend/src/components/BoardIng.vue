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
        url: '/api/board/delete',
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