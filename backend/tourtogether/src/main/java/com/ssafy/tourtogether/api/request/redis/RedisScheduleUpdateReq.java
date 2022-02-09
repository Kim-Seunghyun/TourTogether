package com.ssafy.tourtogether.api.request.redis;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("RedisScheduleUpdateRequest")
public class RedisScheduleUpdateReq {

	@ApiModelProperty(name="schedule_id", example = "1")
	Long scheduleId;
	
	@ApiModelProperty(name="board_id", example = "1")
	int boardId;
	
	@ApiModelProperty(name="dat", example = "1")
	int day;
	
	@ApiModelProperty(name="user_spot_id", example = "1")
	int userSpotId;
	
	@ApiModelProperty(name="tour_spot_id", example = "1")
	int tourSpotId;
	
	@ApiModelProperty(name="order", example = "1")
	int order;
	
	@ApiModelProperty(name="additional", example = "1")
	String additional;
}
