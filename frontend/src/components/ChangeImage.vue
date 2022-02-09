<template>
  <div>
    <div>
      <label for="chooseFile">
        Click
      </label>
    </div>
    <input ref="image" @change="handleImageUpload" type="file" id="chooseFile" name="chooseFile" accept="image/*">
    <img v-if="state.preview" :src="state.preview" class="profile-image" alt="Preview">
  </div>
  


  <!-- modal -->
  <div class="modal" :class="{ 'showModal': state.isChangingImage }">
    <div class="modal-dialog">
      <div class="modal-content">
        <!-- modal-header -->
        <div class="modal-header">
          <button
            @click="toggleChangeImage" type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close">
          </button>
        </div>
        <!-- modal-header -->
        <!-- modal-body -->
        <div class="modal-body">
          <!-- 영화사진 -->
          <div class="me-5">
            <img :src="image"  alt="" class="card-img-top">
          </div>
        </div>
        <!-- modal-body -->
        <!-- modal-footer -->
        <div class="modal-footer">
          <button @click="toggleChangeImage" type="button" class="btn btn-outline">닫기</button>
        </div>
      </div>
    </div>
  </div>



  <div>
    <button @click="uploadImage()">이미지변경!</button>
  </div>
</template>

<script>
import { ref, reactive, onMounted, } from "vue";
import AWS from "aws-sdk";
import axios from "axios";
import { useStore } from "vuex";

export default {
  name: "ChangeImage",
  props: {
    msg: String,
  },
  setup() {
    const store = useStore();
    const getters = store.getters;

    const state = reactive({
      isChangingImage: false,
      image : null,
      preview: null,
    })

    const uploadImage = () => {
      if (state.image) {
        AWS.config.update({
          region: 'ap-northeast-2',
          credentials: new AWS.CognitoIdentityCredentials({
            IdentityPoolId: 'ap-northeast-2:f52ba70c-f5cf-4db7-abba-8435eacd4828',
          })
        })
        // Use S3 ManagedUpload class as it supports multipart uploads
        const upload = new AWS.S3.ManagedUpload({
          params: {
            Bucket: 'tour-together--s3',
            // user이름이랑 같이
            Key: state.image.name,
            Body: state.image
          }
        });
        const promise = upload.promise();
  
        promise.then(
          function(data) {
            console.log(typeof(data.Location))
            alert("Successfully uploaded photo.");
            axios({
              method: 'patch',
              url: 'https://i6a105.p.ssafy.io:8081/user/updateImage/',
              data: {
                userLoginPlatform: getters.getUserLoginPlatform,
                userClientId: getters.getUserClientId,
                userProfileImage: data.Location,
              },
            })
              .then((res) => {
                store.commit("setUserProfileImage", data.Location)
                console.log(res)
              });
          },
          function(err) {
            return alert("There was an error uploading your photo: ", err.message);
          }
        );
      } else {
        console.log(state.image)
        alert('이미지를 선택해주세요!')
      }
      
    };

    const imageRef = ref(null)
    // const hello = ref(null)
    const handleImageUpload = (event) => {
      console.log(event.target.files[0])
      const imageFile = event.target.files[0]
      state.image = imageFile
      if(imageFile) {
        state.preview = URL.createObjectURL(imageFile)
      } else {
        state.preview = null
      }
    }

    onMounted(() => {
    
    });

    return {
      state,
      uploadImage,
      handleImageUpload,
      imageRef,
      getters,
    };
  }
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
