<template>
  <div class="card">
    <div class="p-3 mx-4 text-center card-header">
      <!-- <i class="opacity-10" :class="classIcon" aria-hidden="true"></i> -->
      <h1>🗾</h1>
    </div>
    <div class="p-3 pt-0 text-center card-body">
      <h6 class="mb-0 text-center">{{ title }}</h6>
      <span class="text-xs">{{ desc }}</span>
      <hr class="my-3 horizontal dark" />
      <h7 class="mb-0"> </h7>
    </div>
  </div>
</template>

<script>
export default {
  name: "default-info-card",
  props: {
    icon_bg: {
      type: String,
      default: "bg-white",
    },
    classIcon: {
      type: String,
      required: true,
    },
    title: String,
    desc: String,
    price: String,
  },
};
</script>
