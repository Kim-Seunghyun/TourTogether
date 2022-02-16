package com.ssafy.tourtogether.memo.pubsub;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import com.ssafy.tourtogether.db.entity.Schedule;
import com.ssafy.tourtogether.db.entity.ScheduleMessage;

@RequiredArgsConstructor
@Service
public class SchedulePublisher {

    private final RedisTemplate<String, Object> redisTemplate;

    public void publish(ChannelTopic topic, ScheduleMessage message) {
        redisTemplate.convertAndSend(topic.getTopic(), message);
    }

}
