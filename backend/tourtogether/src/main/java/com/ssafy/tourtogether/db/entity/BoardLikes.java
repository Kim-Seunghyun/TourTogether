package com.ssafy.tourtogether.db.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

/**
 * 보드 좋아요 정의.
 */
@Entity
@Getter
@Setter
public class BoardLikes extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "INT UNSIGNED")
	int boardLikesId;

	int boardLikesBoardId;

	int boardLikesUserId;

}