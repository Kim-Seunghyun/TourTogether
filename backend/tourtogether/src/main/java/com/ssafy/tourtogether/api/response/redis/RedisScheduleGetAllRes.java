package com.ssafy.tourtogether.api.response.redis;

import java.util.List;

import com.ssafy.tourtogether.common.model.response.BaseResponseBody;
import com.ssafy.tourtogether.db.entity.RedisSchedule;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("RedisScheduleGetAllResponse")
public class RedisScheduleGetAllRes extends BaseResponseBody {
	List<RedisSchedule> redisScheduleList = null;
	
	public static RedisScheduleGetAllRes of(Integer statusCode, String message, List<RedisSchedule> redisScheduleList) {
	RedisScheduleGetAllRes res = new RedisScheduleGetAllRes();
	res.setStatusCode(statusCode);
	res.setMessage(message);
	res.setRedisScheduleList(redisScheduleList);
	
	return res;
	}
}