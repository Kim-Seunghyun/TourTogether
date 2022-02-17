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
              <button @click="toggleChangeNickname" class="button-change-nickname" style="margin-left: 20px">
                닉네임변경
              </button>
            </div>
            <div v-else>
              <input
                type="text"
                @keyup="setUserInputNickname"
                :value="computedGetters['userStore/getUserNickname']"
                @keyup.enter="submitNickname"
              />
              <button class="button-change-nickname" @click="[toggleChangeNickname(), submitNickname()]">확인</button>
              <button class="button-change-nickname" @click="toggleChangeNickname">취소</button>
            </div>
          </div>
          <div id="userDelete">
            <div v-if="!state.isDeletingAccount">
              <button class="button-delete-account" @click="toggleDeleteAccount">
                회원탈퇴
              </button>
            </div>
            <div v-else>
              <input
                type="text"
                @keyup="setUserInputEmail"
                placeholder="카카오계정 ID를 입력해주세요"
                v-model="state.userInputEmail"
                @keyup.enter="deleteAccount"
              />
              <button class="button-delete-account" @click="[toggleDeleteAccount(), deleteAccount()]">
                회원탈퇴
              </button>
              <button class="button-delete-account" @click="toggleDeleteAccount">취소</button>
            </div>
          </div>
          <div style="margin-top: 20px">
            <!-- <span class="profiletext">(수정)</span> -->
            <span class="profiletext">등급 🏅</span>
          </div>
          <div style="margin-top: 20px">
            <div class="d-flex justify-content-between">
              <div>
                <div class="profiletext">진행중인 일정 </div>
                <div class="profiletext-num">{{ computedGetters["boardStore/getBoardsIng"].length }}</div>
              </div>
              <div>
                <div class="profiletext">완료한 일정 </div>
                <div class="profiletext-num">{{ computedGetters["boardStore/getBoardsDone"].length }}</div>
              </div>
              <div>
                <div class="profiletext">좋아한 일정 </div>
                <div class="profiletext-num">{{ computedGetters["boardStore/getBoardsLikeId"].length }}</div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="d-flex justify-content-center select-schedule">
        <div class="scheduleTap" @click="toggleBoards('boardsIng')"> ✈️ 진행중인 일정</div>
        <div class="scheduleTap middle-schedule-tap"  @click="toggleBoards('boardsDone')"> 🛬 완료된 일정</div>
        <div class="scheduleTap" @click="toggleBoards('boardsLike')"> ❤️ 좋아요 누른 일정</div>
      </div>
      <div>
        <div v-show="state.isBoardIng" class="row card-container">
          <BoardIng
            v-for="board in computedGetters['boardStore/getBoardsIng']"
            :board="board"
            :key="board"
            />
        </div>
        <div v-show="state.isBoardDone" class="row card-container">
          <BoardDone 
            v-for="board in computedGetters['boardStore/getBoardsDone']"
            :board="board"
            :key="board"
          />
        </div>
        <div v-show="state.isBoardLike" class="row card-container">
          <BoardLike
            v-for="board in computedGetters['boardStore/getBoardsLike']"
            :board="board"
            :key="board"
          />
        </div>
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
      if (!getters["userStore/getUserInputNickname"]) {
        alert('새로운 닉네임을 입력해주세요!')
      } else {
        axios({
          method: "patch",
          url: API_BASE_URL + "user/updateNickname/",
          data: {
            userLoginPlatform: getters["userStore/getUserLoginPlatform"],
            userNickname: getters["userStore/getUserNickname"],
            userClientId: getters["userStore/getUserClientId"],
            newUserNickname: getters["userStore/getUserInputNickname"],
          },
        }).then((res) => {
          store.commit("userStore/setUserNickname", res.data.user.userNickname);
        }).catch(err => {
          if (err.response.data.statusCode === 403) {
            alert('이미 사용중인 닉네임입니다.')
          }
        })
      }
    };

    const toggleDeleteAccount = () => {
      if (state.isDeletingAccount) {
        state.isDeletingAccount = false;
      } else {
        state.isDeletingAccount = true;
      }
    };
    const deleteAccount = () => {
      axios({
        method: "delete",
        url: API_BASE_URL + "user/delete",
        data: {
          userId: getters["userStore/getUserId"],
          userEmail: state.userInputEmail,
        },
      })
        .then((res) => {
          if(!res.data.successDelete) {
            alert('입력하신 email이 카카오계정 ID와 다릅니다!')
          } else {
            // unlink() {  // 카카오 계정 연결끊기
            window.Kakao.API.request({
              url: "/v1/user/unlink",
              success: function () {
                router.push("/");
                store.commit("userStore/setUser", null);
                store.commit("userStore/setUserId", "");
                store.commit("userStore/setUserLoginPlatform", "");
                store.commit("userStore/setUserClientId", "");
                store.commit("userStore/setUserNickname", "");
                store.commit("userStore/setUserInputNickname", "");
                store.commit("userStore/setUserProfileImage", "");
                store.commit("boardStore/setBoardsIng", "");
                store.commit("boardStore/setBoardsDone", "");
                store.commit("boardStore/setBoardsLike", "");
                store.commit("boardStore/setBoardsLikeId", "");
                alert('탈퇴되었습니다!')
              },
              fail: function (error) {
                alert(error);
                return;
              },
            });
          }
        })
        // 모두 200으로 처리된다. true, false로 검증값 반환.
        // .catch(() => {
        // });
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
        success: function () {
          store.commit("userStore/setUser", null);
          store.commit("userStore/setUserId", "");
          store.commit("userStore/setUserLoginPlatform", "");
          store.commit("userStore/setUserClientId", "");
          store.commit("userStore/setUserNickname", "");
          store.commit("userStore/setUserInputNickname", "");
          store.commit("userStore/setUserProfileImage", "");
          alert('unlink 되었습니다!')
          router.push("");
        },
        fail: function (error) {
          alert(error);
          return;
        },
      });
    }

    onMounted(() => {
      axios({
        method: 'post',
        url: API_BASE_URL + 'board/searchByUserId/proceeding',
        data: {
          userId: getters["userStore/getUserId"],
        }
      }).then(res => {
        store.commit("boardStore/setBoardsIng", res.data.myBoards)
      })
      axios({
        method: 'post',
        url: API_BASE_URL + 'board/searchByUserId/finish',
        data: {
          userId: getters["userStore/getUserId"],
        }
      }).then(res => {
        store.commit("boardStore/setBoardsDone", res.data.myBoards)
      })
      axios({
        method: "post",
        url: 
        API_BASE_URL + "board/searchLikeBoardByUserId",
        data: {
          userId: getters["userStore/getUserId"],
        },
      }).then(res => {
        store.commit("boardStore/setBoardsLike", res.data.myBoards)
        store.commit("boardStore/setBoardsLikeId", res.data.myBoards)
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
  width: 270px;
  height: 270px;
  border-radius: 60%;
  overflow: hidden;
}
.object-fit-contain {
  width: 270px;
  height: 270px;
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
  padding-right: 267px;
  padding-left: 113px
}

.container {
  display: flex;
}

.button-change-nickname {
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

.button-delete-account {
  background: white;
  border: 1px solid #dbdbdb;
  border-radius: 10px;
  padding: 5px 10px;
  font-family: "paybooc-Light", sans-serif;
  text-decoration: none;
  font-weight: 600;
  transition: 0.25s;
  margin: 3px 3px 3px 0;
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
  margin-right: 30px;
  margin-top: 20px;
}
.profiletext-num {
  font-family: "paybooc-Light", sans-serif;
  font-weight: 600;
  font-size : 18px;
  margin-right: 30px;
  text-align: center;
  margin-top: 5px;
}

.scheduleTap {
  cursor: pointer;
  font-weight: 600;
  padding: 11px 0 20px 0;
  width: 400px;
}
.middle-schedule-tap {
  border-left: 1px solid gray;
  border-right: 1px solid gray;
}
.select-schedule {
  border-bottom: 1px solid gray;
  margin-top: 25px;
  margin-bottom: 6px;
}
.card-container {
  padding: 0 10px;
}

.profilePart {
  text-align: left;
}
</style>