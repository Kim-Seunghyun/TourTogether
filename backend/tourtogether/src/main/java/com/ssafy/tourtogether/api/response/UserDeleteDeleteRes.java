package com.ssafy.tourtogether.api.response;

import com.ssafy.tourtogether.common.model.response.BaseResponseBody;

import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 유저 회원탈퇴 API ([DELETE] /user/delete) 요청에 대한 응답값 정의.
 */

@Getter
@Setter
@ApiModel("UserDeleteDeleteResponse")
public class UserDeleteDeleteRes extends BaseResponseBody {
	boolean successDelete;

	public static UserDeleteDeleteRes of(Integer statusCode, String message, Boolean successDelete) {
		UserDeleteDeleteRes res = new UserDeleteDeleteRes();
		res.setStatusCode(statusCode);
		res.setMessage(message);
		res.setSuccessDelete(successDelete);
		return res;
	}
}
