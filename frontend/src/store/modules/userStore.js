import jwt_decode from "jwt-decode";
import axios from "axios";
import { API_BASE_URL } from "@/config/index.js";

export const userStore = {
  namespaced: true,
  state: {
    user: null,
    userId: "",
    userNickname: "",
    userProfileImage: "",
    userInputNickname: "",
    userLoginPlatform: "",
    userClientId: "",
    kakaoProfileImage: null,
    accessToken: "",
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
    getKakaoProfileImage(state) {
      return state.kakaoProfileImage;
    },
    getAccessToken(state) {
      return state.accessToken;
    },
  },
  mutations: {
    setUser(state, user) {
      state.user = user;
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
      this.setUser("");
      this.setKakaoProfileImage("");
      this.setAccessToken("");
    },
    setKakaoProfileImage(state, kakaoProfileImage) {
      state.kakaoProfileImage = kakaoProfileImage;
    },
    setAccessToken(state, accessToken) {
      state.accessToken = accessToken;
    },
  },
  actions: {
    async getUserInfo(store) {
      let token = store.state.accessToken;
      let decode_token = jwt_decode(token);

      axios.defaults.headers.common["Authorization"] = token;
      axios({
        method: "get",
        url: API_BASE_URL + "user/info",
        params: {
          userClientId: decode_token.userClientId,
          userLoginPlatformString: "kakao",
        },
      })
        .then((response) => {
          let user = response.data.user;
          store.commit("setUser", user);
          store.commit("setUserId", user.userId);
          store.commit("setUserNickname", user.userNickname);
          store.commit("setUserProfileImage", user.userProfileImage);
          store.commit("setUserInputNickname", user.userNickname);
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
  // modules: {},
};
