package com.ssafy.tourtogether.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.tourtogether.db.repository.ScheduleRepositorySupport;

@Service("scheduleService")
public class ScheduleServiceImpl implements ScheduleService {

	@Autowired
	ScheduleRepositorySupport scheduleRepositorySupport;
	
	@Override
	public void updateScheduleInScheduleRoom(String scheduleBoardId, String content) {
		scheduleRepositorySupport.updateScheduleInScheduleRoom(scheduleBoardId, content);
		
	}
}
