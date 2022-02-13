export const userStore = {
  namespaced: true,
  state: {
    userId: "",
    userNickname: "",
    userProfileImage: "",
    userInputNickname: "",
    userLoginPlatform: "",
    userClientId: "",
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
  },
  mutations: {
    setUserId(state, userId) {
      // alert("userStore -> setUserId : ", userId);
      state.userId = userId;
      // alert("userStore -> state.userId : ", state.userId);
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
  },
  actions: {},
  // modules: {},
};
