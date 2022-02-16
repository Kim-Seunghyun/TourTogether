package com.ssafy.tourtogether.db.entity;

import java.io.Serializable;

import org.springframework.data.redis.core.RedisHash;

import lombok.Getter;
import lombok.Setter;

@RedisHash("ScheduleRooms")
@Getter
@Setter
public class ScheduleRoom implements Serializable {

	private static final long serialVersionUID = 6494678977089006639L;
	
    private String roomId;
    private String name;
    private Schedule schedule;
    
    public static ScheduleRoom create(String id, Schedule schedule) {
    	ScheduleRoom scheduleRoom = new ScheduleRoom();
    	scheduleRoom.roomId = id;
    	scheduleRoom.name = "새 스케쥴 룸";
    	scheduleRoom.schedule = schedule;
    	return scheduleRoom;
    }

}
