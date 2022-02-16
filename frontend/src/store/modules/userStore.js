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
      state.accessToken = accessToken;
    },
  },
  actions: {
    async getUserInfo(store) {
      let token = store.state.accessToken;
      let decode_token = jwt_decode(token);
      console.log("decode_token: ^^^^^^");
      console.log(decode_token);
      await getUserByClientId(
        decode_token.userClientId,
        store.state
        // ({ _user }) => {
        // commit("SET_USER_INFO", data.userInfo);
        // console.log("decode_token: " + decode_token);
        // set ~~~~~~~~~~;
        // axios.defaults.headers.common["Authorization"] = token;
        // console.log("회원정보>>" + _user);
        // },
        // (error) => {
        // console.log("로그인에러", error);
        // }
      );
      // .then(() => {
      // console.log("로그인 완료");
      // console.log(user);
      // this.setUser(user);
      // this.setUserId(user.userId);
      // this.setUserLoginPlatform("kakao");
      // this.setUserClientId(user.userClientId);
      // this.setUserNickname(user.userNickname);
      // this.setUserInputNickname(user.userInputNinkname);
      // this.setUserProfileImage(user.userProfileImage);
      // })
      // .catch((error) => {
      // console.log(error);
      // });

      async function getUserByClientId(userClientId, state) {
        axios({
          method: "get",
          url: API_BASE_URL + "user/info",
          params: {
            userClientId: userClientId,
            userLoginPlatformString: "kakao",
          },
          headers: {
            Authorization: "Bearer " + token,
          },
        })
          .then((response) => {
            console.log(response);
            state.user = response.user;
            // state.userId = response.user.userId;
            // state.userNickname = response.user.userNickname;
            // state.userProfileImage = response.user.userProfileImage;
            // state.userInputNickname = response.user.userInputNickname;
            // state.userLoginPlatform = response.user.userLoginPlatform;
            // state.userClientId = response.user.userClientId;
            // state.kakaoProfileImage = response.user.kakaoProfileImage;
          })
          .catch((error) => {
            console.log(error);
          });
      }
    },
  },
  // modules: {},
};
