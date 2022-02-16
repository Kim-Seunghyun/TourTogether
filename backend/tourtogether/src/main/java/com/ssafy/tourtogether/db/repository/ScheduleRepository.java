package com.ssafy.tourtogether.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.tourtogether.db.entity.Board;

public interface ScheduleRepository extends JpaRepository<Board, Integer> {

}
