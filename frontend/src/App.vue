<template>
  <!-- <sidenav
    :custom_class="this.$store.state.mcolor"
    :class="[
      this.$store.state.isTransparent,
      this.$store.state.isRTL ? 'fixed-end' : 'fixed-start',
    ]"
    v-if="this.$store.state.showSidenav"
  /> -->
  <main
    class="main-content position-relative max-height-vh-100 h-100 border-radius-lg"
    :style="this.$store.state.isRTL ? 'overflow-x: hidden' : ''"
  >
    <!-- nav -->

    <navbar
      :class="[navClasses]"
      :textWhite="this.$store.state.isAbsolute ? 'text-white opacity-8' : ''"
      :minNav="navbarMinimize"
      v-if="this.$store.state.showNavbar"
    />
    <router-view />
    <app-footer v-show="this.$store.state.showFooter" />
    <configurator
      :toggle="toggleConfigurator"
      :class="[
        this.$store.state.showConfig ? 'show' : '',
        this.$store.state.hideConfigButton ? 'd-none' : '',
      ]"
    />
  </main>
  <!-- <router-view /> -->
</template>

<script>
// import Sidenav from "./examples/Sidenav";
import Configurator from "@/examples/Configurator.vue";
import Navbar from "@/examples/Navbars/Navbar.vue";
import AppFooter from "@/examples/Footer.vue";
// import axios from "axios";
// import { API_BASE_URL } from "@/config/index.js";
// import { LOCALHOST } from "@/config/index.js";
import { reactive } from "vue";
import { watch, onMounted } from "vue";
import { mapMutations } from "vuex";

export default {
  components: {
    // Sidenav,
    Configurator,
    Navbar,
    AppFooter,
  },
  methods: {
    ...mapMutations(["toggleConfigurator", "navbarMinimize"]),
  },
  computed: {
    navClasses() {
      return {
        "position-sticky blur shadow-blur mt-4 left-auto top-1 z-index-sticky":
          this.$store.state.isNavFixed,
        "position-absolute px-4 mx-0 w-100 z-index-2":
          this.$store.state.isAbsolute,
        "px-0 mx-4 mt-4": !this.$store.state.isAbsolute,
      };
    },
  },
  beforeMount() {
    this.$store.state.isTransparent = "bg-transparent";
  },
  setup() {
    const state = reactive({
      accessToken: window.Kakao.Auth.getAccessToken(),
    });
    // const store = useStore();
    const accessToken = watch(console.log(state.accessToken));

    onMounted(() => {
      // 우리 api에 id있는지 확인, 있으면 기존 정보 가져오고 없으면 window.Kakao.API.request
      // window.Kakao.API.request({
      //   url: "/v2/user/me",
      //   data: {
      //     property_keys: [
      //       "properties.nickname",
      //       "kakao_account.email",
      //       "properties.profile_image",
      //     ],
      //   },
      //   success: function (response) {
      //     let email = null;
      //     if (
      //       response.kakao_account.has_email &
      //       !response.kakao_account.email_needs_agreement
      //     ) {
      //       email = response.kakao_account.email;
      //     }
      //     axios({
      //       method: "post",
      //       url: LOCALHOST + "user/login",
      //       data: {
      //         userLoginPlatform: "kakao",
      //         userClientId: response.id,
      //         userEmail: email,
      //         userName: response.properties.nickname,
      //         userProfileImage: response.properties.profile_image,
      //       },
      //     }).then((res) => {
      //       store.commit("userStore/setUser", res.data.user);
      //       store.commit("userStore/setUserId", res.data.user.userId);
      //       store.commit("userStore/setUserLoginPlatform", "kakao");
      //       store.commit(
      //         "userStore/setUserClientId",
      //         res.data.user.userClientId
      //       );
      //       store.commit(
      //         "userStore/setUserNickname",
      //         res.data.user.userNickname
      //       );
      //       store.commit(
      //         "userStore/setUserInputNickname",
      //         res.data.user.userNickname
      //       );
      //       store.commit(
      //         "userStore/setUserProfileImage",
      //         res.data.user.userProfileImage
      //       );
      //     });
      //   },
      //   fail: function (error) {
      //     console.log(error);
      //   },
      // });
    });

    return { state, accessToken };
  },
  // watch: {
  //   accessToken: function () { // 토큰이 변경 확인
  //     console.log(this.accessToken);
  //   },
  //   $route(to) { // 라우트 변경 될때 마다 확인하여 (로그인체크)
  //     this.accessToken = window.Kakao.Auth.getAccessToken();
  //     if (to.name != "Login" && to.name != "KakaoLoginCallback") {
  //       if (!this.accessToken) {
  //         console.log("Not logged in.");
  //         location.href = "/login";
  //       }
  //     }
  //   },
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
