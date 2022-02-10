<template>
  <quill-editor
    v-model:value="state.content"
    :options="state.editorOption"
    :disabled="state.disabled"
    @blur="onEditorBlur($event)"
    @focus="onEditorFocus($event)"
    @ready="onEditorReady($event)"
    @change="onEditorChange($event)"
  />
</template>

<script>
// import "quill/dist/quill.core.css";
// import "quill/dist/quill.snow.css";
// import "quill/dist/quill.bubble.css";

import { reactive } from "vue";
import { quillEditor } from "vue3-quill";
// import customQuillModule from "customQuillModule";
// Quill.register("modules/customQuillModule", customQuillModule);

export default {
  components: {
    quillEditor,
  },
  setup() {
    const state = reactive({
      dynamicComponent: null,
      content: "<p>2333</p>",
      _content: "",
      editorOption: {
        placeholder: "core",
        modules: {
          toolbar: [
            // custom toolbars options
            // will override the default configuration
          ],
          // other moudle options here
        },
        // more options
      },
      disabled: false,
    });

    const onEditorBlur = (quill) => {
      console.log("editor blur!", quill);
    };
    const onEditorFocus = (quill) => {
      console.log("editor focus!", quill);
    };
    const onEditorReady = (quill) => {
      console.log("editor ready!", quill);
    };
    const onEditorChange = ({ quill, html, text }) => {
      console.log("editor change!", quill, html, text);
      state._content = html;
    };

    setTimeout(() => {
      state.disabled = true;
    }, 2000);

    return {
      state,
      onEditorBlur,
      onEditorFocus,
      onEditorReady,
      onEditorChange,
    };
  },
};
</script>
