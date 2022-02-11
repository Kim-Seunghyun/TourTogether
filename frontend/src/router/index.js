import { createRouter, createWebHistory } from "vue-router";
import Home from "../views/Home.vue";
import Map from "../views/Map.vue";
import Login from "../views/Login.vue";
import MyPage from "../views/MyPage.vue";
import KakaoLoginCallback from "../views/KakaoLoginCallback.vue";
import FavoritePage from "../views/FavoritePage.vue";
import Memo from "../views/Memo.vue";
import Memo2 from "../views/Memo2.vue";
import Memo3 from "../views/Memo3.vue";
import Schedule from "../views/Schedule.vue";
import Sch2 from "../views/Sch2.vue";

const routes = [
  {
    path: "/",
    name: "Home",
    component: Home,
  },
  {
    path: "/map",
    name: "Map",
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
    component: MyPage,
  },
  {
    path: "/favoritepage",
    name: "FavoritePage",
    component: FavoritePage,
  },
  {
    path: "/kakao-login-callback",
    name: "KakaoLoginCallback",
    component: KakaoLoginCallback,
  },
  {
    path: "/memo",
    name: "Memo",
    component: Memo,
  },
  {
    path: "/memo2",
    name: "Memo2",
    component: Memo2,
  },
  {
    path: "/memo3",
    name: "Memo3",
    component: Memo3,
  },
  {
    path: "/schedule",
    name: "Schedule",
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
