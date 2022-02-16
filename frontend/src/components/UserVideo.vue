<template>
  <div v-if="streamManager">
    <ov-video :stream-manager="streamManager" id="shape" />
    <div>
      <p>{{ clientData }}</p>
    </div>
  </div>
</template>

<script>
import OvVideo from "./OvVideo";

export default {
  name: "UserVideo",

  components: {
    OvVideo,
  },

  props: {
    streamManager: Object,
    isSpeaking: Boolean,
    particiapntId: String,
  },

  computed: {
    clientData() {
      const { clientData } = this.getConnectionData();
      console.log(clientData);
      return clientData;
    },
  },
  watch: {
    isSpeaking: function () {
      if (this.isSpeaking) {
        document.getElementById("shape").className = "highlighting";
      } else {
        document.getElementById("shape").className = "deHighlighting";
      }
    },
  },

  methods: {
    getConnectionData() {
      const { connection } = this.streamManager.stream;
      return JSON.parse(connection.data);
    },
  },
};
</script>

<style>
.highlighting {
  border: 4px solid rgb(88, 236, 59);
}
.deHighlighting {
  border: 0px solid;
}

#shape {
  width: 120px;
  height: 120px;
}
</style>
