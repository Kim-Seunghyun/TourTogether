package com.ssafy.tourtogether.memo.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemoMessage {

    // 메시지 타입 : 입장, 채팅
//    public enum MessageType {
//        ENTER, TALK
//    }

//    private MessageType type; // 메시지 타입
    private String roomId; // 방번호
    private String user;
    private Object delta; // 메시지
}
