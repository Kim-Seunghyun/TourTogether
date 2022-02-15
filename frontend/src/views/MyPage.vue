<template>
  <div>
    <div id="body">
      <div class="container">
        <div id="profileImagePart">
          <div class="profile-image">
            <img
              class="object-fit-contain"
              :src="computedGetters['userStore/getUserProfileImage']"
              alt="my-profile-image"
            />
          </div>
          <div id="changeImageComponent">
            <ChangeImage />
          </div>
        </div>
        <div class="profilePart">
          <div id="changeNickname">
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
          </div>
          <div id="userDelete">
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
          <div style="margin-top: 20px">
            <span class="profiletext">(수정)</span>
            <span class="profiletext">등급 🏅</span>
          </div>
          <div style="margin-top: 20px">
            <span>
              <span class="profiletext">진행중인 일정 </span>
              <span class="profiletext">완료한 일정 </span><br>
              <span class="profiletext">1</span>
              <span class="profiletext">2</span>
            </span>
          </div>
        </div>
      </div>

      <div>
        <hr style="margin-top : 80px; margin-bottom: 30px;">
        <span class="scheduleTap" @click="toggleBoards('boardsIng')"> ✈️ 진행중인 일정</span>
        <span class="scheduleTap"  @click="toggleBoards('boardsDone')"> 🛬 완료된 일정</span>
        <span class="scheduleTap" @click="toggleBoards('boardsLike')"> ❤️ 좋아요 누른 일정</span>
      </div>
      <div v-show="state.isBoardIng">
        <BoardIng
          v-for="board in computedGetters['boardStore/getBoardsIng']"
          :board="board"
          :key="board"
          />
      </div>
      <div v-show="state.isBoardDone">
        <BoardDone 
          v-for="board in computedGetters['boardStore/getBoardsDone']"
          :board="board"
          :key="board"
        />
      </div>
      <div v-show="state.isBoardLike">
        <BoardLike
          v-for="board in computedGetters['boardStore/getBoardsLike']"
          :board="board"
          :key="board"
        />
      </div>
    </div>
  </div>
</template>

<script>
import { reactive, onMounted, computed } from "vue";
import { useStore } from "vuex";
import axios from "axios";
import { API_BASE_URL } from "@/config/index.js";
import ChangeImage from "@/components/ChangeImage.vue";
import BoardIng from "@/components/BoardIng.vue";
import BoardDone from "@/components/BoardDone.vue";
import BoardLike from "@/components/BoardLike.vue";
import router from "@/router";

export default {
  components: {
    ChangeImage, BoardIng, BoardDone, BoardLike
  },
  setup() {
    const store = useStore();

    const computedGetters = computed(() => store.getters);
    const getters = store.getters;
    const state = reactive({
      isChangingNickname: false,
      isDeletingAccount: false,
      userInputEmail: "",
      isBoardIng: true,
      isBoardDone: false,
      isBoardLike: false,
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
        url: API_BASE_URL + "user/updateNickname/",
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
      console.log(state.userInputEmail);
    };
    const deleteAccount = () => {
      console.log(state.userInputEmail);
      axios({
        method: "delete",
        url: API_BASE_URL + "user/delete",
        data: {
          userId: getters["userStore/getUserId"],
          userEmail: state.userInputEmail,
        },
      })
        .then(() => {
          // unlink() {  // 카카오 계정 연결끊기
          window.Kakao.API.request({
            url: "/v1/user/unlink",
            success: function (response) {
              console.log(response);
              router.push("");
              store.commit("userStore/setUser", null);
              store.commit("userStore/setUserId", "");
              store.commit("userStore/setUserLoginPlatform", "");
              store.commit("userStore/setUserClientId", "");
              store.commit("userStore/setUserNickname", "");
              store.commit("userStore/setUserInputNickname", "");
              store.commit("userStore/setUserProfileImage", "");
              // console.log(getters.getUserId);
              alert('탈퇴되었습니다!')
            },
            fail: function (error) {
              console.log(error);
              alert(error);
              return;
            },
          });
        })
        .catch((err) => {
          console.log(err);
        });
    };

    const toggleBoards = whichBoards => {
      if (whichBoards === 'boardsIng') {
        state.isBoardIng = true,
        state.isBoardDone = false,
        state.isBoardLike = false
      } else if (whichBoards === 'boardsDone') {
        state.isBoardIng = false,
        state.isBoardDone = true,
        state.isBoardLike = false
      } else if (whichBoards === 'boardsLike') {
        state.isBoardIng = false,
        state.isBoardDone = false,
        state.isBoardLike = true
      }
    }

    const kakaoUnlink = () => {
      window.Kakao.API.request({
        url: "/v1/user/unlink",
        success: function (response) {
          console.log(response);
          console.log(store);
          store.commit("userStore/setUser", null);
          store.commit("userStore/setUserId", "");
          store.commit("userStore/setUserLoginPlatform", "");
          store.commit("userStore/setUserClientId", "");
          store.commit("userStore/setUserNickname", "");
          store.commit("userStore/setUserInputNickname", "");
          store.commit("userStore/setUserProfileImage", "");
          // console.log(getters.getUserId);
          alert('unlink 되었습니다!')
          router.push("");
        },
        fail: function (error) {
          console.log(error);
          alert(error);
          return;
        },
      });
    }

    onMounted(() => {
      axios({
        method: 'post',
        url: '/api/board/searchByUserId/proceeding',
        data: {
          userId: 98,
        }
      }).then(res => {
        store.commit("boardStore/setBoardsIng", res.data.myBoards)
      })
      axios({
        method: 'post',
        url: '/api/board/searchByUserId/finish',
        data: {
          userId: 98,
        }
      }).then(res => {
        store.commit("boardStore/setBoardsDone", res.data.myBoards)
      })
      axios({
        method: "post",
        url: 
        // API_BASE_URL + 
        "/api/board/searchLikeBoardByUserId",
        data: {
          userId: 98,
        },
      }).then(res => {
        store.commit("boardStore/setBoardsLike", res.data.myBoards)
        console.log(res.data.myBoards)
      });
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
      toggleBoards,
      kakaoUnlink
    };
  },
};
</script>

<style scoped>
.profile-image {
  border: 1px solid gray;
  width: 250px;
  height: 250px;
  border-radius: 60%;
  overflow: hidden;
}
.object-fit-contain {
  width: 290px;
  height: 290px;
  object-fit: contain;
}

#body {
  width: 1200px;
  margin: 0 auto;
  margin-top: 50px;
}

#changeImageComponent {
  position: relative;
  width: 200px;
  bottom: 30px;
  left: 80px;
}

#profileImagePart {
  padding-right: 280px;
  padding-left: 100px
}

.container {
  display: flex;
}

button {
  background: white;
  border: 1px solid #dbdbdb;
  border-radius: 10px;
  padding: 5px 10px;
  font-family: "paybooc-Light", sans-serif;
  text-decoration: none;
  font-weight: 600;
  transition: 0.25s;
  margin: 10px;
}

input {
  width: 250px;
  border-top: none;
  border-left: none;
  border-right: none;
  border-bottom: 3px solid black;
}

input:focus {
  outline: none;
}

.profiletext {
  font-family: "paybooc-Light", sans-serif;
  font-weight: 600;
  font-size : 18px;
  margin-left: 30px;
  margin-top: 20px;
}

.scheduleTap {
  margin: 70px;
  cursor: pointer;
  font-weight: 600;
}

.profilePart {
  text-align: left;
}
</style>