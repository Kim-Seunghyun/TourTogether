package com.ssafy.tourtogether.db.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Memo extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public Memo() {
	}

	public Memo(String memoBoardId, String memoContent) {
		setMemoBoardId(memoBoardId);
		setMemoContent(memoContent);
	}
	
	@Id
	String memoBoardId;
	String memoContent; // html	
}
