package com.ssafy.tourtogether.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.tourtogether.db.entity.TourSpot;

@Repository
public interface TourSpotRepository extends JpaRepository<TourSpot, Integer> {
	List<TourSpot> findAll();
}
