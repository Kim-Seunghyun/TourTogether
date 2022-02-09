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

    <button @click="getAllSchedule()">redis에서 값 찾기</button>

    <ul>
      <li v-for="(sch, index) in scheduleList" :key="index">
        {{ sch }}
      </li>
    </ul>

    <button @click="sendChange()">값 변경 axios 테스트</button>

    <button @click="sendChange()">websocket 보내기</button>
    <hr>

    <textarea id="area" cols="30" rows="10">hi</textarea>

  </div>
</template>

<script>
import axios from "axios";
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
      ],
      scheduleList: Object,
      schdeule: {
        scheduleId: 1,
        boardId: 100,
        day: 1,
        userSpotId: 1,
        tourSpotId: 1,
        order: 1,
        additional: "변경",
      }
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
      this.sockjs.onmessage = function (data) {
        console.log(data);
        console.log(typeof(data.data));
        console.log("data받음 > " + data.data);
        axios({
          method: 'get',
          url: 'http://127.0.0.1:8081/redisschedule/all'
        })
        .then(res => {
          console.log(res.data);
          this.scheduleList = res.data.redisScheduleList;
          console.log(this.scheduleList);
        })
      }
      // this.sockjs.addEventListener('message', function () {
      //   axios({
      //     method: 'get',
      //     url: 'http://127.0.0.1:8081/redisschedule/all'
      //   })
      //     .then(res => {
      //       console.log("websocket 받은 res : " + res.data.redisScheduleList);
      //       this.scheduleList = res.data.redisScheduleList;
      //       console.log("websocket 받은 후 : " + this.scheduleList);
      //     })
      //     .catch(err => {
      //       console.log(err);
      //   })
      //   console.log("websocket 받은 후 : " + this.ScheduleList);
      //   console.log("실행");
        // console.log("데이터 받고 여기" + this.arr);
        // this.arr[0] = "Z";
        // console.log("데이터 받고 저기" + this.arr);
        // var p = document.getElementById("ptag");
        // p.innerText = (event.data);
        // var area = document.getElementById("area");
        // area.innerText = (event.data);
      // })
    },
    sendMessage() {
      console.log("sendMessage: ");
    },
    updateArray () {
      this.arr[0] = "Z";
      this.arr[1] = "X";
      this.sockjs.send(this.arr);
    },
    getAllSchedule() {
      axios({
        method: 'get',
        url: 'http://127.0.0.1:8081/redisschedule/all'
      })
        .then(res => {
          console.log(res.data.redisScheduleList);
          this.scheduleList = res.data.redisScheduleList;
          console.log(this.scheduleList);
        })
        .catch(err => {
          console.log(err);
        })
    },
    sendChange: function() {
      // const schedule = {
      //   scheduleId: 1,
      // }
      // 수정 후 websocket으로 알리기
      axios({
        method: 'delete',
        url: 'http://127.0.0.1:8081/redisschedule/delete',
        // headers:{
        //   'Content-Type': 'application/json',
        //   'Accept' : 'application/json',
        //   'Access-Control-Allow-Origin' : '*',
        //   'Access-Control-Allow-Methods':'GET,PUT,POST,DELETE,PATCH,OPTIONS',
        // },
        data: {
          scheduleId: 1,
        },
      })
        .then(res => {
          console.log(res);
        })
        .catch(err => {
          console.log(err);
        })
      // this.sockjs.send("값 변경");
    }

  }
}
</script>

<style>

</style>