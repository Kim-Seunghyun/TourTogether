<template>
  <div>
    <img
      alt="Kakao logo"
      src="../assets/kakao_login_large_wide.png"
      v-on:click="login()"
    />
  </div>
</template>

<script>
export default {
  name: "Login",
  methods: {
    login: function () {
      window.Kakao.Auth.authorize({
        redirectUri: "https://localhost:8080/kakao-login-callback/",
      });
    },
  },
};
</script>
