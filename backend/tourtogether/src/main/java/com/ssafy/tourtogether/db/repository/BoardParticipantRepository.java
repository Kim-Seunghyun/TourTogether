package com.ssafy.tourtogether.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.tourtogether.db.entity.BoardParticipant;

@Repository
public interface BoardParticipantRepository extends JpaRepository<BoardParticipant, Integer> {

}
