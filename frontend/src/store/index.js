import { createStore } from "vuex";

export default createStore({
  state: {
    userNickname: '',
    userProfileImage: '',
    userInputNickname: '',
    userLoginPlatform: '',
    userClientId: '',
  },
  getters: {
    getUserNickname(state) {
      return state.userNickname
    },
    getUserProfileImage(state) {
      return state.userProfileImage
    },
    getUserInputNickname(state) {
      return state.userInputNickname
    },
    getUserLoginPlatform(state) {
      return state.userLoginPlatform
    },
    getUserClientId(state) {
      return state.userClientId
    }
  },
  mutations: {
    setUserNickname(state, userNickname) {
      state.userNickname = userNickname
    },
    setUserProfileImage(state, userProfileImage) {
      state.userProfileImage = userProfileImage
    },
    setUserInputNickname(state, value) {
      state.userInputNickname = value
    },
    setUserLoginPlatform(state, userLoginPlatform) {
      state.userLoginPlatform = userLoginPlatform
    },
    setUserClientId(state, userCliendId) {
      state.userClientId = userCliendId
    }
  },
  actions: {},
  // modules: {},
});
