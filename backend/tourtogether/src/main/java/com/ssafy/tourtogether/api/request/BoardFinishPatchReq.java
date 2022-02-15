package com.ssafy.tourtogether.api.request;

import java.util.List;

import com.ssafy.tourtogether.db.entity.ScheduleDB;

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
	
	@ApiModelProperty(name = "스케줄 리스트", example = "[{}, {}, {}]")
	List<ScheduleDB> scheduleList;
}
