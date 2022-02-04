package com.ssafy.tourtogether.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 유저 프로필 변경 API ([PATCH] /user/updateImage/{userId}) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ApiModel("UserUpdateImagePatchRequest")
@ToString
public class UserUpdateImagePatchReq {
	@ApiModelProperty(name = "유저가 로그인한 플랫폼", example = "카카오")
	String userLoginPlatform;
	@ApiModelProperty(name = "유저 client id", example = "2012345678")
	String userClientId;
	@ApiModelProperty(name = "기존 유저 닉네임", example = "김철수")
	String userProfileImage;
}
