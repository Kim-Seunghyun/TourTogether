<template>
  <div id="main-container" class="container">
    <button v-if="!session" class="btn btn-success" @click="joinSession()">
      Join!
    </button>

    <div v-if="session" id="session">
      <input
        class="btn btn-large btn-danger"
        type="button"
        id="buttonLeaveSession"
        @click="leaveSession"
        value="Leave session"
      />

      <div id="video_wrapper">
        <user-video :stream-manager="mainStreamManager" class="box" />
        <user-video
          v-for="sub in subscribers"
          :key="sub.stream.connection.connectionId"
          :stream-manager="sub"
          @click="updateMainVideoStreamManager(sub)"
          class="box"
        >
          {{ sub.stream.connection.connectionId }}
        </user-video>
      </div>
      <!-- </div> -->
      <button @click="toggleVideo()">비디오버튼</button>
      <button @click="toggleAudio()">음소거</button>
      <input
        @keyup.enter="submitChatting()"
        placeholder="여기에 메시지 입력"
        v-model="message"
      />
      <div id="chatting-wrapper">
        <ul id="chatting"></ul>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { OpenVidu } from "openvidu-browser";
import UserVideo from "@/components/UserVideo";
import store from "@/store";
const getters = store.getters;

axios.defaults.headers.post["Content-Type"] = "application/json";

// const OPENVIDU_SERVER_URL = "https://" + location.hostname + ":4443";
// 서버 배포 시
const OPENVIDU_SERVER_URL = "https://i6a105.p.ssafy.io:443";
const OPENVIDU_SERVER_SECRET = "twist";

