export const userStore = {
  namespaced: true,
  state: {
    user: [],
    userId: "",
    userNickname: "",
    userProfileImage: "",
    userInputNickname: "",
    userLoginPlatform: "",
    userClientId: "",
  },
  getters: {
    getUser(state) {
      return state.user;
    },
    getUserId(state) {
      return state.userId;
    },
    getUserNickname(state) {
      return state.userNickname;
    },
    getUserProfileImage(state) {
      return state.userProfileImage;
    },
    getUserInputNickname(state) {
      return state.userInputNickname;
    },
    getUserLoginPlatform(state) {
      return state.userLoginPlatform;
    },
    getUserClientId(state) {
      return state.userClientId;
    },
  },
  mutations: {
    setUser(state, user) {
      state.user = user;
      console.log("user -> ", state.user);
    },
    setUserId(state, userId) {
      state.userId = userId;
    },
    setUserNickname(state, userNickname) {
      state.userNickname = userNickname;
    },
    setUserProfileImage(state, userProfileImage) {
      state.userProfileImage = userProfileImage;
    },
    setUserInputNickname(state, value) {
      state.userInputNickname = value;
    },
    setUserLoginPlatform(state, userLoginPlatform) {
      state.userLoginPlatform = userLoginPlatform;
    },
    setUserClientId(state, userClientId) {
      state.userClientId = userClientId;
    },
    logout() {
      this.setUserId("");
      this.setUserLoginPlatform("");
      this.setUserClientId("");
      this.setUserNickname("");
      this.setUserProfileImage("");
    },
  },
  actions: {},
  // modules: {},
};
