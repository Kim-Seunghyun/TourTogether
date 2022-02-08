package com.ssafy.tourtogether.api.request.redis;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("RedisScheduleDeleteReq")
public class RedisScheduleDeleteReq {

	@ApiModelProperty(name="schedule_id", example = "1")
	Long scheduleId;
}
