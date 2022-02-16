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
      console.log("user -> ", state.user);
    },
    setUserId(state, userId) {
      state.userId = userId;
      console.log("user -> ", state.userId);
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
      console.log(this.getUser);
    },
    setKakaoProfileImage(state, kakaoProfileImage) {
      state.kakaoProfileImage = kakaoProfileImage;
    },
    setAccessToken(state, accessToken) {
      console.log("ready to set accessToken: ", accessToken);
      state.accessToken = accessToken;
    },
  },
  actions: {
    // async findById(userid, success, fail) {
    // api.defaults.headers["access-token"] = sessionStorage.getItem("access-token");
    // await api.get(`/user/info/${userid}`).then(success).catch(fail);
    // },

    async getUserInfo(token) {
      console.log("token: " + token);
      // let decode_token = jwt_decode(token);
      // console.log("decode_token: "+decode_token);
      // console.log("디코드콘솔"+decode_token.id)
      // await findById(decode_token.userClientId, ({ data }) => {
      // commit("SET_USER_INFO", data.userInfo);
      // console.log("decode_token: "+decode_token);
      // setAccessToken
      // console.log("회원정보>>"+data.userInfo)
      // }, (error) => {
      // console.log("로그인에러", error);
      // });
    },
  },
  // modules: {},
};
