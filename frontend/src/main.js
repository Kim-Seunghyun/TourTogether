import { createApp } from "vue";
import VueClipboard from "vue-clipboard2";
import App from "./App.vue";
import store from "./store";
import router from "./router";
import "./assets/css/nucleo-icons.css";
import "./assets/css/nucleo-svg.css";
import SoftUIDashboard from "./soft-ui-dashboard";
window.Kakao.init(process.env.VUE_APP_KAKAO_JAVASCRIPT_KEY);

createApp(App)
  .use(router)
  .use(store)
  .use(SoftUIDashboard)
  .use(VueClipboard)
  .mount("#app");
