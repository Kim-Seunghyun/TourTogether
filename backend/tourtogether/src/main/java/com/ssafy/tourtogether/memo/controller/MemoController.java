package com.ssafy.tourtogether.memo.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import com.ssafy.tourtogether.memo.model.MemoMessage;
import com.ssafy.tourtogether.memo.pubsub.RedisPublisher;
import com.ssafy.tourtogether.memo.repository.MemoRoomRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class MemoController {

	private final RedisPublisher redisPublisher;
	private final MemoRoomRepository memoRoomRepository;

	/**
	 * websocket "/pub/memo"로 들어오는 메시징을 처리한다.
	 */
	@MessageMapping("/memo")
	public void message(MemoMessage message) {
		System.out.println("@@@message: " + message.toString());
		if (memoRoomRepository.findRoomById(message.getRoomId()) == null) {
			memoRoomRepository.createMemoRoom(message.getRoomId());
			memoRoomRepository.enterMemoRoom(message.getRoomId());
		}

		redisPublisher.publish(memoRoomRepository.getTopic(message.getRoomId()), message);

	}
}
