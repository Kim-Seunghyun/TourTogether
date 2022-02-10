package com.ssafy.tourtogether.db.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("redisSchedule")
public class RedisSchedule {

	@Id
	private Long scheduleId;
	
	private int boardId;
	
	private int day;
	
	private int userSpotId;
	
	private int tourSpotId;
	
	private int order;
	
	private String additional;

}
