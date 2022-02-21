package com.ssafy.tourtogether.db.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// 실제로 프론트에서 넘어오는 정보
@Getter
@Setter
@ToString
public class ScheduleMessage extends BaseMessage{

    private String roomId; // 방번호
    private String user;
//    private Integer day;
//    private Integer orderInteger;
//    private double lat;
//    private double lng;
//    private String name;
    private String content;
}
