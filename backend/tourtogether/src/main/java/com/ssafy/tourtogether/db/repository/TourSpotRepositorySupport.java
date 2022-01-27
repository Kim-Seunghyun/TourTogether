package com.ssafy.tourtogether.db.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.tourtogether.db.entity.QTourSpot;
import com.ssafy.tourtogether.db.entity.TourSpot;

/**
 * 관광지 모델 관련 디비 쿼리 생성을 위한 구현 정의.
 */
@Repository
public class TourSpotRepositorySupport {
	@Autowired
	private JPAQueryFactory jpaQueryFactory;
	QTourSpot qTourSpot = QTourSpot.tourSpot;

	public List<TourSpot> findAll() {
		List<TourSpot> tourSpotList = jpaQueryFactory.select(qTourSpot).from(qTourSpot).fetch();
		System.out.println(tourSpotList.size());
		return tourSpotList;
	}
}