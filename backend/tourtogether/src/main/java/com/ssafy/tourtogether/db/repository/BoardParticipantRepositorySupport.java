package com.ssafy.tourtogether.db.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.tourtogether.db.entity.QBoardParticipant;

@Repository
public class BoardParticipantRepositorySupport {
	@Autowired
	private JPAQueryFactory jpaQueryFactory;
	QBoardParticipant qBoardParticipant = QBoardParticipant.boardParticipant;

//	@Transactional
//	public void delete(BoardDeleteDeleteReq boardDeleteInfo) {
//		System.out.println(boardDeleteInfo.getBoardId());
//
//		jpaQueryFactory.delete(qBoardParticipant)
//				.where(qBoardParticipant.boardParticipantBoardId.eq(boardDeleteInfo.getBoardId())).execute();
//	}
}
