package com.ssafy.tourtogether.db.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.tourtogether.api.request.ScheduleSearchByBoardIdPostReq;
import com.ssafy.tourtogether.db.entity.QScheduleDB;
import com.ssafy.tourtogether.db.entity.Schedule;
import com.ssafy.tourtogether.db.entity.ScheduleDB;
import com.ssafy.tourtogether.db.entity.ScheduleRoom;
import com.ssafy.tourtogether.memo.pubsub.RedisSubscriber;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class ScheduleRepositorySupport {

	@Autowired
	private JPAQueryFactory jpaQueryFactory;
	QScheduleDB qScheduleDB = QScheduleDB.scheduleDB;

	// 메모(topic)에 발행되는 메시지를 처리할 Listner
	private final RedisMessageListenerContainer redisMessageListener;
	// 구독 처리 서비스
	private final RedisSubscriber redisSubscriber;
	// Redis
	private static final String SCHEDULE_ROOMS = "SCHEDULE_ROOM";
	private final RedisTemplate<String, Object> redisTemplate;
	private HashOperations<String, String, ScheduleRoom> opsHashScheduleRoom;

	private Map<String, ChannelTopic> topics;

	@PostConstruct
	private void init() {
		opsHashScheduleRoom = redisTemplate.opsForHash();
		topics = new HashMap<>();
	}

	public ScheduleRoom findRoomById(String id) {
		return opsHashScheduleRoom.get(SCHEDULE_ROOMS, id);
	}

	/**
	 * 채팅방 생성 : 서버간 공유를 위해 redis hash에 저장한다.
	 */

	/**
	 * 채팅방 생성 : 서버간 메모 공유를 위해 redis hash에 저장한다.
	 */
	public ScheduleRoom createScheduleRoom(String id) {
		System.out.println("createScheduleRoom: " + id);
		ScheduleRoom scheduleRoom = ScheduleRoom.create(id, new Schedule(id, ""));
		opsHashScheduleRoom.put(SCHEDULE_ROOMS, scheduleRoom.getRoomId(), scheduleRoom);
		return scheduleRoom;
	}

	public void enterScheduleRoom(String id) {
		System.out.println("enterScheduleRoom: " + id);
		ChannelTopic topic = topics.get(id);
		if (topic == null)
			topic = new ChannelTopic(id);
		redisMessageListener.addMessageListener(redisSubscriber, topic);
		topics.put(id, topic);
	}

	public void updateScheduleInScheduleRoom(String id, String content) {
		opsHashScheduleRoom.put(SCHEDULE_ROOMS, id, ScheduleRoom.create(id, new Schedule(id, content)));
	}

	public ChannelTopic getTopic(String roomId) {
		return topics.get(roomId);
	}

	public List<ScheduleDB> findByBoardId(ScheduleSearchByBoardIdPostReq scheduleSearchByBoardIdInfo) {
		List<ScheduleDB> schedule = jpaQueryFactory.select(qScheduleDB).from(qScheduleDB)
				.where(qScheduleDB.scheduleBoardId.eq(scheduleSearchByBoardIdInfo.getBoardId())).fetch();
		return schedule;
	}
}
