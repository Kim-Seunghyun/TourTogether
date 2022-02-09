package com.ssafy.tourtogether.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 보드 좋아요 누르기 API ([POST] /board/clickBoardLike) 요청에 필요한 리퀘스트 바디 정의.
 */

@Getter
@Setter
@ApiModel("BoardClickBoardLikePostReq")
public class BoardClickBoardLikePatchReq {

	@ApiModelProperty(name = "보드 아이디", example = "보드 아이디")
	int boardId;

	@ApiModelProperty(name = "유저 아이디", example = "유저 아이디")
	int userId;
}