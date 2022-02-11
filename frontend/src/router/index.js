import { createRouter, createWebHistory } from "vue-router";
// import Home from "../views/Home.vue";
import Map from "../views/Map.vue";
import Login from "../views/Login.vue";
import MyPage from "../views/MyPage.vue";
import KakaoLoginCallback from "../views/KakaoLoginCallback.vue";
import FavoritePage from "../views/FavoritePage.vue";
import Dashboard from "@/views/Dashboard.vue";
import Tables from "@/views/Tables.vue";
import Profile from "@/views/Profile.vue";
import SignIn from "@/views/SignIn.vue";
import SignUp from "@/views/SignUp.vue";
import Memo from "../views/Memo.vue";
import Memo2 from "../views/Memo2.vue";
import Memo3 from "../views/Memo3.vue";
import Schedule from "../views/Schedule.vue";
import Sch2 from "../views/Sch2.vue";

import store from "@/store/index.js";

const onlyAuthUser = async (to, from, next) => {
  // console.log(store);
  const checkUserInfo = store.getters["userStore/getUserId"];
  console.log("checkUserInfo!!!!! -> " + store.getters["userStore/getUserId"]);
  console.log(checkUserInfo);
  if (!checkUserInfo) {
    alert("로그인이 필요한 페이지입니다..");
    // next({ name: "SignIn" });
    router.push({ name: "Login" });
    // location.href = `/login`;
  } else {
    // console.log("로그인 했다.");
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
    name: "Map",
    beforeEnter: onlyAuthUser,
    component: Map,
  },
  {
    path: "/login",
    name: "Login",
    component: Login,
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
    name: "Sign In",
    component: SignIn,
  },
  {
    path: "/sign-up",
    name: "Sign Up",
    component: SignUp,
  },
  {
    path: "/memo",
    name: "Memo",
    beforeEnter: onlyAuthUser,
    component: Memo,
  },
  {
    path: "/memo2",
    name: "Memo2",
    beforeEnter: onlyAuthUser,
    component: Memo2,
  },
  {
    path: "/memo3",
    name: "Memo3",
    beforeEnter: onlyAuthUser,
    component: Memo3,
  },
  {
    path: "/schedule",
    name: "Schedule",
    beforeEnter: onlyAuthUser,
    component: Schedule,
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
