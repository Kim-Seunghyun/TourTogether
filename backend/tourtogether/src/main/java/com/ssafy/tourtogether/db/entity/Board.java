package com.ssafy.tourtogether.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 보드 모델 정의.
 */
@Entity
@Getter
@Setter
public class Board extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "INT UNSIGNED")
	int boardId;
	
	@Column(name="board_name")
	String boardName;
	
	@Column(name="board_is_active")
	boolean boardIsActive;
}