<template>
  <div>
    <h1>Schedule 제발!!</h1>
    <div id="memoConnect">
      <button @click="connectSocket()">일정 시작하기</button>
    </div>
  
    <div v-for="(item,index) in arr" :key="index">
      <span>
        {{ index }} : {{ item }}
      </span>
    </div>
    <button @click="updateArray()">일정바꾸기</button>
    <p id="ptag"></p>
    <hr>

    <div v-for="(item,index) in list" :key="index">
      <p>
        {{ item.name }}
      </p>
    </div>
    <textarea id="area" cols="30" rows="10">hi</textarea>
    <button @click="change()">list 순서 바꾸기</button>

  </div>
</template>

<script>
import SockJS from "sockjs-client";

export default {
  name: "Sch2",
  components: {
    
  },
  data() {
    return {
      arr: ["a", "b", "c"],
      list: [
        {
          id: 2,
          name: "1번",
        },
        {
          id: 1,
          name: "2번",
        }
      ]
    }
  },
  computed: {

  },
  methods: {
    connectSocket() {
      this.sockjs = new SockJS("http://localhost:8081/sch2");
      this.sockjs.onopen = function () {
        // websocket을 열어줄 때 redis에서 data를 가져와야할거같음
        // console.log(this.arr);
        this.arr =["A", "B", "C"];
        console.log("연결");
        alert("연결 성공");
        document.getElementById("memoConnect").setAttribute("hidden", "hidden");
      }
      // this.sockjs.onmessage = function (data) {
      //   console.log(typeof(data.data));
      //   console.log("data받음 > " + data.data);
      //   // this.arr[0] = "데이터받음";
      // },
      this.sockjs.addEventListener('message', function (event) {
        console.log(event.data);
        console.log("데이터 받고 여기" + this.arr);
        this.arr[0] = "Z";
        console.log("데이터 받고 저기" + this.arr);
        var p = document.getElementById("ptag");
        p.innerText = (event.data);
        var area = document.getElementById("area");
        area.innerText = (event.data);
      })
    },
    sendMessage() {
      console.log("sendMessage: ");
    },
    change() {
      this.list[0].id=2;
      this.list[1].id=1;
      console.log(this.list);
      console.log();
    },
    updateArray () {
      this.arr[0] = "Z";
      this.arr[1] = "X";
      this.sockjs.send(this.arr);
    },

  }
}
</script>

<style>

</style>