package com.ssafy.tourtogether.db.repository;

import org.springframework.data.repository.CrudRepository;

import com.ssafy.tourtogether.db.entity.RedisSchedule;

public interface RedisScheduleRepository extends CrudRepository<RedisSchedule, Long>  {

}
