<template>
  <div class="card">
    <div class="delete-button-div">
      <img
        class="delete-button cursur-pointer"
        src="../assets/delete_button.png"
        alt="delete-button"
        data-bs-toggle="modal"
        data-bs-target="#modal1"
        @click="commitBoardToDelete(board)"
      />
    </div>
    <img
      @click="openBoard"
      class="cursur-pointer"
      src="@/assets/trip-route.jpg"
      alt="trip-route"
    />
    <div class="board-info d-flex justify-content-between">
      <div @click="openBoard" class="cursur-pointer">
        <h6>{{ board.boardName }}</h6>
      </div>
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
            <span>일정 "{{ computedGetters['boardStore/getBoardToDelete'].boardName }}"</span><br />
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
            @click="deleteBoard(computedGetters['boardStore/getBoardToDelete'])"
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
import { API_BASE_URL } from "@/config/index.js";
import { computed } from "vue";

export default {
  name: "BoardIng",
  props: {
    board: Object,
  },
  setup(props) {
    const store = useStore();
    const computedGetters = computed(() => store.getters);
    const getters = store.getters;
    const deleteBoard = (boardId) => {
      axios({
        method: "delete",
        url: API_BASE_URL + "board/delete",
        data: {
          boardId: boardId,
        },
      }).then(() => {
        store.commit("boardStore/deleteBoardIng", boardId);
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
          // 이걸 실행하는 이유는 mypage에서 computed로 boardIng들을 뿌리기 때문!
          getBoardsIng();
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
          getBoardsIng();
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
        store.commit("boardStore/setBoardsLikeId", res.data.myBoards);
      });
    };
    const getBoardsIng = () => {
      axios({
        method: "post",
        url: API_BASE_URL + "board/searchByUserId/proceeding",
        data: {
          userId: getters["userStore/getUserId"],
        },
      }).then((res) => {
        store.commit("boardStore/setBoardsIng", res.data.myBoards);
      });
    };
    const commitBoardToDelete = (board) => {
      store.commit("boardStore/setBoardToDelete", board);
    };
    const openBoard = () => {
      axios({
        method: "post",
        url: API_BASE_URL + "board/searchByBoardId",
        data: {
          boardId: props.board.boardId,
        },
      }).then((res) => {
        location.href = `/board/${res.data.board.boardRandom}`;
      });
    };

    return {
      deleteBoard,
      like,
      likeCancel,
      computedGetters,
      getBoardsLike,
      getBoardsIng,
      commitBoardToDelete,
      openBoard,
    };
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
