package com.ssafy.tourtogether.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 보드 ID에 따른 스케줄 불러오기 API ([Post] /schedule/searchByBoardId) 요청에 필요한 리퀘스트 바디 정의.
 */

@Getter
@Setter
@ApiModel("ScheduleSearchByBoardIdPostReq")
public class ScheduleSearchByBoardIdPostReq {
	@ApiModelProperty(name = "보드 아이디", example = "보드 아이디")
	int boardId;
}
