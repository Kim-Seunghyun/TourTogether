package com.ssafy.tourtogether.db.entity;

import java.io.Serializable;

import org.springframework.data.redis.core.RedisHash;

import lombok.Getter;
import lombok.Setter;

@RedisHash("MemoRooms")
@Getter
@Setter
public class MemoRoom implements Serializable {

    private static final long serialVersionUID = 6494678977089006639L;

    private String roomId;
    private String name;
    private Memo memo;

    public static MemoRoom create(String id, Memo memo) {
        MemoRoom memoRoom = new MemoRoom();
        memoRoom.roomId = id;
        memoRoom.name = "새 메모룸";
        memoRoom.memo = memo;
        return memoRoom;
    }
}
