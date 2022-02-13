<template>
  <div>
    <div>
      <div class="profile-image">
        <img
          :src="userProfileImage"
          alt="my-profile-image"
          @click="changeProfileImage"
        />
      </div>
      <div>
        <ChangeImage />
      </div>
    </div>
    <div v-if="!state.isChangingNickname">
      <span>{{ userNickname }}</span>
      <button @click="toggleChangeNickname" style="margin-left: 20px">
        닉네임변경
      </button>
    </div>
    <div v-else>
      <input
        type="text"
        @keyup="setUserInputNickname"
        v-model="userInputNickname"
      />
      <button @click="[toggleChangeNickname(), submitNickname()]">확인</button>
      <button @click="toggleChangeNickname">취소</button>
    </div>
    <div>
      <span>
        <span>진행중인 일정 |</span>
        <div>1</div>
        <div>2</div>
      </span>
      <span> 완료된 일정 |</span>
      <span> 좋아요 누른 일정</span>
    </div>
  </div>
</template>

<script>
import { reactive, onMounted } from "vue";
import { useStore, mapGetters } from "vuex";
import axios from "axios";

import ChangeImage from "@/components/ChangeImage.vue";

export default {
  components: {
    ChangeImage,
  },
  computed: {
    ...mapGetters({
      userProfileImage: "userStore/getUserProfileImage",
      userNickname: "userStore/getUserNickname",
      userInputNickname: "userStore/getUserInputNickname",
    }),
  },
  setup() {
    const store = useStore();

    // const computedGetters = computed(() => store.getters.userStore);
    const getters = store.getters;
    const state = reactive({
      isChangingNickname: false,
    });
    const toggleChangeNickname = () => {
      if (state.isChangingNickname) {
        state.isChangingNickname = false;
      } else {
        state.isChangingNickname = true;
      }
    };
    const setUserInputNickname = (event) => {
      store.commit("userStore/setUserInputNickname", event.target.value);
    };
    const submitNickname = () => {
      axios({
        method: "patch",
        url: "https://i6a105.p.ssafy.io:8080/api/user/updateNickname",
        // url: "http://localhost:8081/user/updateNickname/",
        data: {
          userLoginPlatform: getters["userStore/getUserLoginPlatform"],
          userNickname: getters["userStore/getUserNickname"],
          userClientId: getters["userStore/getUserClientId"],
          newUserNickname: getters["userStore/getUserInputNickname"],
        },
      }).then((res) => {
        console.log(res);
        console.log(res.data.user.userNickname);
        store.commit("setUserNickname", res.data.user.userNickname);
      });
    };

    onMounted(() => {
      if (!getters["userStore/getUserClientId"]) {
        alert("로그인해주세요!");
        // TODO 여기에 리다이렉트 해야할듯
      }
      axios({
        method: "POST",
        url: "https://i6a105.p.ssafy.io:8080/api/board/searchByUserId",
        data: {
          userId: getters["userStore/getUserId"],
        },
      }).then((res) => {
        console.log("로그인성공", res);
      });
    });

    return {
      // computedGetters,
      getters,
      state,
      toggleChangeNickname,
      setUserInputNickname,
      submitNickname,
    };
  },
};
</script>

<style scoped>
.profile-image {
  width: 290px;
  height: 290px;
  border-radius: 60%;
  overflow: hidden;
}
</style>
