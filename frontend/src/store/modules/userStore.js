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
      this.setAccessToken("");
    },
    setKakaoProfileImage(state, kakaoProfileImage) {
      state.kakaoProfileImage = kakaoProfileImage;
    },
    setAccessToken(state, accessToken) {
      console.log("@@@@@@@@@@@@@@@@@setAccessToken: ", accessToken);
      state.accessToken = accessToken;
    },
  },
  actions: {
    async getUserInfo() {
      console.log("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
      // console.log(this.state.accessToken);
      console.log(this.$state.accessToken);
      console.log(this.getAccessToken);
      console.log(this.getAccessToken());
      console.log(this.getAccessToken(this.state));
      console.log(this.getAccessToken(this.$state));
      let token = this.state.accessToken;
      console.log(token);
      console.log("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
      let decode_token = jwt_decode(token);
      console.log("decode_token: " + decode_token);
      console.log("디코드콘솔 ID" + decode_token.id);
      await getUserByClientId(
        decode_token.userClientId,
        ({ data }) => {
          // commit("SET_USER_INFO", data.userInfo);
          console.log("decode_token: " + decode_token);
          // set ~~~~~~~~~~;
          axios.defaults.headers.common["Authorization"] = token;
          console.log("회원정보>>" + data.userInfo);
        },
        (error) => {
          console.log("로그인에러", error);
        }
      )
        .then((res) => {
          this.setUser(res.data.user);
          this.setUserId(res.data.user.userId);
          this.setUserLoginPlatform("kakao");
          this.setUserClientId(res.data.user.userClientId);
          this.setUserNickname(res.data.user.userNickname);
          this.setUserInputNickname(res.data.user.userInputNinkname);
          this.setUserProfileImage(res.data.user.userProfileImage);
        })
        .catch((error) => {
          console.log(error);
        });

      async function getUserByClientId(userClientId, success, fail) {
        axios({
          method: "get",
          url: API_BASE_URL + "user/info",
          data: {
            userClientId: userClientId,
          },
        })
          .then(success)
          .catch(fail);
      }
    },
  },
  // modules: {},
};
