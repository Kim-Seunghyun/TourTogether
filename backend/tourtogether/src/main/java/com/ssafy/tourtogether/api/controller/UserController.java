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
import com.ssafy.tourtogether.common.model.response.BaseResponseBody;
//import com.ssafy.tourtogether.common.util.JwtTokenUtil;
import com.ssafy.tourtogether.db.entity.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

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
	@ApiResponses({ @ApiResponse(code = 200, message = "성공", response = UserLoginPostRes.class),
			@ApiResponse(code = 500, message = "서버 오류", response = BaseResponseBody.class) })
	public ResponseEntity<UserLoginPostRes> login(
			@RequestBody @ApiParam(value = "로그인 정보", required = true) UserLoginPostReq loginInfo) {
		String userClientId = loginInfo.getUserClientId();
		String userLoginPlatformString = loginInfo.getUserLoginPlatform();
		int userLoginPlatform = -1;

		if (userLoginPlatformString.compareTo("kakao") == 0)
			userLoginPlatform = 1;
		else if (userLoginPlatformString.compareTo("naver") == 0)
			userLoginPlatform = 2;
		else if (userLoginPlatformString.compareTo("google") == 0)
			userLoginPlatform = 3;

		System.out.println("login Req with: " + loginInfo.toString());

		User user;
		user = userService.getUserByUserId(userClientId, userLoginPlatform);

		if (user == null) {
			System.out.println("회원가입 필요");
			user = userService.createUser(loginInfo);
		} else
			System.out.println("기존유저");
		System.out.println("Return user: " + user.toString());
		return ResponseEntity.ok(UserLoginPostRes.of(200, "Success", user/* JwtTokenUtil.getToken(userId) */));
	}

	@DeleteMapping("/delete")
	@ApiOperation(value = "회원탈퇴", notes = "소셜로그인 API를 통해 가입한 계정을 탈퇴한다.")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공", response = UserLoginPostRes.class),
			@ApiResponse(code = 500, message = "서버 오류", response = BaseResponseBody.class) })
	public ResponseEntity<UserDeleteDeleteRes> delete(
			@RequestBody @ApiParam(value = "회원탈퇴 정보", required = true) UserDeleteDeleteReq deleteInfo) {
		if (userService.checkUser(deleteInfo)) {
			System.out.println("유저 삭제됨");
			userService.deleteUser(deleteInfo);
			return ResponseEntity.ok(UserDeleteDeleteRes.of(200, "Success", true));
		} else {
			System.out.println("존재하지 않은 유저");
			return ResponseEntity.ok(UserDeleteDeleteRes.of(200, "Success", false));
		}
	}

	@PatchMapping("/updateImage")
	@ApiOperation(value = "프로필 이미지 변경", notes = "유저의 프로필 이미지를 변경한다.")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공", response = UserLoginPostRes.class),
			@ApiResponse(code = 500, message = "서버 오류", response = BaseResponseBody.class) })
	public ResponseEntity<UserUpdateImagePatchRes> updateImage(
			@RequestBody @ApiParam(value = "프로필 변경 정보", required = true) UserUpdateImagePatchReq updateImageInfo) {
		String userClientId = updateImageInfo.getUserClientId();
		String userLoginPlatformString = updateImageInfo.getUserLoginPlatform();
		int userLoginPlatform = -1;

		if (userLoginPlatformString.compareTo("kakao") == 0)
			userLoginPlatform = 1;
		else if (userLoginPlatformString.compareTo("naver") == 0)
			userLoginPlatform = 2;
		else if (userLoginPlatformString.compareTo("google") == 0)
			userLoginPlatform = 3;

		System.out.println("update image Req with: " + updateImageInfo.toString());

		User user = userService.getUserByUserId(userClientId, userLoginPlatform);
		if (user == null) {
			System.out.println("유효하지 않은 유저");
			return ResponseEntity.status(401).body(UserUpdateImagePatchRes.of(401, "유효하지 않은 유저", null));
		} else {
			System.out.println("유효한 유저");
			String userProfileImage = updateImageInfo.getUserProfileImage();
			user = userService.updateUserProfileImage(userClientId, userLoginPlatform, userProfileImage);

			if (user == null) {
				System.out.println("잘못된 이미지 링크");
				return ResponseEntity.status(403).body(UserUpdateImagePatchRes.of(403, "잘못된 이미지 링크", null));
			} else {
				System.out.println("이미지 변경 성공 ====> " + user.getUserProfileImage());
				return ResponseEntity.ok().body(UserUpdateImagePatchRes.of(200, "이미지 변경 성공", user));
			}
		}
	}

	@PatchMapping("/updateNickname")
	@ApiOperation(value = "프로필 이미지 변경", notes = "유저의 프로필 이미지를 변경한다.")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공", response = UserLoginPostRes.class),
			@ApiResponse(code = 500, message = "서버 오류", response = BaseResponseBody.class) })
	public ResponseEntity<UserUpdateNicknamePatchRes> updateNickname(
			@RequestBody @ApiParam(value = "닉네임 변경 정보", required = true) UserUpdateNicknamePatchReq updateNicknameInfo) {
		String userClientId = updateNicknameInfo.getUserClientId();
		String userLoginPlatformString = updateNicknameInfo.getUserLoginPlatform();
		int userLoginPlatform = -1;

		if (userLoginPlatformString.compareTo("kakao") == 0)
			userLoginPlatform = 1;
		else if (userLoginPlatformString.compareTo("naver") == 0)
			userLoginPlatform = 2;
		else if (userLoginPlatformString.compareTo("google") == 0)
			userLoginPlatform = 3;

		System.out.println("update nickname Req with: " + updateNicknameInfo.toString());

		User user = userService.getUserByUserId(userClientId, userLoginPlatform);
		if (user == null) {
			System.out.println("유효하지 않은 유저");
			return ResponseEntity.status(401).body(UserUpdateNicknamePatchRes.of(401, "유효하지 않은 유저", null));
		} else {
			System.out.println("유효한 유저");
			String userNickname = updateNicknameInfo.getUserNickname();
			String newUserNickname = updateNicknameInfo.getNewUserNickname();
			user = userService.updateUserNickname(userNickname, newUserNickname);

			if (user == null) {
				System.out.println("잘못된 닉네임");
				return ResponseEntity.status(403).body(UserUpdateNicknamePatchRes.of(403, "잘못된 닉네임", null));
			} else {
				System.out.println("닉네임 변경 성공: " + userNickname + " ====> " + user.getUserNickname());
				return ResponseEntity.ok().body(UserUpdateNicknamePatchRes.of(200, "닉네임 변경 성공", user));
			}
		}
	}
}
