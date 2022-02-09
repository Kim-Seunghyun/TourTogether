package com.ssafy.tourtogether.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 보드 완료 API ([Patch] /board/finish) 요청에 필요한 리퀘스트 바디 정의.
 */

@Getter
@Setter
@ApiModel("BoardFinishPatchReq")
public class BoardFinishPatchReq {
	@ApiModelProperty(name = "보드 아이디", example = "보드 아이디")
	int boardId;
}
