package com.ssafy.tourtogether.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssafy.tourtogether.api.service.MemoService;
import com.ssafy.tourtogether.db.entity.MemoMessage;
import com.ssafy.tourtogether.db.entity.MemoRoom;
import com.ssafy.tourtogether.db.repository.MemoRepositorySupport;
import com.ssafy.tourtogether.memo.pubsub.RedisPublisher;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

/**
 * 메모 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "메모 API", tags = { "Memo" })
@Controller
@RequiredArgsConstructor
public class MemoController {
	@Autowired
	MemoService memoservice;
	private final RedisPublisher redisPublisher;
	private final MemoRepositorySupport memoRoomRepository;

	@PostMapping("/api/memo/room")
	@ResponseBody
	public MemoRoom createRoom(@RequestParam String id, @RequestParam String user) {
		MemoRoom memoRoom = memoRoomRepository.findRoomById(id);
		if(memoRoom==null) {			
			memoRoom = memoRoomRepository.createMemoRoom(id);
		}
		memoRoomRepository.enterMemoRoom(id);
		return memoRoom;
	}

	/**
	 * websocket "/pub/memo"로 들어오는 메시징을 처리한다.
	 */
	@MessageMapping("/api/memo")
	@ApiOperation(value = "메모", notes = "유저가 메모에 입력한 메시지를 받는다.")
	public void memo(MemoMessage message) {
		System.out.println("@@@message: " + message.toString());

		String memoBoardId = message.getRoomId();
		String memoContent = message.getContent();

		memoservice.updateMemoInMemoRoom(memoBoardId, memoContent);

		redisPublisher.publish(memoRoomRepository.getTopic(message.getRoomId()), message);
	}
}
