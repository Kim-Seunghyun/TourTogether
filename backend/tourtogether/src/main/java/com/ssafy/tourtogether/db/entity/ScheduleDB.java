package com.ssafy.tourtogether.db.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ScheduleDB {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "INT UNSIGNED")
	int scheduleId;

	@Column(name = "schedule_day")
	int scheduleDay;
	
	@Column(name = "schedule_ord")
	int scheduleOrd;

	@Column(name = "schedule_additional")
	String scheduleAdditional;

	@Column(name = "schedule_tour_spot_id")
	int scheduleTourSpotId;
	
	@Column(name = "schedule_board_id")
	int scheduleBoardId;
}
