<template>
  <div id="whole_wrapper">
    <div id="map_wrapper">
      <Map />
    </div>
    <div id="memo_wrapper">
      <Memo />
    </div>
    <div id="webrtc_wrapper">
      <webRTC />
    </div>
  </div>
</template>

<script>
import Map from "@/views/Map.vue";
import Memo from "@/views/Memo.vue";
import webRTC from "@/views/WebRTC.vue";

export default {
  components: {
    Map,
    Memo,
    webRTC,
  },
};
</script>

<style>
#whole_wrapper {
  width: 100%;
  height: 100%;
}
#map_wrapper {
  width: 95%;
  height: 60%;
  left: 2%;
  position: relative;
}
#memo_wrapper {
  width: 20%;
  height: 30%;
  overflow: scroll;
  position: absolute;
  top: 75%;
  left: 77%;
  border: solid 1px #888;
  z-index: 3;
  background-color: white;
}
#webrtc_wrapper {
}
</style>
