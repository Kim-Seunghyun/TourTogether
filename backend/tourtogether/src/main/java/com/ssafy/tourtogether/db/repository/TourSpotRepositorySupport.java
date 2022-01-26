package com.ssafy.tourtogether.db.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.stereotype.Repository;

/**
 * 관광지 모델 관련 디비 쿼리 생성을 위한 구현 정의.
 */
@Repository
public class TourSpotRepositorySupport {
	@Autowired
	private JpaRepositoryFactory jpaRepositoryFactory;
//	QTourSpot qTourSpot = QTourSpot.
}