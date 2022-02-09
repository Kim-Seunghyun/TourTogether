<template>
  <div class="nav-wrapper position-relative end-0">
    <!-- 사람유형 -->
    <ul class="nav nav-pills nav-fill p-1" role="tablist">
      <li class="nav-item">
        <a
          class="nav-link mb-0 px-0 py-1 active active"
          data-bs-toggle="tab"
          role="tab"
          aria-selected="true"
          >#남자끼리</a
        >
      </li>
      <li class="nav-item">
        <a
          class="nav-link mb-0 px-0 py-1"
          data-bs-toggle="tab"
          role="tab"
          aria-selected="false"
          >#여자끼리</a
        >
      </li>
      <li class="nav-item">
        <a
          class="nav-link mb-0 px-0 py-1"
          data-bs-toggle="tab"
          role="tab"
          aria-selected="false"
          >#연인끼리</a
        >
      </li>
      <li class="nav-item">
        <a
          class="nav-link mb-0 px-0 py-1"
          data-bs-toggle="tab"
          role="tab"
          aria-selected="false"
          >#부모님과</a
        >
      </li>
      <li class="nav-item">
        <a
          class="nav-link mb-0 px-0 py-1"
          data-bs-toggle="tab"
          role="tab"
          aria-selected="false"
          >#아이들과</a
        >
      </li>
      <li class="nav-item">
        <a
          class="nav-link mb-0 px-0 py-1"
          data-bs-toggle="tab"
          role="tab"
          aria-selected="false"
          >#혼자</a
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
