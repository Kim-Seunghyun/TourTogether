package com.ssafy.tourtogether.memo.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Repository;

import com.ssafy.tourtogether.memo.model.MemoRoom;
import com.ssafy.tourtogether.memo.pubsub.RedisSubscriber;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class MemoRoomRepository {
    // 메모(topic)에 발행되는 메시지를 처리할 Listner
    private final RedisMessageListenerContainer redisMessageListener;
    // 구독 처리 서비스
    private final RedisSubscriber redisSubscriber;
    // Redis
    private static final String MEMO_ROOMS = "MEMO_ROOM";
    private final RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, MemoRoom> opsHashMemoRoom;
    // 메모의 메시지를 발행하기 위한 redis topic 정보. 서버별로 메모에 매치되는 topic정보를 Map에 넣어 roomId로 찾을수 있도록 한다.
    private Map<String, ChannelTopic> topics;

    @PostConstruct
    private void init() {
        opsHashMemoRoom = redisTemplate.opsForHash();
        topics = new HashMap<>();
    }

    public List<MemoRoom> findAllRoom() {
        return opsHashMemoRoom.values(MEMO_ROOMS);
    }

    public MemoRoom findRoomById(String id) {
        return opsHashMemoRoom.get(MEMO_ROOMS, id);
    }

    /**
     * 채팅방 생성 : 서버간 메모 공유를 위해 redis hash에 저장한다.
     */
    public MemoRoom createMemoRoom(String name) {
        MemoRoom memoRoom = MemoRoom.create(name);
        opsHashMemoRoom.put(MEMO_ROOMS, memoRoom.getRoomId(), memoRoom);
        return memoRoom;
    }

    /**
     * 메모 입장 : redis에 topic을 만들고 pub/sub 통신을 하기 위해 리스너를 설정한다.
     */
    public void enterMemoRoom(String roomId) {
        ChannelTopic topic = topics.get(roomId);
        if (topic == null)
            topic = new ChannelTopic(roomId);
        redisMessageListener.addMessageListener(redisSubscriber, topic);
        topics.put(roomId, topic);
    }

    public ChannelTopic getTopic(String roomId) {
        return topics.get(roomId);
    }
}
