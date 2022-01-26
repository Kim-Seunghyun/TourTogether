import { createApp } from "vue";
import App from "./App.vue";
import store from "./store";
import router from "./router";

window.Kakao.init(process.env.VUE_APP_KAKAO_JAVASCRIPT_KEY)

createApp(App).use(router).use(store).mount("#app");
