<template>
  <div>
    <div>
      <div class="profile-image">
        <img
          :src="computedGetters.getUserProfileImage"
          alt="my-profile-image"
          @click="changeProfileImage"
        />
      </div>
      <div>
        <ChangeImage />
      </div>
    </div>
    <div v-if="!state.isChangingNickname">
      <span>{{ computedGetters.getUserNickname }}</span>
      <button @click="toggleChangeNickname" style="margin-left: 20px">
        닉네임변경
      </button>
    </div>
    <div v-else>
      <input
        type="text"
        @keyup="setUserInputNickname"
        :value="computedGetters.getUserInputNickname"
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
import { computed, reactive, onMounted } from "vue";
import { useStore } from "vuex";
import axios from "axios";

import ChangeImage from "@/components/ChangeImage.vue";

export default {
  components: {
    ChangeImage,
  },
  setup() {
    const store = useStore();

    const computedGetters = computed(() => store.getters.userStore);
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
      store.commit("setUserInputNickname", event.target.value);
    };
    const submitNickname = () => {
      axios({
        method: "patch",
        url: "https://i6a105.p.ssafy.io:8080/api/user/updateNickname",
        // url: "http://localhost:8081/user/updateNickname/",
        data: {
          userLoginPlatform: getters.getUserLoginPlatform,
          userNickname: getters.getUserNickname,
          userClientId: getters.getUserClientId,
          newUserNickname: getters.getUserInputNickname,
        },
      }).then((res) => {
        console.log(res);
        console.log(res.data.user.userNickname);
        store.commit("setUserNickname", res.data.user.userNickname);
      });
    };

    onMounted(() => {
      if (!getters.getUserClientId) {
        console.log(getters.getUserClientId);
        console.log(getters.getUserNickname);
        alert("로그인해주세요!");
      }
      axios({
        method: "get",
        url: "https://i6a105.p.ssafy.io:8080/api/board/user",
        data: {
          userClientId: getters.getUserClientId,
        },
      }).then((res) => {
        console.log(res);
      });
    });

    return {
      computedGetters,
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
