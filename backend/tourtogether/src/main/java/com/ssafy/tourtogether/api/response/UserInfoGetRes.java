package com.ssafy.tourtogether.api.response;

import com.ssafy.tourtogether.common.model.response.BaseResponseBody;
import com.ssafy.tourtogether.db.entity.User;

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
public class UserInfoGetRes extends BaseResponseBody {
	User user;

	public static UserInfoGetRes of(Integer statusCode, String message, User user) {
		UserInfoGetRes res = new UserInfoGetRes();
		res.setStatusCode(statusCode);
		res.setMessage(message);
		res.setUser(user);
		return res;
	}
}
