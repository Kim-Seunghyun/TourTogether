export const boardStore = {
  namespaced: true,
  state: {
    boards: [],
    searchByCategoryBoards: [],
    withWhom: "",
    season: "",
    area: "",
    theme: "",
    boardsIng: null,
    boardsDone: null,
    boardsLike: null,
  },
  getters: {
    getAllBoards(state) {
      return state.boards;
    },
    getSearchByCategoryBoards(state) {
      return state.searchByCategoryBoards;
    },
    getBoardsIng(state) {
      return state.boardsIng;
    },
    getBoardsDone(state) {
      return state.boardsDone;
    },
    getBoardsLike(state) {
      return state.boardsLike;
    },
  },
  mutations: {
    setAllBoards(state, boards) {
      state.boards = boards;
    },
    setSearchByCategoryBoards(state, boards) {
      state.searchByCategoryBoards = boards;
    },
    setCategory(state, category) {
      state.withWhom = category[0];
      state.season = category[1];
      state.area = category[2];
      state.theme = category[3];
    },
    setBoardsIng(state, boardsIng) {
      state.boardsIng = boardsIng;
    },
    setBoardsDone(state, boardsDone) {
      state.boardsDone = boardsDone;
    },
    setBoardsLike(state, boardsLike) {
      state.boardsLike = boardsLike;
    },
    deleteBoardIng(state, boardId) {
      state.boardsIng = state.boardsIng.filter((board) => {
        if (board) {
          return board.boardId !== boardId;
        }
      });
    },
    deleteBoardDone(state, boardId) {
      state.boardsDone = state.boardsDone.filter((board) => {
        if (board) {
          return board.boardId !== boardId;
        }
      });
    },
    addBoardLike(state, board) {
      state.boardsLike.push(board);
    },
    cancelBoardLike(state, board) {
      state.boardsLike = state.boardsLike.fliter((boardLike) => {
        return boardLike !== board;
      });
    },
  },
  actions: {},
  // modules: {},
};
