package com.ssafy.tourtogether.api.response;

import com.ssafy.tourtogether.common.model.response.BaseResponseBody;

import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 유저 로그인 API ([POST] user/login) 요청에 대한 응답값 정의.
 */

@Getter
@Setter
@ApiModel("UserLoginPostResponse")
public class UserLoginPostRes extends BaseResponseBody {
	String accessToken;

	public static UserLoginPostRes of(Integer statusCode, String message, String accessToken) {
		UserLoginPostRes res = new UserLoginPostRes();
		res.setStatusCode(statusCode);
		res.setMessage(message);
		res.setAccessToken(accessToken);
		return res;
	}
}
