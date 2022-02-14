export const boardStore = {
  namespaced: true,
  state: {
    boards: [],
    searchByCategoryBoards: [],
    withWhom: "",
    season: "",
    area: "",
    theme: "",
  },
  getters: {
    getAllBoards(state) {
      return state.boards;
    },
    getSearchByCategoryBoards(state) {
      return state.searchByCategoryBoards;
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
  },
  actions: {},
  // modules: {},
};
