package com.ssafy.tourtogether.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 유저 ID로 모든 보드 불러오기 API ([GET] /board/searchByUserID) 요청에 필요한 리퀘스트 바디 정의.
 */

@Getter
@Setter
@ApiModel("BoardSearchByUserIdReq")
public class BoardSearchByUserIdGetReq {
	@ApiModelProperty(name = "유저 아이디", example = "유저 아이디")
	int userId;
}
