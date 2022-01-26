package com.ssafy.tourtogether.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 유저 로그인 API ([POST] /user/login) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ToString
@ApiModel("UserLoginPostRequest")
public class UserLoginPostReq {
	@ApiModelProperty(name = "유저 이름 (카카오에서 유저가 설정한 닉네임)", example = "김철수")
	String userName;
	@ApiModelProperty(name = "유저 프로필 사진 (카카오에서 유저가 설정한 프로필 사진)", example = "user/김철수.jpg")
	String userProfileImage;
	@ApiModelProperty(name = "유저가 로그인한 플랫폼", example = "카카오")
	String userLoginPlatform;
	@ApiModelProperty(name = "유저 client id", example = "asdf1234")
	String userClientId;
	@ApiModelProperty(name = "유저 이메일 (카카오 계쩡이 등록된 이메일)", example = "test@test.com")
	String userEmail;
}
