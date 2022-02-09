package com.ssafy.tourtogether.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.tourtogether.db.entity.Board;

// 보드 모델 관련 디비 쿼리 생성을 위한 JPA Query Method 인터페이스 정의
public interface BoardRepository extends JpaRepository<Board, Integer> {

	List<Board> findAll();

}