package com.ssafy.tourtogether.api.service;

import java.util.List;

import com.ssafy.tourtogether.db.entity.TourSpot;

public interface TourSpotService {
	List<TourSpot> getAllTourSpot() throws Exception; // 모든 관광지 가져오기
}
