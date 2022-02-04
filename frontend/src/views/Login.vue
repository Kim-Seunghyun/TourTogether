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
        redirectUri: "https://i6a105.p.ssafy.io/kakao-login-callback/",
      });
    },
  },
};
</script>
