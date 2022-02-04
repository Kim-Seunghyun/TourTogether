package com.ssafy.tourtogether.db.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.tourtogether.db.entity.Board;
import com.ssafy.tourtogether.db.entity.QBoard;

@Repository
public class BoardRepositorySupport {

	@Autowired
	private JPAQueryFactory jpaQueryFactory;
	QBoard qBoard= QBoard.board;
	
	public List<Board> findAll() {
		List<Board> boardList = jpaQueryFactory.select(qBoard).from(qBoard).fetch(); 
		return boardList;
	}
	
	// 사용자가 참여한 보드 리스트 보여주기(수정해야함)
	public List<Board> findByUserId() {
		List<Board> boardList = jpaQueryFactory.select(qBoard).from(qBoard).fetch();
		return boardList;
	}
	
	// 보드 삭제
	public String deleteBoard() {
		// 쿼리문 작성
		return "success";
	}
}