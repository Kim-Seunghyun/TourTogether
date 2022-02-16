package com.ssafy.tourtogether.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.tourtogether.api.request.ScheduleSearchByBoardIdPostReq;
import com.ssafy.tourtogether.api.response.ScheduleSearchByBoardIdPostRes;
import com.ssafy.tourtogether.api.service.ScheduleService;
import com.ssafy.tourtogether.common.model.response.BaseResponseBody;
import com.ssafy.tourtogether.db.entity.ScheduleDB;
import com.ssafy.tourtogether.db.entity.ScheduleMessage;
import com.ssafy.tourtogether.db.entity.ScheduleRoom;
import com.ssafy.tourtogether.db.repository.ScheduleRepositorySupport;
import com.ssafy.tourtogether.memo.pubsub.SchedulePublisher;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

@Api(value = "스케쥴 API", tags = { "Schedule" })
@RestController
@RequestMapping("/api/schedule")
@RequiredArgsConstructor
public class ScheduleController {

	@Autowired
	ScheduleService scheduleService;
	private final SchedulePublisher schedulePublisher;
	private final ScheduleRepositorySupport scheduleRepository;

	@PostMapping("/room")
	@ResponseBody
	public ScheduleRoom createRoom(@RequestParam String id, @RequestParam String user) {
		ScheduleRoom scheduleRoom = scheduleRepository.findRoomById(id);
		if (scheduleRoom == null) {
			scheduleRoom = scheduleRepository.createScheduleRoom(id);
		}
		scheduleRepository.enterScheduleRoom(id);
		return scheduleRoom;
	}

	/**
	 * websocket "/api/pub/memo"로 들어오는 메시징을 처리한다.
	 */
	@MessageMapping()
	@ApiOperation(value = "스케줄", notes = "유저가 메모에 입력한 스케쥴을 받는다.")
	public void schedule(ScheduleMessage message) {
		System.out.println("@@@message: " + message.toString());

		String scheduleBoardId = message.getRoomId();
		String scheduleContent = message.getContent();

		scheduleService.updateScheduleInScheduleRoom(scheduleBoardId, scheduleContent);

		schedulePublisher.publish(scheduleRepository.getTopic(message.getRoomId()), message);
	}

	@PostMapping("/searchByBoardId")
	@ApiOperation(value = "보드 ID로 스케쥴 가져오기", notes = "보드 ID로 스케쥴 가져온다")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공", response = BaseResponseBody.class),
			@ApiResponse(code = 401, message = "인증 실패", response = BaseResponseBody.class),
			@ApiResponse(code = 404, message = "보드 없음", response = BaseResponseBody.class),
			@ApiResponse(code = 500, message = "서버 오류", response = BaseResponseBody.class) })

	public ResponseEntity<? extends BaseResponseBody> searchByBoardId(
			@RequestBody @ApiParam(value = "불러올 보드 ID", required = true) ScheduleSearchByBoardIdPostReq scheduleSearchByBoardIdInfo) {

		List<ScheduleDB> schedule = scheduleService.searchByBoardId(scheduleSearchByBoardIdInfo);
		return ResponseEntity.status(200).body(ScheduleSearchByBoardIdPostRes.of(200, "Success", schedule));
	}

}
