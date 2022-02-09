package com.ssafy.tourtogether.db.entity;

import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

/**
 * 모델 간 공통 사항 정의
 */

@Getter
@Setter
@MappedSuperclass
public class BaseEntity {
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	Long id = null;

}
