package com.ssafy.tourtogether.api.response.redis;

import java.util.Optional;

import com.ssafy.tourtogether.common.model.response.BaseResponseBody;
import com.ssafy.tourtogether.db.entity.RedisSchedule;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("RedisScheduleGetByIdRes")
public class RedisScheduleGetByIdRes extends BaseResponseBody {
	Optional<RedisSchedule> redisSchedule = null;
	
	public static RedisScheduleGetByIdRes of(Integer statusCode, String message, Optional<RedisSchedule> redisSchedule) {
		RedisScheduleGetByIdRes res = new RedisScheduleGetByIdRes();
		res.setStatusCode(statusCode);
		res.setMessage(message);
		res.setRedisSchedule(redisSchedule);
		
		return res;
	}

}