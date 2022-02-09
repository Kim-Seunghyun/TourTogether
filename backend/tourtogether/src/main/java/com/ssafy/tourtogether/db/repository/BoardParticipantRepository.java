package com.ssafy.tourtogether.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.tourtogether.db.entity.BoardParticipant;

public interface BoardParticipantRepository extends JpaRepository<BoardParticipant, Integer> {

}
