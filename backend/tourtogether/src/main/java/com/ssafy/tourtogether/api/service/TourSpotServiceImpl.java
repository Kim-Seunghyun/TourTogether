package com.ssafy.tourtogether.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.tourtogether.db.entity.TourSpot;
import com.ssafy.tourtogether.db.repository.TourSpotRepositorySupport;

@Service("tourSpotService")
public class TourSpotServiceImpl implements TourSpotService {
	@Autowired
	TourSpotRepositorySupport tourSpotRepositorySupport;

	@Override
	public List<TourSpot> getAllTourSpot() throws Exception {
		List<TourSpot> tourSpotList = tourSpotRepositorySupport.findAll();
		return tourSpotList;
	}

}
