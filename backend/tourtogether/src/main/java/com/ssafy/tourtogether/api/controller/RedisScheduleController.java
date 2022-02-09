package com.ssafy.tourtogether.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.tourtogether.api.request.redis.RedisScheduleCreateReq;
import com.ssafy.tourtogether.api.request.redis.RedisScheduleDeleteReq;
import com.ssafy.tourtogether.api.request.redis.RedisScheduleGetByIdReq;
import com.ssafy.tourtogether.api.request.redis.RedisScheduleUpdateReq;
import com.ssafy.tourtogether.api.response.redis.RedisScheduleGetAllRes;
import com.ssafy.tourtogether.api.response.redis.RedisScheduleGetByIdRes;
import com.ssafy.tourtogether.api.service.RedisScheduleService;
import com.ssafy.tourtogether.common.model.response.BaseResponseBody;
import com.ssafy.tourtogether.db.entity.RedisSchedule;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("redisschedule/")
public class RedisScheduleController {
	
	@Autowired
	private RedisScheduleService redisScheduleService;
	
	@PostMapping("/save")
	@ApiOperation(value = "redis 저장", notes = "redis값 저장")
	public ResponseEntity<? extends BaseResponseBody> save(@RequestBody RedisScheduleCreateReq redisScheduleInfo) {
		
		// redis에 저장
		redisScheduleService.save(redisScheduleInfo);
		
		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
	}
	
	@GetMapping("/all")
	@ApiOperation(value = "redis get", notes = "redis 모든 값 가져오기")
	public ResponseEntity<RedisScheduleGetAllRes> getAllRedisSchedule() throws Exception {
		List<RedisSchedule> RedisScheduleList = redisScheduleService.getAll();
		return ResponseEntity.ok(RedisScheduleGetAllRes.of(200, "Success", RedisScheduleList));
	}
	
	@GetMapping("/get")
	@ApiOperation(value = "redis get by id", notes = "redis 특정 값 가져오기")
	public ResponseEntity<RedisScheduleGetByIdRes> getRedisSchedule(
			RedisScheduleGetByIdReq redisScheduleGetByIdReq) {
		Optional<RedisSchedule> redisSchedule = redisScheduleService.getById(redisScheduleGetByIdReq);
		return ResponseEntity.ok(RedisScheduleGetByIdRes.of(200, "Success", redisSchedule));
	}
	
	@DeleteMapping("/delete")
	@ApiOperation(value = "redis delete", notes = "redis 값 삭제")
	public ResponseEntity<? extends BaseResponseBody> delete(@RequestBody RedisScheduleDeleteReq redisScheduleDeleteReq) {
		
		redisScheduleService.delete(redisScheduleDeleteReq);
		
		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
	}
	
	@PutMapping("/update")
	@ApiOperation(value = "redis update", notes = "redis 값 삭제")
	public ResponseEntity<? extends BaseResponseBody> update(@RequestBody RedisScheduleUpdateReq redisScheduleUpdateReq) {
		
		redisScheduleService.update(redisScheduleUpdateReq);
		
		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
	}
}
