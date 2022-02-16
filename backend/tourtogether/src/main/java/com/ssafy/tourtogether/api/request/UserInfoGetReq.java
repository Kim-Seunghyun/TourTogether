package com.ssafy.tourtogether.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 유저 로그인 API ([GET] /user/info) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ToString
@ApiModel("UserInfoGetReq")
public class UserInfoGetReq {
	@ApiModelProperty(name = "유저 client id", example = "2012345678")
	String userClientId;
	@ApiModelProperty(name = "유저가 로그인한 플랫폼", example = "카카오")
	String userLoginPlatform;
}
