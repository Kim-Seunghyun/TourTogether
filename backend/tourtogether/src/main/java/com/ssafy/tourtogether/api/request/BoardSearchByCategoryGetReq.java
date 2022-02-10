package com.ssafy.tourtogether.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 유형별로 보드 불러오기 API ([GET] /board/searchByCategory) 요청에 필요한 리퀘스트 바디 정의.
 */

@Getter
@Setter
@ApiModel("BoardSearchByCategoryGetReq")
public class BoardSearchByCategoryGetReq {

	@ApiModelProperty(name = "여행자 유형", example = "여행자 유형")
	int categoryWithWhom;

	@ApiModelProperty(name = "계절 유형", example = "계절 유형")
	int categorySeason;

	@ApiModelProperty(name = "관광지 유형", example = "관광지 유형")
	int categoryArea;

	@ApiModelProperty(name = "관광테마 유형", example = "관광테마 유형")
	int categoryTheme;
}