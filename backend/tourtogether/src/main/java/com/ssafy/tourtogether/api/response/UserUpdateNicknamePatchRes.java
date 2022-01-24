package com.ssafy.tourtogether.api.response;

import com.ssafy.tourtogether.common.model.response.BaseResponseBody;

import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
* 유저 프로필 변경 API ([PATCH] /user/updateNickname/{userId}) 요청에 대한 응답값 정의.
*/

@Getter
@Setter
@ApiModel("UserUpdateNicknamePatchRes")
public class UserUpdateNicknamePatchRes extends BaseResponseBody{
	// TODO 토큰 형식 수정
//	@ApiModelProperty(name="JWT 인증 토큰", example="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN...")
	String accessToken;
	
	public static UserUpdateNicknamePatchRes of(Integer statusCode, String message, String accessToken) {
		UserUpdateNicknamePatchRes res = new UserUpdateNicknamePatchRes();
		res.setStatusCode(statusCode);
		res.setMessage(message);
		res.setAccessToken(accessToken);
		return res;
	}
}
