package com.ssafy.tourtogether.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.tourtogether.db.entity.ScheduleDB;
import com.ssafy.tourtogether.db.repository.ScheduleDBRepository;

@Service("scheduleDBService")
public class ScheduleDBServiceImpl implements ScheduleDBService {
	
	@Autowired
	ScheduleDBRepository scheduleDBRepository;

	@Override
	public void saveScheduleList(List<ScheduleDB> list) {
		for (ScheduleDB schedule : list) {
			ScheduleDB scheduleDB = new ScheduleDB();
			scheduleDB.setScheduleDay(schedule.getScheduleDay());
			scheduleDB.setScheduleOrd(schedule.getScheduleOrd());
			scheduleDB.setScheduleAdditional(schedule.getScheduleAdditional());
			scheduleDB.setScheduleTourSpotId(schedule.getScheduleTourSpotId());
			scheduleDB.setScheduleBoardId(schedule.getScheduleBoardId());
			scheduleDBRepository.save(schedule);
		}	
		
	}

}
