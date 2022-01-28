package com.ssafy.tourtogether.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 유저 닉네임 변경 API ([PATCH] /user/updateNickname/{userId}) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ApiModel("UserUpdateNicknamePatchRequest")
public class UserUpdateNicknamePatchReq {
	// TODO updateNickname req를 통해 들어오는 data에 따라 추가
	@ApiModelProperty(name = "유저가 로그인한 플랫폼", example = "카카오")
	String userLoginPlatform;
	@ApiModelProperty(name = "유저 client id", example = "2012345678")
	String userClientId;
	@ApiModelProperty(name = "기존 유저 닉네임", example = "김철수")
	String userNickname;
	@ApiModelProperty(name = "유저가 변경하려는 닉네임", example = "김철수 짱짱맨")
	String newUserNickname;
}
