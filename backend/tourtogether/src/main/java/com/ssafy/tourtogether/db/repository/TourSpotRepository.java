package com.ssafy.tourtogether.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.tourtogether.db.entity.TourSpot;

public interface TourSpotRepository extends JpaRepository<TourSpot, Integer> {
	List<TourSpot> findAll();
}
