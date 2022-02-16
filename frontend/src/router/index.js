import { createRouter, createWebHistory } from "vue-router";
// import Home from "../views/Home.vue";
// import Map from "../views/Map.vue";
import MyPage from "../views/MyPage.vue";
import KakaoLoginCallback from "../views/KakaoLoginCallback.vue";
import FavoritePage from "../views/FavoritePage.vue";
import Dashboard from "@/views/Dashboard.vue";
import Tables from "@/views/Tables.vue";
import Profile from "@/views/Profile.vue";
import SignIn from "@/views/SignIn.vue";
import SignUp from "@/views/SignUp.vue";
import Memo from "../views/Memo.vue";
import Sch2 from "../views/Sch2.vue";
import Board from "@/views/Board.vue";
import store from "@/store/index.js";

const onlyAuthUser = async (to, from, next) => {
  const checkUserInfo = store.getters["userStore/getUserId"];
  const getUserInfo = store.actions["userStore/getUserInfo"];
  let token = sessionStorage.getItem("access-token");
  if (checkUserInfo == null && token) {
    await getUserInfo(token);
  }
  console.log("checkUserInfo!!!!! -> " + checkUserInfo);
  if (!checkUserInfo) {
    alert("로그인이 필요한 페이지입니다..");
    router.push({ name: "Dashboard" });
  } else {
    next();
  }
};

const routes = [
  {
    path: "/",
    name: "Home",
    // component: Home,
    redirect: "/dashboard",
  },
  {
    path: "/board/:boardRandom",
    name: "Board",
    beforeEnter: onlyAuthUser,
    component: Board,
  },
  {
    path: "/mypage",
    name: "MyPage",
    beforeEnter: onlyAuthUser,
    component: MyPage,
  },
  {
    path: "/favoritepage",
    name: "FavoritePage",
    beforeEnter: onlyAuthUser,
    component: FavoritePage,
  },
  {
    path: "/kakao-login-callback",
    name: "KakaoLoginCallback",
    component: KakaoLoginCallback,
  },
  {
    path: "/dashboard",
    name: "Dashboard",
    component: Dashboard,
  },
  {
    path: "/tables",
    name: "Tables",
    component: Tables,
  },
  {
    path: "/profile",
    name: "Profile",
    beforeEnter: onlyAuthUser,
    component: Profile,
  },
  {
    path: "/sign-in",
    name: "SignIn",
    component: SignIn,
  },
  {
    path: "/sign-up",
    name: "SignUp",
    component: SignUp,
  },
  {
    path: "/memo",
    name: "Memo",
    beforeEnter: onlyAuthUser,
    component: Memo,
  },
  {
    path: "/sch2",
    name: "Sch2",
    component: Sch2,
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
