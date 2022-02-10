<template>
  <div>
    <h1>메모장</h1>
    <div id="memoConnect">
        <button @click="connectSocket()">메모 시작하기</button>
    </div>
  
    <div>
      <textarea type="text" v-model="test"></textarea>
      <p style="white-space: pre-line">test: {{ test }}</p>
    </div>
    <button @click="changeInputText()">클릭</button>
    <div id="memo">
      <textarea id="message" value="" style="height:500px; width:500px"></textarea>
      <button id="sendBtn" @click="sendMessage()">전송</button>
      <p style="white-space: pre-line">v-model 실험: {{ inputText }}</p>
      <div id="memoBox">
        <p id="memocontent"></p>
      </div>
    </div> 
  </div>
</template>

<script>
// import { reactive } from "@vue/reactivity";
// import { onMounted } from "vue";
import SockJS from "sockjs-client";

export default {
  name: "Memo3",
  components: {
  },

  data() {
    return {
      test: "",
      inputText: "",
      sockjs : Object,
    };
  },

  methods: {
    connectSocket() {
      this.sockjs = new SockJS("http://localhost:8081/memo3");
      this.sockjs.onopen = function () {
        console.log("연결");
        alert("연결 성공");
        document.getElementById("memoConnect").setAttribute("hidden", "hidden");
      }
      this.sockjs.onmessage = function (data) {
        // 그냥 p태그 값 바꾸기
        var element = document.getElementById("memocontent");
        element.innerText = (data.data);
        console.log("data 받기" + data.data);
        // textarea
        var message = document.getElementById("message");
        message.innerText = (data.data);
        
      }
    },
    sendMessage() {
      this.sockjs.send(document.getElementById("message").value);
      console.log("data보내기" + document.getElementById("message").value);
    },
    changeInputText() {
      this.inputText = "바껴라"
    },
    searchChangeFunc() {
      console.log(this.inputText);
    }
  },

  // created() {
  //   this.sockjs = new SockJS("http://localhost:8081/memo3");
  //   this.sockjs.onopen = function () {
  //     console.log("연결");
  //     alert("연결 성공");
  //     document.getElementById("memoConnect").setAttribute("hidden", "hidden");
  //     console.log(this.sockjs);
  //   }
  // }

}
</script>

<style>

</style>