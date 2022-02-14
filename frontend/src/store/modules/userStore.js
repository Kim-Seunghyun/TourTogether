export const userStore = {
  namespaced: true,
  state: {
    userId: "",
    userNickname: "",
    userProfileImage: "",
    userInputNickname: "",
    userLoginPlatform: "",
    userClientId: "",
    userInputEmail: "",
  },
  getters: {
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
    getUserInpuEmail(state) {
      return state.userInputEmail;
    },
  },
  mutations: {
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
    setUserInputEmail(state, value) {
      state.userInputEmail = value;
    },
    logout() {
      this.setUserId("")
      this.setUserLoginPlatform("")
      this.setUserClientId("")
      this.setUserNickname("")
      this.setUserProfileImage("")
    }
  },
  actions: {},
  // modules: {},
};
