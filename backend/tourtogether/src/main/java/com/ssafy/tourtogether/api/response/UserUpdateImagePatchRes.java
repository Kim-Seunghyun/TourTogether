package com.ssafy.tourtogether.api.response;

import com.ssafy.tourtogether.common.model.response.BaseResponseBody;
import com.ssafy.tourtogether.db.entity.User;

import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
* 유저 프로필 변경 API ([PATCH] /user/updateImage/{userId}) 요청에 대한 응답값 정의.
*/

@Getter
@Setter
@ApiModel("UserUpdateImagePatchRes")
public class UserUpdateImagePatchRes extends BaseResponseBody{
	// TODO 토큰 형식 수정
//	@ApiModelProperty(name="JWT 인증 토큰", example="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN...")
//	String accessToken;
	User user;
	
	public static UserUpdateImagePatchRes of(Integer statusCode, String message, User user) {
		UserUpdateImagePatchRes res = new UserUpdateImagePatchRes();
		res.setStatusCode(statusCode);
		res.setMessage(message);
//		res.setAccessToken(accessToken);
		res.setUser(user);
		return res;
	}
}
