<template>
  <div id="map">
    <Map />
  </div>
</template>

<script>
import Map from "./map.vue";

export default {
  name: "App",
  components: {
    Map,
  },
};
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
#map {
  width: 100%;
  /* height: 60%; */
  height: 1000px;
}
</style>
