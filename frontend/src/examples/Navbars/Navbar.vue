<template>
  <nav
    class="shadow-none navbar navbar-main navbar-expand-lg border-radius-xl"
    v-bind="$attrs"
    id="navbarBlur"
    data-scroll="true"
  >
    <div class="px-3 py-1 container-fluid">
      <router-link to="/" class="px-0 nav-link font-weight-bold"
        >TOUR TOGETHER</router-link
      >

      <div
        class="mt-2 collapse navbar-collapse mt-sm-0 me-md-0 me-sm-4"
        :class="this.$store.state.isRTL ? 'px-0' : 'me-sm-4'"
        id="navbar"
      >
        <div
          class="pe-md-3 d-flex align-items-center"
          :class="this.$store.state.isRTL ? 'me-md-auto' : 'ms-md-auto'"
        ></div>
        <ul class="navbar-nav justify-content-end">
          <li class="nav-item d-flex align-items-center">
            <img
              v-if="!user"
              alt="Kakao logo"
              src="@/assets/kakao_login_medium_narrow.png"
              @click="login()"
            />

            <h6 v-if="user" class="px-0 nav-link font-weight-bold">
              {{ user.userNickname }} 님 &nbsp;
            </h6>

            <router-link
              to="/favoritepage"
              v-if="user"
              class="px-0 nav-link font-weight-bold"
              :class="textWhite ? textWhite : 'text-body'"
              >FavoritePage &nbsp;</router-link
            >

            <router-link
              to="/mypage"
              v-if="user"
              class="px-0 nav-link font-weight-bold"
              :class="textWhite ? textWhite : 'text-body'"
              >MyPage &nbsp;</router-link
            >

            <router-link
              to="/memo2"
              v-if="user"
              class="px-0 nav-link font-weight-bold"
              :class="textWhite ? textWhite : 'text-body'"
              >memo2 &nbsp;</router-link
            >

            <router-link
              to="/dashboard"
              v-if="user"
              v-on:click="logout()"
              class="px-0 nav-link font-weight-bold"
              :class="textWhite ? textWhite : 'text-body'"
              >Logout</router-link
            >
            <!-- <router-link
                to="#"
                v-show="accessToken"
                v-on:click="unlink()"
                class="px-0 nav-link font-weight-bold"
                :class="textWhite ? textWhite : 'text-body'"
              >
                Kakao Unlink</router-link
              > -->
          </li>
        </ul>
      </div>
    </div>
  </nav>
</template>
<script>
import { mapMutations, mapActions, useStore, mapState } from "vuex";
import { onUpdated } from "vue";
import router from "@/router";

const userStore = "userStore";

export default {
  name: "navbar",
  data() {
    return {
      showMenu: false,
    };
  },
  props: ["minNav", "textWhite"],

  created() {
    this.minNav;
  },
  methods: {
    ...mapMutations(["navbarMinimize", "toggleConfigurator"]),
    ...mapActions(["toggleSidebarColor"]),

    toggleSidebar() {
      this.toggleSidebarColor("bg-white");
      this.navbarMinimize();
    },
  },
  setup() {
    const store = useStore();
    const getters = store.getters;
    const login = () => {
      window.Kakao.Auth.authorize({
        // redirectUri: "https://i6a105.p.ssafy.io/kakao-login-callback",
        redirectUri: "http://localhost:8080/kakao-login-callback",
      });
    };
    const logout = () => {
      // 카카오 로그아웃
      window.Kakao.Auth.logout(function () {
        router.push("");
        console.log(store);
        store.commit("userStore/setUser", null);
        store.commit("userStore/setUserId", "");
        store.commit("userStore/setUserLoginPlatform", "");
        store.commit("userStore/setUserClientId", "");
        store.commit("userStore/setUserNickname", "");
        store.commit("userStore/setUserInputNickname", "");
        store.commit("userStore/setUserProfileImage", "");
        // console.log(getters.getUserId);
        alert('로그아웃 되었습니다!')
      });
    }
    onUpdated(() => {
      const navbar = document.getElementById("navbarBlur");
      window.addEventListener("scroll", () => {
        if (window.scrollY > 10 && this.$store.state.isNavFixed) {
          navbar.classList.add("blur");
          navbar.classList.add("position-sticky");
          navbar.classList.add("shadow-blur");
        } else {
          navbar.classList.remove("blur");
          navbar.classList.remove("position-sticky");
          navbar.classList.remove("shadow-blur");
        }
      });
    });
    return { login, logout, getters };
  },
  components: {},
  computed: {
    ...mapState(userStore, ["user"]),
    currentRouteName() {
      return this.$route.name;
    },
  },
};
</script>
