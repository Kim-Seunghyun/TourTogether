<template>
  <div class="card col-3">
    <div class="delete-button-div">
      <img
        class="delete-button cursur-pointer"
        src="../assets/delete_button.png"
        alt="delete-button"
        data-bs-toggle="modal"
        data-bs-target="#modal1"
      />
    </div>
    <img src="@/assets/trip-route.jpg" alt="trip-route" />
    <div class="board-info d-flex justify-content-between">
      <div>{{ board.boardName }}</div>
      <div>
        <img
          class="heart cursur-pointer"
          v-if="
            computedGetters['boardStore/getBoardsLikeId'].includes(
              board.boardId
            )
          "
          src="@/assets/img/full_heart.png"
          width="30"
          @click="likeCancel()"
        />
        <img
          class="heart cursur-pointer"
          v-if="
            !computedGetters['boardStore/getBoardsLikeId'].includes(
              board.boardId
            )
          "
          src="@/assets/img/empty_heart.png"
          width="30"
          @click="like()"
        />
        <span>{{ board.boardLikesCount }}</span>
      </div>
    </div>
  </div>

  <!-- Modal -->
  <div
    class="modal fade"
    id="modal1"
    tabindex="-1"
    aria-labelledby="modalLabel"
    aria-hidden="true"
  >
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="modalLabel">삭제 확인</h5>
          <button
            type="button"
            class="btn-close"
            data-bs-dismiss="modal"
            aria-label="Close"
          ></button>
        </div>
        <div class="modal-body">
          <div class="profile-image d-inline-block">
            <span>일정 "{{ board.boardName }}"</span><br />
            <span>정말 삭제하시겠습니까?</span>
          </div>
        </div>
        <div class="modal-footer">
          <button
            type="button"
            class="btn btn-secondary"
            data-bs-dismiss="modal"
          >
            취소
          </button>
          <button
            @click="deleteBoard"
            class="btn btn-primary"
            data-bs-dismiss="modal"
          >
            확인
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { useStore } from "vuex";
import { computed } from "vue";
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

    const deleteBoard = () => {
      axios({
        method: "delete",
        url: "/api/board/delete",
        data: {
          boardId: props.board.boardId,
        },
      }).then(() => {
        store.commit("boardStore/deleteBoardIng", props.board.boardId);
        getBoardsLike();
      });
    };
    const like = () => {
      if (!getters["userStore/getUserId"]) {
        alert("로그인 해주세요!");
      } else {
        axios({
          method: "patch",
          url: API_BASE_URL + "board/clickBoardLike",
          data: {
            boardId: props.board.boardId,
            userId: getters["userStore/getUserId"],
          },
        }).then(() => {
          store.commit("boardStore/addBoardLike", props.board);
          getBoardsLike();
          getBoardsDone();
        });
      }
    };
    const likeCancel = () => {
      if (!getters["userStore/getUserId"]) {
        alert("로그인 해주세요!");
      } else {
        axios({
          method: "patch",
          url: API_BASE_URL + "board/cancelBoardLike",
          data: {
            boardId: props.board.boardId,
            userId: getters["userStore/getUserId"],
          },
        }).then(() => {
          store.commit("boardStore/cancelBoardLike", props.board);
          getBoardsLike();
          getBoardsDone();
        });
      }
    };
    const getBoardsLike = () => {
      axios({
        method: "post",
        url: API_BASE_URL + "board/searchLikeBoardByUserId",
        data: {
          userId: getters["userStore/getUserId"],
        },
      }).then((res) => {
        store.commit("boardStore/setBoardsLike", res.data.myBoards);
      });
    };
    const getBoardsDone = () => {
      axios({
        method: "post",
        url: API_BASE_URL + "board/searchByUserId/finish",
        data: {
          userId: getters["userStore/getUserId"],
        },
      }).then((res) => {
        store.commit("boardStore/setBoardsDone", res.data.myBoards);
      });
    };

    return {
      deleteBoard,
      getters,
      computedGetters,
      like,
      likeCancel,
      getBoardsLike,
      getBoardsDone,
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
.delete-button-div {
  position: relative;
  left: 120px;
  bottom: 12px;
}

.cursur-pointer {
  cursor: pointer;
}

.card {
  border: 1px solid gray;
  margin: 25px;
  width: 250px;
}

.board-info {
  margin: 5px;
}

.heart {
  margin: 0 3px;
}
</style>
