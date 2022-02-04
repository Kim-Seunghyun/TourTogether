package com.ssafy.tourtogether.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 보드 생성 API ([POST] /board/create) 요청에 필요한 리퀘스트 바디 정의.
 */

@Getter
@Setter
@ApiModel("BoardCreatePostRequest")
public class BoardCreatePostReq {

	@ApiModelProperty(name="보드 이름", example = "보드 이름")
	String boardName;
}