<template>
  <h1>일정</h1>
  <div id="memoConnect">
    <button @click="connectSocket()">일정 시작하기</button>
  </div>

  <div class="row">
    <div class="col-2">
      <div class="form-group">
        <div
          class="btn-group-vertical buttons"
          role="group"
          aria-label="Basic example"
        >
          <button class="btn btn-secondary" @click="add">Add</button>
        </div>
      </div>
    </div>

    <div class="col-6">
      <draggable
        :list="list"
        :disabled="!enabled"
        item-key="name"
        class="list-group"
        ghost-class="ghost"
        :move="checkMove"
        @start="dragging = true"
        @end="dragging = false"
      >
        <template #item="{ element }">
          <div class="list-group-item" :class="{ 'not-draggable': !enabled }">
            {{ element.name }}
          </div>
        </template>
      </draggable>
    </div>

    <rawDisplayer class="col-3" :value="list" title="List" />
  </div>
</template>

<script>
import draggable from "vuedraggable";
import SockJS from "sockjs-client";

let id = 1;

export default {
  name: "Schedule",
  display: "Simple",
  order: 0,
  components: {
    draggable,
  },
  data() {
    return {
      enabled: true,
      list: [
        { name: "1번", id: 0 },
        { name: "2번", id: 1 },
        { name: "3번", id: 2 },
      ],
      dragging: false,
    };
  },
  computed: {},
  methods: {
    connectSocket() {
      this.sockjs = new SockJS("http://localhost:8080/api/schedule");
      console.log(this.sockjs);
      this.sockjs.onopen = function () {
        console.log("연결");
        alert("연결 성공");
        document.getElementById("memoConnect").setAttribute("hidden", "hidden");
      };
      this.sockjs.onmessage = function (data) {
        console.log("data받음" + data);
        console.log(data);
      };
      this.sockjs.onerror = function (e) {
        alert("연결에 실패하였습니다.");
        console.log(e);
      };
    },
    add: function () {
      this.list.push({ name: "Juan " + id, id: id++ });
    },
    checkMove: function (e) {
      window.console.log("Future index: " + e.draggedContext.futureIndex);
      this.sockjs.send(JSON.stringify(e.relatedContext.list));
      console.log(JSON.stringify(e.relatedContext.list)); // string
    },
    sendMessage() {
      console.log("실행");
      console.log("sendMessage: " + this.list);
    },
  },
};
</script>
<style scoped>
.buttons {
  margin-top: 35px;
}
.ghost {
  opacity: 0.5;
  background: #c8ebfb;
}
.not-draggable {
  cursor: no-drop;
}
</style>
