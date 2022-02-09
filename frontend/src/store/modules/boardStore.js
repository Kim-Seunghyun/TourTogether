export const boardStore = {
  namespaced: true,
  state: {
    boards: [],
  },
  getters: {
    getAllBoards(state) {
      return state.boards;
    },
  },
  mutations: {
    setAllBoards(state, boards) {
      state.boards = boards;
    },
  },
  actions: {},
  // modules: {},
};
