<template>
  <div class="about">
    <h1>Callback</h1>
  </div>
</template>

<script>
// import axios from "axios"
import router from "@/router";

export default {
  methods: {
    getParameterByName: function (name) {
      name = name.replace(/[[]/, "\\[").replace(/[\]]/, "\\]");
      var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
        results = regex.exec(location.search);
      return results == null
        ? ""
        : decodeURIComponent(results[1].replace(/\+/g, " "));
    },
  },
  beforeMount() {
    const code = this.getParameterByName("code");
    // alert("this is code:"+code);
    var details = {
      grant_type: "authorization_code",
      client_id: process.env.VUE_APP_KAKAO_RESTAPI_KEY,
      redirect_uri: "https://i6a105.p.ssafy.io/kakao-login-callback/",
      // redirect_uri: "http://localhost:8080/kakao-login-callback/",
      code: code,
    };
    var formBody = [];
    for (var property in details) {
      var encodedKey = encodeURIComponent(property);
      var encodedValue = encodeURIComponent(details[property]);
      formBody.push(encodedKey + "=" + encodedValue);
    }
    formBody = formBody.join("&");
    fetch("https://kauth.kakao.com/oauth/token", {
      method: "POST",
      headers: {
        "Content-Type": "application/x-www-form-urlencoded;charset=utf-8",
      },
      body: formBody,
    })
      .then((response) => response.json())
      .then((data) => {
        console.log(JSON.stringify(data));
        alert(JSON.stringify(data));
        window.Kakao.Auth.setAccessToken(data.access_token);
        // test : Token
        alert(window.Kakao.Auth.getAccessToken());
        router.push("/dashboard");
        // axios({
        //   method: 'post',
        //   url: 'http://localhost:3000/user/login',
        //   data:
        // })
      });
  },
};
</script>
