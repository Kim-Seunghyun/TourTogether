package com.ssafy.tourtogether.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.tourtogether.db.entity.BoardLikes;

public interface BoardLikesRepository extends JpaRepository<BoardLikes, Integer> {

}
