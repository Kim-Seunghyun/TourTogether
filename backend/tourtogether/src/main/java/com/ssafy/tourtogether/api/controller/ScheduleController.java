package com.ssafy.tourtogether.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssafy.tourtogether.api.service.ScheduleService;
import com.ssafy.tourtogether.db.entity.ScheduleMessage;
import com.ssafy.tourtogether.db.entity.ScheduleRoom;
import com.ssafy.tourtogether.db.repository.ScheduleRepositorySupport;
import com.ssafy.tourtogether.memo.pubsub.SchedulePublisher;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@Api(value = "스케쥴 API", tags = { "Schedule" })
@Controller
@RequiredArgsConstructor
public class ScheduleController {
	
	@Autowired
	ScheduleService scheduleService;
	private final SchedulePublisher schedulePublisher;
	private final ScheduleRepositorySupport scheduleRepository;

	@PostMapping("/api/schedule/room")
	@ResponseBody
	public ScheduleRoom createRoom(@RequestParam String id, @RequestParam String user) {
		ScheduleRoom scheduleRoom = scheduleRepository.findRoomById(id);
		if(scheduleRoom==null) {			
			scheduleRoom = scheduleRepository.createScheduleRoom(id);
		}
		scheduleRepository.enterScheduleRoom(id);
		return scheduleRoom;
	}
	
	/**
	 * websocket "/api/pub/memo"로 들어오는 메시징을 처리한다.
	 */
	@MessageMapping("/schedule")
	@ApiOperation(value = "스케줄", notes = "유저가 메모에 입력한 스케쥴을 받는다.")
	public void schedule(ScheduleMessage message) {
		System.out.println("@@@message: " + message.toString());

		String scheduleBoardId = message.getRoomId();
		String scheduleContent = message.getContent();

		scheduleService.updateScheduleInScheduleRoom(scheduleBoardId, scheduleContent);

		schedulePublisher.publish(scheduleRepository.getTopic(message.getRoomId()), message);
	}

}
