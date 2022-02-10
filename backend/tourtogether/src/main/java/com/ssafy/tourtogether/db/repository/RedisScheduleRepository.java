package com.ssafy.tourtogether.db.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.tourtogether.db.entity.RedisSchedule;

@Repository
public interface RedisScheduleRepository extends CrudRepository<RedisSchedule, Long>  {

}
