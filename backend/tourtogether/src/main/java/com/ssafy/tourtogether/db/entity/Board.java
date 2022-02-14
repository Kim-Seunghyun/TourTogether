package com.ssafy.tourtogether.db.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

/**
 * 보드 모델 정의.
 */
@Entity
@Getter
@Setter
public class Board extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "INT UNSIGNED")
	int boardId;

	@Column(name = "board_name")
	String boardName;

	@Column(name = "board_is_active")
	boolean boardIsActive;

	int boardLikesCount;

	String boardRandom;
}