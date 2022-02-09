package com.ssafy.tourtogether.db.repository;

import java.util.LinkedList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.tourtogether.api.request.BoardDeleteDeleteReq;
import com.ssafy.tourtogether.api.request.BoardFinishPatchReq;
import com.ssafy.tourtogether.api.request.BoardSearchByUserIdGetReq;
import com.ssafy.tourtogether.db.entity.Board;
import com.ssafy.tourtogether.db.entity.QBoard;
import com.ssafy.tourtogether.db.entity.QBoardLikes;
import com.ssafy.tourtogether.db.entity.QBoardParticipant;

@Repository
public class BoardRepositorySupport {

	@Autowired
	private JPAQueryFactory jpaQueryFactory;
	QBoard qBoard = QBoard.board;
	QBoardParticipant qBoardParticipant = QBoardParticipant.boardParticipant;
	QBoardLikes qBoardLikes = QBoardLikes.boardLikes;

	@Transactional
	public void delete(BoardDeleteDeleteReq boardDeleteInfo) {
		jpaQueryFactory.delete(qBoard).where(qBoard.boardId.eq(boardDeleteInfo.getBoardId())).execute();
	}

	public List<Board> findByUserId(BoardSearchByUserIdGetReq boardSearchByUserIdInfo) {
		List<Integer> myBoardIds = jpaQueryFactory.select(qBoardParticipant.boardParticipantBoardId)
				.from(qBoardParticipant)
				.where(qBoardParticipant.boardParticipantUserId.eq(boardSearchByUserIdInfo.getUserId())).fetch();

		List<Board> boardList = new LinkedList<Board>();

		for (int boardId : myBoardIds) {
			Board board = jpaQueryFactory.select(qBoard).from(qBoard).where(qBoard.boardId.eq(boardId)).fetchFirst();
			boardList.add(board);
		}

		return boardList;
	}

	@Transactional
	public void increaseLike(int boardId) {
		int curLikes = jpaQueryFactory.select(qBoard.boardLikesCount).from(qBoard).where(qBoard.boardId.eq(boardId))
				.fetchFirst();
		jpaQueryFactory.update(qBoard).where(qBoard.boardId.eq(boardId)).set(qBoard.boardLikesCount, curLikes + 1)
				.execute();
	}

	@Transactional
	public void decreaseLike(int boardId) {
		int curLikes = jpaQueryFactory.select(qBoard.boardLikesCount).from(qBoard).where(qBoard.boardId.eq(boardId))
				.fetchFirst();
		jpaQueryFactory.update(qBoard).where(qBoard.boardId.eq(boardId)).set(qBoard.boardLikesCount, curLikes - 1)
				.execute();
	}

	public List<Board> findLikeBoardByUserId(BoardSearchByUserIdGetReq boardSearchByUserIdInfo) {
		List<Integer> myLikeBoardIds = jpaQueryFactory.select(qBoardLikes.boardLikesBoardId).from(qBoardLikes)
				.where(qBoardLikes.boardLikesUserId.eq(boardSearchByUserIdInfo.getUserId())).fetch();

		List<Board> myLikeBoards = new LinkedList<Board>();

		for (int boardId : myLikeBoardIds) {
			Board board = jpaQueryFactory.select(qBoard).from(qBoard).where(qBoard.boardId.eq(boardId)).fetchFirst();
			myLikeBoards.add(board);
		}
		return myLikeBoards;
	}

	@Transactional
	public void updateFinish(BoardFinishPatchReq boardFinishInfo) {
		jpaQueryFactory.update(qBoard).where(qBoard.boardId.eq(boardFinishInfo.getBoardId()))
				.set(qBoard.boardIsActive, true).execute();
	}
}