export default {
  name: "webRTC",

  components: {
    UserVideo,
  },

  data() {
    return {
      OV: undefined,
      session: undefined,
      mainStreamManager: undefined,
      publisher: undefined,
      subscribers: [],
      message: undefined,
      mySessionId: window.location.pathname.split("/")[1],
      myUserName: getters["userStore/getUserNickname"],
    };
  },

  methods: {
    joinSession() {
      // --- Get an OpenVidu object ---
      this.OV = new OpenVidu();

      // --- Init a session ---
      this.session = this.OV.initSession();

      // --- Specify the actions when events take place in the session ---

      // On every new Stream received...
      this.session.on("streamCreated", ({ stream }) => {
        const subscriber = this.session.subscribe(stream);
        this.subscribers.push(subscriber);
      });

      // On every Stream destroyed...
      this.session.on("streamDestroyed", ({ stream }) => {
        const index = this.subscribers.indexOf(stream.streamManager, 0);
        if (index >= 0) {
          this.subscribers.splice(index, 1);
        }
      });

      // On every asynchronous exception...
      this.session.on("exception", ({ exception }) => {
        console.warn(exception);
      });

      // --- Connect to the session with a valid user token ---

      // 'getToken' method is simulating what your server-side should do.
      // 'token' parameter should be retrieved and returned by your own backend
      this.getToken(this.mySessionId).then((token) => {
        this.session
          .connect(token, { clientData: this.myUserName })
          .then(() => {
            // --- Get your own camera stream with the desired properties ---

            let publisher = this.OV.initPublisher(undefined, {
              audioSource: undefined, // The source of audio. If undefined default microphone
              videoSource: undefined, // The source of video. If undefined default webcam
              publishAudio: false, // Whether you want to start publishing with your audio unmuted or not
              publishVideo: false, // Whether you want to start publishing with your video enabled or not
              resolution: "320x320", // The resolution of your video
              frameRate: 30, // The frame rate of your video
              insertMode: "APPEND", // How the video is inserted in the target element 'video-container'
              mirror: false, // Whether to mirror your local video or not
            });

            this.mainStreamManager = publisher;
            this.publisher = publisher;

            // --- Publish your stream ---

            this.session.publish(this.publisher);
          })
          .catch((error) => {
            console.log(
              "There was an error connecting to the session:",
              error.code,
              error.message
            );
          });
      });

      window.addEventListener("beforeunload", this.leaveSession);
      this.session.on("signal", (event) => {
        // console.log(event.data); // Message/
        // console.log(event.from); // Connection object of the sender
        // console.log(event.type); // The type of message

        // 여기에 div 찾아서 내용 넣고 올리기
        const el = document.createElement("li");
        const dest = document.getElementById("chatting");
        const wrapper = document.getElementById("chatting-wrapper");
        el.innerText =
          event.from.data.split(":")[1].split('"')[1] + "\t" + event.data;
        dest.append(el);
        wrapper.scrollTop = wrapper.scrollHeight;
      });
    },

    leaveSession() {
      // --- Leave the session by calling 'disconnect' method over the Session object ---
      if (this.session) this.session.disconnect();

      this.session = undefined;
      this.mainStreamManager = undefined;
      this.publisher = undefined;
      this.subscribers = [];
      this.OV = undefined;

      window.removeEventListener("beforeunload", this.leaveSession);
    },

    updateMainVideoStreamManager(stream) {
      if (this.mainStreamManager === stream) return;
      this.mainStreamManager = stream;
    },

    /**
     * --------------------------
     * SERVER-SIDE RESPONSIBILITY
     * --------------------------
     * These methods retrieve the mandatory user token from OpenVidu Server.
     * This behavior MUST BE IN YOUR SERVER-SIDE IN PRODUCTION (by using
     * the API REST, openvidu-java-client or openvidu-node-client):
     *   1) Initialize a Session in OpenVidu Server	(POST /openvidu/api/sessions)
     *   2) Create a Connection in OpenVidu Server (POST /openvidu/api/sessions/<SESSION_ID>/connection)
     *   3) The Connection.token must be consumed in Session.connect() method
     */

    getToken(mySessionId) {
      return this.createSession(mySessionId).then((sessionId) =>
        this.createToken(sessionId)
      );
    },

    // See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-openviduapisessions
    createSession(sessionId) {
      return new Promise((resolve, reject) => {
        axios
          .post(
            `${OPENVIDU_SERVER_URL}/openvidu/api/sessions`,
            JSON.stringify({
              customSessionId: sessionId,
            }),
            {
              auth: {
                username: "OPENVIDUAPP",
                password: OPENVIDU_SERVER_SECRET,
              },
            }
          )
          .then((response) => response.data)
          .then((data) => resolve(data.id))
          .catch((error) => {
            console.log("errorrororororo", error);
            if (error.response.status === 409) {
              resolve(sessionId);
            } else {
              console.warn(
                `No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}`
              );
              if (
                window.confirm(
                  `No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}\n\nClick OK to navigate and accept it. If no certificate warning is shown, then check that your OpenVidu Server is up and running at "${OPENVIDU_SERVER_URL}"`
                )
              ) {
                location.assign(`${OPENVIDU_SERVER_URL}/accept-certificate`);
              }
              reject(error.response);
            }
          });
      });
    },

    // See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-openviduapisessionsltsession_idgtconnection
    createToken(sessionId) {
      return new Promise((resolve, reject) => {
        axios
          .post(
            `${OPENVIDU_SERVER_URL}/openvidu/api/sessions/${sessionId}/connection`,
            {},
            {
              auth: {
                username: "OPENVIDUAPP",
                password: OPENVIDU_SERVER_SECRET,
              },
            }
          )
          .then((response) => response.data)
          .then((data) => resolve(data.token))
          .catch((error) => reject(error.response));
      });
    },

    toggleVideo() {
      if (this.publishVideo) {
        this.publishVideo = false;
        this.publisher.publishVideo(this.publishVideo);
      } else if (!this.publishVideo) {
        this.publishVideo = true;
        this.publisher.publishVideo(this.publishVideo);
      }
    },
    toggleAudio() {
      if (this.publishAudio) {
        this.publishAudio = false;
        this.publisher.publishAudio(this.publishAudio);
      } else if (!this.publishAudio) {
        this.publishAudio = true;
        this.publisher.publishAudio(this.publishAudio);
      }
    },
    submitChatting() {
      const content = this.message;
      if (this.message == "") return;
      this.message = undefined;
      this.session
        .signal({
          data: content, // Any string (optional)
          to: [], // Array of Connection objects (optional. Broadcast to everyone if empty)
        })
        .then(() => {
          // console.log("Message successfully sent");
        })
        .catch((error) => {
          console.error(error);
        });
    },
  },
};
</script>
<style>
.box {
  width: 320px;
  height: 320px;
  border-radius: 70%;
  overflow: hidden;
  float: left;
}
#chatting-wrapper {
  width: 400px;
  height: 300px;
  overflow-y: auto;
  border: solid 1px black;
  top: 130%;
  right: 10%;
  position: absolute;
  z-index: 3;
}
#chatting {
  list-style: none;
}
</style>
