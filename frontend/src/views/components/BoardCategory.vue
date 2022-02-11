<template>
  <div class="nav-wrapper position-relative end-0">
    <!-- 사람유형 -->
    <ul
      class="nav nav-pills nav-fill p-1"
      role="tablist"
      data-id="hi"
      @click="selectWithWhom()"
    >
      <li class="nav-item">
        <a
          class="nav-link mb-0 px-0 py-1 active"
          data-bs-toggle="tab"
          role="tab"
          aria-selected="true"
        >
          #상관없음
        </a>
      </li>
      <li class="nav-item">
        <a
          class="nav-link mb-0 px-0 py-1 active"
          data-bs-toggle="tab"
          role="tab"
          aria-selected="false"
          id="1"
        >
          #남자끼리
        </a>
      </li>
      <li class="nav-item">
        <a
          class="nav-link mb-0 px-0 py-1"
          data-bs-toggle="tab"
          role="tab"
          aria-selected="false"
          id="2"
        >
          #여자끼리
        </a>
      </li>
      <li class="nav-item">
        <a
          class="nav-link mb-0 px-0 py-1"
          data-bs-toggle="tab"
          role="tab"
          aria-selected="false"
          id="3"
        >
          #연인끼리
        </a>
      </li>
      <li class="nav-item">
        <a
          class="nav-link mb-0 px-0 py-1"
          data-bs-toggle="tab"
          role="tab"
          aria-selected="false"
          id="4"
        >
          #부모님과
        </a>
      </li>
      <li class="nav-item">
        <a
          class="nav-link mb-0 px-0 py-1"
          data-bs-toggle="tab"
          role="tab"
          aria-selected="false"
          id="5"
        >
          #아이들과
        </a>
      </li>
      <li class="nav-item">
        <a
          class="nav-link mb-0 px-0 py-1"
          data-bs-toggle="tab"
          role="tab"
          aria-selected="false"
          id="6"
        >
          #혼자
        </a>
      </li>
      <li class="nav-item">
        <a
          class="nav-link mb-0 px-0 py-1"
          data-bs-toggle="tab"
          role="tab"
          aria-selected="false"
          id="7"
        >
          #그 외
        </a>
      </li>
    </ul>
  </div>
  <div class="nav-wrapper position-relative end-0">
    <!-- 계절 유형 -->
    <ul class="nav nav-pills nav-fill p-1" role="tablist">
      <li class="nav-item">
        <a
          class="nav-link mb-0 px-0 py-1 active"
          data-bs-toggle="tab"
          role="tab"
          aria-selected="true"
        >
          #상관없음
        </a>
      </li>
      <li class="nav-item">
        <a
          class="nav-link mb-0 px-0 py-1"
          data-bs-toggle="tab"
          role="tab"
          aria-selected="false"
          >#봄</a
        >
      </li>
      <li class="nav-item">
        <a
          class="nav-link mb-0 px-0 py-1"
          data-bs-toggle="tab"
          role="tab"
          aria-selected="false"
          >#여름</a
        >
      </li>
      <li class="nav-item">
        <a
          class="nav-link mb-0 px-0 py-1"
          data-bs-toggle="tab"
          role="tab"
          aria-selected="false"
          >#가을</a
        >
      </li>
      <li class="nav-item">
        <a
          class="nav-link mb-0 px-0 py-1"
          data-bs-toggle="tab"
          role="tab"
          aria-selected="false"
          >#겨울</a
        >
      </li>
    </ul>
  </div>
  <div class="nav-wrapper position-relative end-0">
    <!-- 지역유형 -->
    <ul class="nav nav-pills nav-fill p-1" role="tablist">
      <li class="nav-item">
        <a
          class="nav-link mb-0 px-0 py-1 active"
          data-bs-toggle="tab"
          role="tab"
          aria-selected="true"
        >
          #상관없음
        </a>
      </li>
      <li class="nav-item">
        <a
          class="nav-link mb-0 px-0 py-1"
          data-bs-toggle="tab"
          role="tab"
          aria-selected="false"
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
  <div class="nav-wrapper position-relative end-0">
    <!-- 여행 유형 -->
    <ul class="nav nav-pills nav-fill p-1" role="tablist">
      <li class="nav-item">
        <a
          class="nav-link mb-0 px-0 py-1 active"
          data-bs-toggle="tab"
          role="tab"
          aria-selected="true"
        >
          #상관없음
        </a>
      </li>
      <li class="nav-item">
        <a
          class="nav-link mb-0 px-0 py-1"
          data-bs-toggle="tab"
          role="tab"
          aria-selected="false"
          >#바다,강</a
        >
      </li>
      <li class="nav-item">
        <a
          class="nav-link mb-0 px-0 py-1"
          data-bs-toggle="tab"
          role="tab"
          aria-selected="false"
          >#산</a
        >
      </li>
      <li class="nav-item">
        <a
          class="nav-link mb-0 px-0 py-1"
          data-bs-toggle="tab"
          role="tab"
          aria-selected="false"
          >#액티비티</a
        >
      </li>
      <li class="nav-item">
        <a
          class="nav-link mb-0 px-0 py-1"
          data-bs-toggle="tab"
          role="tab"
          aria-selected="false"
          >#호캉스</a
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
  name: "BoardCategory",
  data() {
    return {
      withWhom: "0",
      season: "0",
      area: "0",
      theme: "0",
    };
  },
  mounted() {
    setNavPills();
  },
  methods: {
    // selectWithWhom(event) {
    //   console.log(event.target.getAttribute("data-id"));
    // },
  },
};
</script>
