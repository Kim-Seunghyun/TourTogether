package com.ssafy.tourtogether.api.service;

import java.util.List;

import com.ssafy.tourtogether.db.entity.ScheduleDB;

public interface ScheduleDBService {
	
	void saveScheduleList(List<ScheduleDB> list);
}
