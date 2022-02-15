package com.ssafy.tourtogether.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 보드 랜덤값으로 보드 아이디 찾기 API ([POST] /board/searchBoardIdByBoardRandom) 요청에 필요한
 * 리퀘스트 바디 정의.
 */

@Getter
@Setter
@ApiModel("BoardSearchBoardIdByBoardRandomPostReq")
public class BoardSearchBoardIdByBoardRandomPostReq {

	@ApiModelProperty(name = "보드 랜덤값", example = "보드 랜덤값")
	String boardRandom;

}