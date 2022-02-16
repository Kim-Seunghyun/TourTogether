package com.ssafy.tourtogether.api.service;

import java.util.List;

import com.ssafy.tourtogether.api.request.ScheduleSearchByBoardIdPostReq;
import com.ssafy.tourtogether.db.entity.ScheduleDB;

public interface ScheduleService {
	void updateScheduleInScheduleRoom(String scheduleBoardId, String content);

	List<ScheduleDB> searchByBoardId(ScheduleSearchByBoardIdPostReq scheduleSearchByBoardIdInfo);
}
