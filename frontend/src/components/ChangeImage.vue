<template>
  <div>
    <div class="changeImageButton">
      <img src="../assets/circularplusbutton_121982.png" alt="uploadNewImage"
        @click="$refs.imageUpload.click()"
        data-bs-toggle="modal"
        data-bs-target="#modal"/>
    </div>
    <input class="display-none" ref="imageUpload" @change="handleImageUpload" type="file" id="chooseFile" name="chooseFile" accept="image/*">
    <!-- 여기선 object-fit이 적용된다 왜 그럴까 -->
  </div>

  <!-- Modal -->
  <div
    class="modal fade"
    id="modal"
    tabindex="-1"
    aria-labelledby="modalLabel"
    aria-hidden="true"
  >
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="modalLabel">
            프로필이미지 변경
          </h5>
          <button
            type="button"
            class="btn-close"
            data-bs-dismiss="modal"
            aria-label="Close"
          ></button>
        </div>
        <div class="modal-body">
          <div class="profile-image d-inline-block">
            <img v-if="state.preview" :src="state.preview" class="object-fit-contain" alt="Preview">
          </div>
          <button
            @click="bringKakaoProfileImage"
            class="button"
            style="margin-right: 10px;">
            카카오톡 프로필이미지 가져오기
          </button>
          <button
            v-if="state.preview"
            @click="$refs.imageUpload.click()"
            class="button-insta">
            다시 선택하기
          </button>
        </div>
        <div class="modal-footer">
          <button
            type="button"
            class="btn btn-secondary"
            data-bs-dismiss="modal"
          >
            Close
          </button>
          <button @click="uploadImage()" class="btn btn-primary">이미지변경</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { reactive, onMounted } from "vue";
import AWS from "aws-sdk";
import axios from "axios";
import { API_BASE_URL } from "@/config/index.js";
import { useStore } from "vuex";

export default {
  name: "ChangeImage",
  props: {
    
  },
  setup() {
    const store = useStore();
    const getters = store.getters;

    const state = reactive({
      isChangingImage: false,
      image: null,
      preview: null,
      imageURL: null,
    });

    const uploadImage = () => {
      if (state.image) {
        AWS.config.update({
          region: "ap-northeast-2",
          credentials: new AWS.CognitoIdentityCredentials({
            IdentityPoolId:
              "ap-northeast-2:f52ba70c-f5cf-4db7-abba-8435eacd4828",
          }),
        });
        // Use S3 ManagedUpload class as it supports multipart uploads
        const upload = new AWS.S3.ManagedUpload({
          params: {
            Bucket: "tour-together--s3",
            // user이름이랑 같이
            Key: state.image.name,
            Body: state.image,
          },
        });
        const promise = upload.promise();

        promise.then(
          function (data) {
            console.log(typeof data.Location);
            alert("Successfully uploaded photo.");
            axios({
              method: "patch",
              url: API_BASE_URL + "user/updateImage/",
              data: {
                userLoginPlatform: getters["userStore/getUserLoginPlatform"],
                userClientId: getters["userStore/getUserClientId"],
                userProfileImage: data.Location,
              },
            })
              .then(() => {
                store.commit("userStore/setUserProfileImage", data.Location);
                state.preview = ''
              })
              .catch((error) => {
                console.log(error);
              });
          },
          function (err) {
            return alert(
              "There was an error uploading your photo: ",
              err.message
            );
          }
        );
      } else if (state.imageURL) {
        axios({
          method: "patch",
          url: API_BASE_URL + "user/updateImage/",
          data: {
            userLoginPlatform: getters["userStore/getUserLoginPlatform"],
            userClientId: getters["userStore/getUserClientId"],
            userProfileImage: state.imageURL,
          },
        }).then(() => {
            store.commit("userStore/setUserProfileImage", state.imageURL);
            state.preview = null
          })
          .catch((error) => {
            console.log(error);
          });
      } else {
        console.log(state.image);
        alert("이미지를 선택해주세요!");
      }
    };

    // const imageRef = ref(null);
    const handleImageUpload = (event) => {
      console.log(event.target.files[0]);
      const imageFile = event.target.files[0];
      state.image = imageFile;
      if (imageFile) {
        state.preview = URL.createObjectURL(imageFile);
      } else {
        state.preview = null;
      }
      console.log(getters['userStore/getKakaoProfileImage'])
    };

    const bringKakaoProfileImage = () => {
      state.imageURL = getters["userStore/getKakaoProfileImage"]
      if (state.imageURL) {
        state.preview = state.imageURL
      } else {
        alert('회원가입시 카카오이미지를 불러오는 것에 동의하지 않으셨습니다! \
                \n카카오unlink 후 해당 항목에 동의해주세요.')
      }
    }

    onMounted(() => {});

    return {
      state,
      uploadImage,
      handleImageUpload,
      // imageRef,
      getters,
      bringKakaoProfileImage
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
  object-fit: contain;
}
.display-none {
  display: none;
}
.object-fit-contain {
  width: 290px;
  height: 290px;
  object-fit: contain;
}
.button-insta {
  background: white;
  border: 1px solid #dbdbdb;
  border-radius: 10px;
  padding: 6px 11px;
  font-family: "paybooc-Light", sans-serif;
  text-decoration: none;
  font-weight: 600;
  transition: 0.25s;
  margin: 10px;
}
.button {
  background: #fae100;
  border: 1px solid #dbdbdb;
  border-radius: 10px;
  padding: 6px 11px;
  font-family: "paybooc-Light", sans-serif;
  text-decoration: none;
  font-weight: 600;
  transition: 0.25s;
  margin: 10px;
}
</style>
