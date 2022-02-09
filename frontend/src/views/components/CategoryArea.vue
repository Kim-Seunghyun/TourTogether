<template>
  <div class="nav-wrapper position-relative end-0">
    <!-- 지역유형 -->
    <ul class="nav nav-pills nav-fill p-1" role="tablist">
      <li class="nav-item">
        <a
          class="nav-link mb-0 px-0 py-1 active active"
          data-bs-toggle="tab"
          role="tab"
          aria-selected="true"
          >#서울</a
        >
      </li>
      <li class="nav-item">
        <a
          class="nav-link mb-0 px-0 py-1"
          data-bs-toggle="tab"
          role="tab"
          aria-selected="false"
          >#부산</a
        >
      </li>
      <li class="nav-item">
        <a
          class="nav-link mb-0 px-0 py-1"
          data-bs-toggle="tab"
          role="tab"
          aria-selected="false"
          >#여수</a
        >
      </li>
      <li class="nav-item">
        <a
          class="nav-link mb-0 px-0 py-1"
          data-bs-toggle="tab"
          role="tab"
          aria-selected="false"
          >#대구</a
        >
      </li>
      <li class="nav-item">
        <a
          class="nav-link mb-0 px-0 py-1"
          data-bs-toggle="tab"
          role="tab"
          aria-selected="false"
          >#제주도</a
        >
      </li>
      <li class="nav-item">
        <a
          class="nav-link mb-0 px-0 py-1"
          data-bs-toggle="tab"
          role="tab"
          aria-selected="false"
          >#그 외</a
        >
      </li>
    </ul>
  </div>
</template>

<script>
import setNavPills from "@/assets/js/nav-pills.js";

export default {
  name: "nav-pill",
  mounted() {
    setNavPills();
  },
};
</script>
