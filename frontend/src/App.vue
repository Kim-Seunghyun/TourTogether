<template>
  <div id="nav">
    <router-link to="/">Home</router-link> |
    <router-link to="/about">About</router-link> | 
    <router-link to="/Login" v-if="!accessToken">Login</router-link>||
    <router-link to="#" v-on:click="logout()">Logout</router-link>
    <router-link to="#" v-show="accessToken" v-on:click="unlink()"> | Kakao Unlink</router-link>
  </div>
  <router-view />
</template>

<script>
import router from "./router";
import axios from "axios";
import { reactive } from '@vue/reactivity';
import { computed, watch, onMounted } from 'vue';
import { useStore } from 'vuex';

export default {
  setup() {
    const state = reactive({
        accessToken: window.Kakao.Auth.getAccessToken(),
    })
    const store = useStore();
    const counter = computed(() => store.state.counter);
    const test = computed(() => store.getters);
    const inc = () => store. commit("setCounter", counter.value + 1)
    const login = () => {
      window.Kakao.Auth.authorize({
        redirectUri: "http://localhost:8080/kakao-login-callback/",
      });
    }
    const accessToken = watch(console.log(state.accessToken))
    const logout = (type) => { // 카카오 로그아웃
      window.Kakao.Auth.logout(function () {
        if (type) { // "unlink"
          alert("Unlinked Kakao Account!");
        } else {
          alert("Logout Account!");
        }
        router.push("login");
      });
    }

    onMounted(() => { // 최초 한번만 호출 (내가 작성한거 아님)
      // 우리 api에 id있는지 확인, 있으면 기존 정보 가져오고 없으면 window.Kakao.API.request
      window.Kakao.API.request({
        url: '/v2/user/me',
        data: {
          property_keys: ["properties.nickname", "kakao_account.email", "properties.profile_image"]
        },
        success: function (response) {
          let email = null
          // kakao developers에서 email_needs부분 확인 요망
          if(response.kakao_account.has_email & !response.kakao_account.email_needs_agreement) {
            email = response.kakao_account.email
          }
          // 우리 db에 idp에 상응하는 user정보가 없다면 이렇게하고
          // ㄴㄴ 백에서 처리하면 될듯
          axios({
            method: 'post',
            url: 'http://localhost:8081/user/login',
            data: {
              userLoginPlatform: 'kakao',
              userClientId: response.id,
              userEmail: email,
              userName: response.properties.nickname,
              userProfileImage: response.properties.profile_image,
            }
          })
            .then(res => {
              store.commit("setUserLoginPlatform", 'kakao')
              store.commit("setUserClientId", res.data.user.userClientId)
              store.commit("setUserNickname", res.data.user.userNickname)
              store.commit("setUserInputNickname", res.data.user.userNickname)
              store.commit("setUserProfileImage", res.data.user.userProfileImage)
            })
        },
        fail: function (error) {
          console.log(error)
        },
      })
    })

    return { state, counter, inc, test, login, logout, accessToken}
  }
  // methods: {
  //   unlink() {  // 카카오 계정 연결끊기
  //     let logout = this.logout;
  //     window.Kakao.API.request({
  //       url: "/v1/user/unlink",
  //       success: function (response) {
  //         console.log(response);
  //         logout("unlink");
  //       },
  //       fail: function (error) {
  //         console.log(error);
  //         alert(error);
  //         return;
  //       },
  //     });
  //   },
  // },
  // watch: {
  //   accessToken: function () { // 토큰이 변경 확인
  //     console.log(this.accessToken);
  //   },
    // $route(to) { // 라우트 변경 될때 마다 확인하여 (로그인체크)
    //   this.accessToken = window.Kakao.Auth.getAccessToken();
    //   if (to.name != "Login" && to.name != "KakaoLoginCallback") {
    //     if (!this.accessToken) {
    //       console.log("Not logged in.");
    //       location.href = "/login";
    //     }
    //   }
    // },
  // },
};
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

#nav {
  padding: 30px;
}

#nav a {
  font-weight: bold;
  color: #2c3e50;
}

#nav a.router-link-exact-active {
  color: #42b983;
}
</style>
