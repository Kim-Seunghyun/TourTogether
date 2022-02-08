package com.ssafy.tourtogether.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.tourtogether.api.request.redis.RedisScheduleCreateReq;
import com.ssafy.tourtogether.api.request.redis.RedisScheduleDeleteReq;
import com.ssafy.tourtogether.api.request.redis.RedisScheduleGetByIdReq;
import com.ssafy.tourtogether.api.request.redis.RedisScheduleUpdateReq;
import com.ssafy.tourtogether.db.entity.RedisSchedule;
import com.ssafy.tourtogether.db.repository.RedisScheduleRepository;

@Service
public class RedisScheduleService {

	@Autowired
	private RedisScheduleRepository redisScheduleRepository;
	
	public void save(RedisScheduleCreateReq redisScheduleInfo) {
		RedisSchedule redisSchedule = new RedisSchedule();
		
		redisSchedule.setScheduleId(redisScheduleInfo.getScheduleId());
		redisSchedule.setBoardId(redisScheduleInfo.getBoardId());
		redisSchedule.setDay(redisScheduleInfo.getDay());
		redisSchedule.setUserSpotId(redisScheduleInfo.getUserSpotId());
		redisSchedule.setTourSpotId(redisScheduleInfo.getTourSpotId());
		redisSchedule.setOrder(redisScheduleInfo.getOrder());
		redisSchedule.setAdditional(redisScheduleInfo.getAdditional());
		
		redisScheduleRepository.save(redisSchedule);

	}
	
	public List<RedisSchedule> getAll() {
		List<RedisSchedule> redisScheduleList = (List<RedisSchedule>) redisScheduleRepository.findAll();
		return redisScheduleList;
	}
	
	public Optional<RedisSchedule> getById(RedisScheduleGetByIdReq redisScheduleGetByIdReq) {
		Optional<RedisSchedule> redisSchedule = redisScheduleRepository.findById(redisScheduleGetByIdReq.getScheduleId());
		return redisSchedule;
	}
	
	public void delete(RedisScheduleDeleteReq redisScheduleDeleteReq) {
		redisScheduleRepository.deleteById(redisScheduleDeleteReq.getScheduleId());
	}
	
	public void update(RedisScheduleUpdateReq redisScheduleUpdateReq) {
			
		RedisSchedule redisSchedule = new RedisSchedule();
		
		redisSchedule.setScheduleId(redisScheduleUpdateReq.getScheduleId());
		redisSchedule.setBoardId(redisScheduleUpdateReq.getBoardId());
		redisSchedule.setDay(redisScheduleUpdateReq.getDay());
		redisSchedule.setUserSpotId(redisScheduleUpdateReq.getUserSpotId());
		redisSchedule.setTourSpotId(redisScheduleUpdateReq.getTourSpotId());
		redisSchedule.setOrder(redisScheduleUpdateReq.getOrder());
		redisSchedule.setAdditional(redisScheduleUpdateReq.getAdditional());
		
		redisScheduleRepository.save(redisSchedule);
	}
}
