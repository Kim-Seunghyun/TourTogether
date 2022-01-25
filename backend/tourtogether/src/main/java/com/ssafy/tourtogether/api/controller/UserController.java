package com.ssafy.tourtogether.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.tourtogether.api.request.UserDeleteDeleteReq;
import com.ssafy.tourtogether.api.request.UserLoginPostReq;
import com.ssafy.tourtogether.api.request.UserUpdateImagePatchReq;
import com.ssafy.tourtogether.api.request.UserUpdateNicknamePatchReq;
import com.ssafy.tourtogether.api.response.UserDeleteDeleteRes;
import com.ssafy.tourtogether.api.response.UserLoginPostRes;
import com.ssafy.tourtogether.api.response.UserUpdateImagePatchRes;
import com.ssafy.tourtogether.api.response.UserUpdateNicknamePatchRes;
import com.ssafy.tourtogether.api.service.UserService;
//import com.ssafy.tourtogether.common.util.JwtTokenUtil;
import com.ssafy.tourtogether.db.entity.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

/**
 * 유저 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "유저 API", tags = { "User" })
@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	UserService userService;

	@PostMapping("/login")
	@ApiOperation(value = "로그인", notes = "소셜로그인 API를 통해 로그인 한다.")
	@ApiResponses({
			// TODO 필요한 response 종류와 메시지 작성
//        @ApiResponse(code = 200, message = "성공", response = UserLoginPostRes.class),
//        @ApiResponse(code = 401, message = "인증 실패", response = BaseResponseBody.class),
//        @ApiResponse(code = 404, message = "사용자 없음", response = BaseResponseBody.class),
//        @ApiResponse(code = 500, message = "서버 오류", response = BaseResponseBody.class)
	})
	public ResponseEntity<UserLoginPostRes> login(
			@RequestBody @ApiParam(value = "로그인 정보", required = true) UserLoginPostReq loginInfo) {
		String userId = /* loginInfo.getId() */ ""; // TODO api 전달 방식에 따라 수정
//		String password = loginInfo.getPassword();

		User user = userService.getUserByUserId(userId);
		// TODO login res 메시지에 따라 수정: 유저가 존재하면 로그인, 존재하지 않으면 회원가입처리 해야할듯
//		return ResponseEntity.ok(UserLoginPostRes.of(200, "Success", JwtTokenUtil.getToken(userId)));
//		return ResponseEntity.status(401).body(UserLoginPostRes.of(401, "Invalid Password", null));
		return null;
	}

	@DeleteMapping("/delete")
	@ApiOperation(value = "회원탈퇴", notes = "소셜로그인 API를 통해 가입한 계정을 탈퇴한다.")
	@ApiResponses({
			// TODO 필요한 response 종류와 메시지 작성
	})
	public ResponseEntity<UserDeleteDeleteRes> delete(
			@RequestBody @ApiParam(value = "회원탈퇴 정보", required = true) UserDeleteDeleteReq deleteInfo) {
		String userId = /* deleteInfo.getId() */ ""; // TODO api 전달 방식에 따라 수정

		User user = userService.getUserByUserId(userId);
		// TODO delete res 메시지에 따라 수정
//		return ResponseEntity.ok(UserDeleteDeleteRes.of(200, "Success", JwtTokenUtil.getToken(userId)));
//		return ResponseEntity.status(401).body(UserLoginPostRes.of(401, "Invalid Password", null));
		return null;
	}

	@PatchMapping("/updateImage/{userId}")
	@ApiOperation(value = "프로필 이미지 변경", notes = "유저의 프로필 이미지를 변경한다.")
	@ApiResponses({
			// TODO 필요한 response 종류와 메시지 작성
	})
	public ResponseEntity<UserUpdateImagePatchRes> updateImage(
			@RequestBody @ApiParam(value = "프로필 변경 정보", required = true) UserUpdateImagePatchReq updateImageInfo) {
		String userId = /* loginInfo.getId() */ ""; // TODO api 전달 방식에 따라 수정

		User user = userService.getUserByUserId(userId);
		// TODO updateImage res 메시지에 따라 수정
		// TODO delete 하고 새 이미지 저장후 경로 patch 해야될것같음
//		return ResponseEntity.ok(UserUpdateImagePatchRes.of(200, "Success", JwtTokenUtil.getToken(userId)));
//		return ResponseEntity.status(401).body(UserLoginPostRes.of(401, "Invalid Password", null));
		return null;
	}
	
	@PatchMapping("/updateNickname/{userId}")
	@ApiOperation(value = "프로필 이미지 변경", notes = "유저의 프로필 이미지를 변경한다.")
	@ApiResponses({
			// TODO 필요한 response 종류와 메시지 작성
	})
	public ResponseEntity<UserUpdateNicknamePatchRes> updateNickname(
			@RequestBody @ApiParam(value = "닉네임 변경 정보", required = true) UserUpdateNicknamePatchReq updateNicknameInfo) {
		String userId = /* loginInfo.getId() */ ""; // TODO api 전달 방식에 따라 수정

		User user = userService.getUserByUserId(userId);
		// TODO updateNickname res 메시지에 따라 수정
//		return ResponseEntity.ok(UserUpdateNicknamePatchRes.of(200, "Success", JwtTokenUtil.getToken(userId)));
//		return ResponseEntity.status(401).body(UserLoginPostRes.of(401, "Invalid Password", null));
		return null;
	}
}
