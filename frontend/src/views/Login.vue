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
  setup() {
    const login = () => {
      window.Kakao.Auth.authorize({
        redirectUri: "http://localhost:8080/kakao-login-callback/",
      });
    }

    return { login }
  }
};
</script>
