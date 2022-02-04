<template>
  <div>
    <form method="post" enctype="multipart/form-data">
      <div>
        <label for="chooseFile">
          Click
        </label>
      </div>
    <input ref="image" @change="uploadImg" type="file" id="chooseFile" name="chooseFile" accept="image/*">
    </form>
    <img v-if="state.image" :src="state.image" class="profile-image" alt="Preview">
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
    <button @click="changeProfileImage">이미지변경</button>
  </div>
</template>

<script>
import { ref, reactive, onMounted, } from "vue";

export default {
  name: "ChangeImage",
  props: {
    msg: String,
  },
  setup() {
    const state = reactive({
      isChangingImage: false,
      image : null,
    })
    const changeProfileImage = () => {

      };
    const imageRef = ref(null)
    // const hello = ref(null)
    const uploadImg = (event) => {
      console.log(event.target.files[0])
      const imageFile = event.target.files[0]
      if(imageFile) {
        state.image = URL.createObjectURL(imageFile)
        console.log(state.image)
      }
    }

    onMounted(() => {
    
    });

    return {
      state,
      changeProfileImage,
      uploadImg,
      imageRef,
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
