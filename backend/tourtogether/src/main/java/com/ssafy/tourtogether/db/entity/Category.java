package com.ssafy.tourtogether.db.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

/**
 * 보드 유형 모델 정의.
 */
@Entity
@Getter
@Setter
public class Category extends BaseEntity {

	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "INT UNSIGNED")
	int categoryBoardId;

	int categoryWithWhom;
	int categorySeason;
	int categoryArea;
	int categoryTheme;
}