package com.ssafy.tourtogether.db.entity;

import java.io.Serializable;

import javax.persistence.Id;

import lombok.Getter;
import lombok.ToString;
import lombok.Setter;

@Getter
@Setter
@ToString
public class Schedule extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public Schedule() {
	}
	
	public Schedule(String scheduleBoardId, String scheduleContent) {
		setScheduleBoardId(scheduleBoardId);
		setScheduleContent(scheduleContent);
	}
	
	@Id
	String scheduleBoardId;
	String scheduleContent; // html	
}
