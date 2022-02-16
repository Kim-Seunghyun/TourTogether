package com.ssafy.tourtogether.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.tourtogether.api.request.ScheduleSearchByBoardIdPostReq;
import com.ssafy.tourtogether.db.entity.ScheduleDB;
import com.ssafy.tourtogether.db.repository.ScheduleRepository;
import com.ssafy.tourtogether.db.repository.ScheduleRepositorySupport;

@Service("scheduleService")
public class ScheduleServiceImpl implements ScheduleService {

	@Autowired
	ScheduleRepository scheduleRepository;
	@Autowired
	ScheduleRepositorySupport scheduleRepositorySupport;

	@Override
	public void updateScheduleInScheduleRoom(String scheduleBoardId, String content) {
		scheduleRepositorySupport.updateScheduleInScheduleRoom(scheduleBoardId, content);

	}

	@Override
	public List<ScheduleDB> searchByBoardId(ScheduleSearchByBoardIdPostReq scheduleSearchByBoardIdInfo) {
		List<ScheduleDB> schedule = scheduleRepositorySupport.findByBoardId(scheduleSearchByBoardIdInfo);
		return schedule;
	}
}
