// module.exports = {
//   // 개발 서버 설정
//   devServer: {
//     // 프록시 설정
//     proxy: {
//       // 프록시 요청을 보낼 api의 시작 부분
//       "/openvidu/api/sessions": {
//         // 프록시 요청을 보낼 서버의 주소
//         target: "https://i6a105.p.ssafy.io:443/openvidu/api/sessions",
//         changeOrigin: true,
//       },
//       "/tourspot": {
//         target: "https://i6a105.p.ssafy.io:8080/api/tourspot",
//         changeOrigin: true,
//       },
//     },
//   },
// };
module.exports = {
  devServer: {
    proxy: {
      "/api": {
        // '/api' 로 들어오면 포트 8080(스프링 서버)로 보낸다
        target: "https://i6a105.p.ssafy.io:8080",
        changeOrigin: true, // cross origin 허용
      },
      // "/user/login": {
      //   target: "https://i6a105.p.ssafy.io:8090",
      //   changeOrigin: true,
      // },
      // "/user/updateNickname/": {
      //   target: "https://i6a105.p.ssafy.io:8090",
      //   changeOrigin: true,
      // },
      // "/api/user/delete": {
      //   target: "https://i6a105.p.ssafy.io:8090",
      //   changeOrigin: true,
      // },
    },
  },
};
