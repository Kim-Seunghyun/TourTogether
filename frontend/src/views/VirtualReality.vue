<template>
  <div ref="pdfarea">
    //pdf 내용 ...

    <b-img :src="이미지url"></b-img>
    ...

    <b-button class="mb-5 float-right" @click="exportToPDF"
      >PDF 다운로드</b-button
    >
  </div>
</template>

<script>
import html2pdf from "html2pdf.js";

export default {
  name: "submission-detail",
  methods: {
    exportToPDF() {
      //window.scrollTo(0, 0);
      html2pdf(this.$refs.pdfarea, {
        margin: 0,
        filename: "document.pdf",
        image: { type: "jpg", quality: 0.95 },
        //	allowTaint 옵션추가
        html2canvas: {
          useCORS: true,
          scrollY: 0,
          scale: 1,
          dpi: 300,
          letterRendering: true,
          allowTaint: false, //useCORS를 true로 설정 시 반드시 allowTaint를 false처리 해주어야함
        },
        jsPDF: {
          orientation: "portrait",
          unit: "mm",
          format: "a4",
          compressPDF: true,
        },
      });
    },

    mounted() {},
  },
};
</script>
