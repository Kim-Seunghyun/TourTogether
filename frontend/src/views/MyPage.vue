<template>
  <div>
    <div>
      <div class="profile-image">
        <img
          class="object-fit-contain"
          :src="computedGetters['userStore/getUserProfileImage']"
          alt="my-profile-image"
        />
      </div>
      <div>
        <ChangeImage />
      </div>
    </div>
    <div v-if="!state.isChangingNickname">
      <span>{{ computedGetters["userStore/getUserNickname"] }}</span>
      <button @click="toggleChangeNickname" style="margin-left: 20px">
        닉네임변경
      </button>
    </div>
    <div v-else>
      <input
        type="text"
        @keyup="setUserInputNickname"
        :value="computedGetters['userStore/getUserNickname']"
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
    <div v-if="!state.isDeletingAccount">
      <button @click="toggleDeleteAccount" style="margin-left: 20px">
        회원탈퇴
      </button>
    </div>
    <div v-else>
      <input
        type="text"
        @keyup="setUserInputEmail"
        placeholder="카카오계정 ID를 입력해주세요"
        v-model="state.userInputEmail"
      />
      <button @click="[toggleDeleteAccount(), deleteAccount()]">
        회원탈퇴
      </button>
      <button @click="toggleDeleteAccount">취소</button>
    </div>
  </div>
</template>

<script>
import { reactive, onMounted, computed } from "vue";
import { useStore, mapGetters } from "vuex";
import axios from "axios";
import { API_BASE_URL } from "@/config/index.js";
import { LOCALHOST } from "@/config/index.js";
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

    const computedGetters = computed(() => store.getters);
    const getters = store.getters;
    const state = reactive({
      isChangingNickname: false,
      isDeletingAccount: false,
      userInputEmail: '',
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
        url: LOCALHOST + "user/updateNickname/",
        // url: "http://localhost:8081/user/updateNickname/",
        data: {
          userLoginPlatform: getters["userStore/getUserLoginPlatform"],
          userNickname: getters["userStore/getUserNickname"],
          userClientId: getters["userStore/getUserClientId"],
          newUserNickname: getters["userStore/getUserInputNickname"],
        },
      }).then((res) => {
        store.commit("userStore/setUserNickname", res.data.user.userNickname);
      });
    };

    const toggleDeleteAccount = () => {
      if (state.isDeletingAccount) {
        state.isDeletingAccount = false;
      } else {
        state.isDeletingAccount = true;
      }
      console.log(state.userInputEmail)
    };
    const setUserInputEmail = (event) => {
      store.commit("userStore/setUserInputEmail", event.target.value);
      state.userInputEmail
    };
    const deleteAccount = () => {
      console.log(state.userInputEmail)
      axios({
        method: 'delete',
        url: API_BASE_URL + 'user/delete',
        data: {
          userId: getters["userStore/getUserId"],
          userEmail: state.userInputEmail
        },
      })
        .then(() => {
          // unlink() {  // 카카오 계정 연결끊기
          window.Kakao.API.request({
            url: "/v1/user/unlink",
            success: function (response) {
              console.log(response);
              window.Kakao.Auth.logout();
            },
            fail: function (error) {
              console.log(error);
              alert(error);
              return;
            },
          });
          alert("회원탈퇴되었습니다!");
        })
        .catch((err) => {
          console.log(err);
        });
    };

    onMounted(() => {
      // if (!getters["userStore/getUserClientId"]) {
      //   alert("로그인해주세요!");
      // }
      // axios({
      //   method: "get",
      //   url: API_BASE_URL + "board/user",
      //   data: {
      //     userId: getters["userStore/getUserId"],
      //   },
      // }).then((res) => {
      //   console.log("로그인성공", res);
      // });
    });

    return {
      computedGetters,
      getters,
      state,
      toggleChangeNickname,
      setUserInputNickname,
      submitNickname,
      toggleDeleteAccount,
      deleteAccount,
      setUserInputEmail,
    };
  },
};
</script>

<style scoped>
.profile-image {
  border: 1px solid gray;
  width: 290px;
  height: 290px;
  border-radius: 60%;
  overflow: hidden;
}
.object-fit-contain {
  width: 290px;
  height: 290px;
  object-fit: contain;
}
</style>
