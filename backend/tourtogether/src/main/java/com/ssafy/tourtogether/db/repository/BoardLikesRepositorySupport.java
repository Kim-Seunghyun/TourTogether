package com.ssafy.tourtogether.db.repository;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.tourtogether.api.request.BoardClickBoardLikePatchReq;
import com.ssafy.tourtogether.db.entity.QBoardLikes;

@Repository
public class BoardLikesRepositorySupport {
	@Autowired
	private JPAQueryFactory jpaQueryFactory;
	QBoardLikes qBoardLikes = QBoardLikes.boardLikes;

	@Transactional
	public void deleteByBoardId(BoardClickBoardLikePatchReq boardclickBoardLikeInfo) {
		jpaQueryFactory.delete(qBoardLikes)
				.where(qBoardLikes.boardLikesBoardId.eq(boardclickBoardLikeInfo.getBoardId()))
				.where(qBoardLikes.boardLikesUserId.eq(boardclickBoardLikeInfo.getUserId())).execute();
	}

}
