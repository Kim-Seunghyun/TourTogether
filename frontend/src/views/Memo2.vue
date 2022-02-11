<template>
  <quill-editor
    v-model:value="state.content"
    :options="state.editorOption"
    @blur="onEditorBlur($event)"
    @focus="onEditorFocus($event)"
    @ready="onEditorReady($event)"
    @change="onEditorChange($event)"
  />
  <!-- <p>{{ state._content }}</p> -->
</template>

<script>
import { reactive } from "vue";
import { quillEditor } from "vue3-quill";

import SockJS from "sockjs-client";
import Stomp from "stomp-websocket";

import axios from "axios";

let sock = new SockJS("https://i6a105.p.ssafy.io:8081/ws-stomp");

export default {
  components: {
    quillEditor,
  },
  setup() {
    const state = reactive({
      ws: Stomp.over(sock),
      quill: null,
      my: true,
      user: "임의의 사용자1",
      id: "abc-1234",

      // dynamicComponent: null,
      content: "",
      editorOption: {
        placeholder: "core",
        modules: {
          toolbar: [
            // custom toolbars options
            // will override the default configuration
            ["bold", "italic", "underline", "strike"],
            ["blockquote", "code-block"],
            [{ header: 1 }, { header: 2 }],
            [{ list: "ordered" }, { list: "bullet" }],
            [{ script: "sub" }, { script: "super" }],
            [{ indent: "-1" }, { indent: "+1" }],
            [{ direction: "rtl" }],
            [{ size: ["small", false, "large", "huge"] }],
            [{ header: [1, 2, 3, 4, 5, 6, false] }],
            [{ color: [] }, { background: [] }],
            [{ font: [] }],
            [{ align: [] }],
            ["clean"],
            ["link", "image", "video"],
          ],
          // other moudle options here
        },
        // more options
      },
    });

    const onEditorBlur = () => {
      // console.log("editor blur!");
    };
    const onEditorFocus = () => {
      // console.log("editor focus!");
    };
    const onEditorReady = () => {
      // console.log("editor ready!");
      // if (state.quill === null) {
      // console.log("quill 초기화");
      // state.quill = quill;
      // }
    };
    const onEditorChange = ({ quill }) => {
      state.quill = quill;
      // sendText(html);
      quill.once("text-change", function (...args) {
        if (state.my) sendMessage(args[0], state.content);
        // console.log(args[2]); // user
      });
    };

    const sendMessage = function (delta, content) {
      console.log("sendMessage", delta);
      state.ws.send(
        "/pub/memo",
        {},
        JSON.stringify({
          roomId: state.id,
          user: state.user,
          delta: delta,
          content: content,
        })
      );
    };

    return {
      state,
      onEditorBlur,
      onEditorFocus,
      onEditorReady,
      onEditorChange,
    };
  },
  created() {
    this.init();
    this.initRecv();
  },
  methods: {
    recvMessage(message) {
      // let _this = this;
      console.log("recvMessage", message);
      if (message.user !== this.state.user) {
        this.state.my = false;
        this.state.quill.updateContents(message.delta);
        // this.state.content = message.content;
        this.state.my = true;
      }
    },
    initRecv() {
      // 접속시 처음 값을 받아오도록 하기
      // 테스트 페이지인 경우와 아닌 경우로 분기
      console.log("init RECV start");
      (response) => {
        console.log("initRecv@@@@");
        console.log(response.data);
      },
        (err) => {
          console.log("initRecv 실패");
          console.log(err);
        };
    },
    init() {
      axios({
        method: "POST",
        url: "http://localhost:8081/memo/room",
        params: {
          id: this.state.id,
          user: this.state.user,
        },
        // data: {
        // id: "1",
        // },
      })
        .then((response) => {
          console.log("@@@@return: ", response);
          this.state.my = false;
          this.state.content = response.data.memo.memoContent;
          this.state.my = true;
        })
        .catch((error) => {
          console.log(error);
        });
      var sock = new SockJS("https://i6a105.p.ssafy.io:8081/ws-stomp");
      var ws = Stomp.over(sock);
      var _this = this;
      this.ws = ws;
      var subUrl = "/sub/memo/" + _this.state.id;
      console.log("채널 구독하기: ", subUrl);
      ws.connect(
        {
          userNickname: _this.state.user, // 이거 없으면 오류나는데, stomp API 내부적으로 쓰는거같음
        },
        function (frame) {
          console.log("ws.connect: ", frame);
          ws.subscribe(subUrl, function (message) {
            var recv = JSON.parse(message.body);
            _this.recvMessage(recv);
          });
        },
        function (error) {
          console.log("ws.connect error: ", error);
          alert("서버 연결에 실패 하였습니다. 다시 접속해 주십시요.");
        }
      );
    },
  },
};
</script>
