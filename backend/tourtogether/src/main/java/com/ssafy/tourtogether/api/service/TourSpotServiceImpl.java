package com.ssafy.tourtogether.api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.tourtogether.db.entity.TourSpot;

@Service("tourSpotService")
public class TourSpotServiceImpl implements TourSpotService {

	@Override
	public List<TourSpot> getAllTourSpot() throws Exception {
		List<TourSpot> tourSpotList = null;
		return tourSpotList;
	}

}
