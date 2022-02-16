package com.ssafy.tourtogether.db.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.tourtogether.api.request.BoardSearchParticipantPostReq;
import com.ssafy.tourtogether.db.entity.BoardParticipant;
import com.ssafy.tourtogether.db.entity.QBoardParticipant;

@Repository
public class BoardParticipantRepositorySupport {
	@Autowired
	private JPAQueryFactory jpaQueryFactory;
	QBoardParticipant qBoardParticipant = QBoardParticipant.boardParticipant;

	public Boolean findByUserId(BoardSearchParticipantPostReq boardSearchParticipantInfo) {
		BoardParticipant boardParticipant = jpaQueryFactory.select(qBoardParticipant).from(qBoardParticipant)
				.where(qBoardParticipant.boardParticipantBoardId.eq(boardSearchParticipantInfo.getBoardId()))
				.where(qBoardParticipant.boardParticipantUserId.eq(boardSearchParticipantInfo.getUserId()))
				.fetchFirst();
		if (boardParticipant != null) {
			return true;
		} else {
			return false;
		}
	}

//	@Transactional
//	public void delete(BoardDeleteDeleteReq boardDeleteInfo) {
//		System.out.println(boardDeleteInfo.getBoardId());
//
//		jpaQueryFactory.delete(qBoardParticipant)
//				.where(qBoardParticipant.boardParticipantBoardId.eq(boardDeleteInfo.getBoardId())).execute();
//	}
}
