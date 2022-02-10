package com.ssafy.tourtogether.memo.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
public class MemoRoom implements Serializable {

    private static final long serialVersionUID = 6494678977089006639L;

    private String roomId;
    private String name;

    public static MemoRoom create(String name) {
        MemoRoom memoRoom = new MemoRoom();
        memoRoom.roomId = UUID.randomUUID().toString();
        memoRoom.name = name;
        return memoRoom;
    }
}
