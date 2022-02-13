package com.ssafy.tourtogether.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 유저 회원탈퇴 API ([DELETE] /user/delete) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ApiModel("UserDeleteDeleteRequest")
public class UserDeleteDeleteReq {
	@ApiModelProperty(name = "유저 ID", example = "ssafy_web")
	int userId;
	@ApiModelProperty(name = "유저 Email", example = "your_password")
	String userEmail;

}
