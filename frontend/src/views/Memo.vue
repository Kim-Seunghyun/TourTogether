<template>
  <quill-editor ref="myQuillEditor" v-model="content" :options="editorOption" />
</template>

<script>
import "quill/dist/quill.core.css";
import "quill/dist/quill.snow.css";
import "quill/dist/quill.bubble.css";

import { quillEditor } from "vue3-quill";
// import customQuillModule from "customQuillModule";
// Quill.register("modules/customQuillModule", customQuillModule);

export default {
  components: {
    quillEditor,
  },
  data() {
    return {
      content: "<h2>I am Example</h2>",
      editorOption: {
        // Some Quill options...
      },
    };
  },
};
</script>
