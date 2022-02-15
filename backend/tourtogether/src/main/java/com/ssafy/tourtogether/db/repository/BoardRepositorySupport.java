package com.ssafy.tourtogether.db.repository;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.tourtogether.api.request.BoardClickBoardLikePatchReq;
import com.ssafy.tourtogether.api.request.BoardDeleteDeleteReq;
import com.ssafy.tourtogether.api.request.BoardSearchBoardIdByBoardRandomPostReq;
import com.ssafy.tourtogether.api.request.BoardSearchByCategoryPostReq;
import com.ssafy.tourtogether.api.request.BoardSearchByUserIdPostReq;
import com.ssafy.tourtogether.db.entity.Board;
import com.ssafy.tourtogether.db.entity.QBoard;
import com.ssafy.tourtogether.db.entity.QBoardLikes;
import com.ssafy.tourtogether.db.entity.QBoardParticipant;
import com.ssafy.tourtogether.db.entity.QCategory;

@Repository
public class BoardRepositorySupport {

	@Autowired
	private JPAQueryFactory jpaQueryFactory;
	QBoard qBoard = QBoard.board;
	QBoardParticipant qBoardParticipant = QBoardParticipant.boardParticipant;
	QBoardLikes qBoardLikes = QBoardLikes.boardLikes;
	QCategory qCategory = QCategory.category;

	@Transactional
	public void delete(BoardDeleteDeleteReq boardDeleteInfo) {
		jpaQueryFactory.delete(qBoard).where(qBoard.boardId.eq(boardDeleteInfo.getBoardId())).execute();
	}

	@Transactional
	public List<Integer> increaseLike(BoardClickBoardLikePatchReq boardclickBoardLikeInfo) {
		int curLikes = jpaQueryFactory.select(qBoard.boardLikesCount).from(qBoard)
				.where(qBoard.boardId.eq(boardclickBoardLikeInfo.getBoardId())).fetchFirst();
		jpaQueryFactory.update(qBoard).where(qBoard.boardId.eq(boardclickBoardLikeInfo.getBoardId()))
				.set(qBoard.boardLikesCount, curLikes + 1).execute();
		List<Integer> favoritesBoardId = jpaQueryFactory.select(qBoardLikes.boardLikesBoardId).from(qBoardLikes)
				.where(qBoardLikes.boardLikesUserId.eq(boardclickBoardLikeInfo.getUserId())).fetch();
		return favoritesBoardId;
	}

	@Transactional
	public List<Integer> decreaseLike(BoardClickBoardLikePatchReq boardclickBoardLikeInfo) {
		int curLikes = jpaQueryFactory.select(qBoard.boardLikesCount).from(qBoard)
				.where(qBoard.boardId.eq(boardclickBoardLikeInfo.getBoardId())).fetchFirst();
		jpaQueryFactory.update(qBoard).where(qBoard.boardId.eq(boardclickBoardLikeInfo.getBoardId()))
				.set(qBoard.boardLikesCount, curLikes - 1).execute();
		List<Integer> favoritesBoardId = jpaQueryFactory.select(qBoardLikes.boardLikesBoardId).from(qBoardLikes)
				.where(qBoardLikes.boardLikesUserId.eq(boardclickBoardLikeInfo.getUserId())).fetch();
		return favoritesBoardId;
	}

	public List<Board> findLikeBoardByUserId(BoardSearchByUserIdPostReq boardSearchByUserIdInfo) {
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
	public void updateFinish(int boardId) {
		jpaQueryFactory.update(qBoard).where(qBoard.boardId.eq(boardId))
				.set(qBoard.boardIsActive, true).execute();
	}

	public List<Board> findAll() {
		List<Board> boards = jpaQueryFactory.select(qBoard).from(qBoard).where(qBoard.boardIsActive.eq(true))
				.orderBy(qBoard.boardLikesCount.desc()).fetch();
		return boards;
	}

	public List<Board> findByCategory(BoardSearchByCategoryPostReq boardSearchByCategoryInfo) {

		String withWhom, season, area, theme;
		// 값이 0이면 선택되지 않은 것.
		if (boardSearchByCategoryInfo.getCategoryWithWhom() == 0) {
			withWhom = "%";
		} else {
			withWhom = boardSearchByCategoryInfo.getCategoryWithWhom() + "";
		}

		if (boardSearchByCategoryInfo.getCategorySeason() == 0) {
			season = "%";
		} else {
			season = boardSearchByCategoryInfo.getCategorySeason() + "";
		}

		if (boardSearchByCategoryInfo.getCategoryArea() == 0) {
			area = "%";
		} else {
			area = boardSearchByCategoryInfo.getCategoryArea() + "";
		}

		if (boardSearchByCategoryInfo.getCategoryTheme() == 0) {
			theme = "%";
			;
		} else {
			theme = boardSearchByCategoryInfo.getCategoryTheme() + "";
		}

		List<Integer> boardIds = jpaQueryFactory.select(qCategory.categoryBoardId).from(qCategory)
				.where(qCategory.categoryWithWhom.like(withWhom)).where(qCategory.categorySeason.like(season))
				.where(qCategory.categoryArea.like(area)).where(qCategory.categoryTheme.like(theme)).fetch();

		List<Board> boards = new LinkedList<Board>();
		for (int boardId : boardIds) {
			Board board = jpaQueryFactory.select(qBoard).from(qBoard).where(qBoard.boardId.eq(boardId)).fetchFirst();
			boards.add(board);
		}

		Collections.sort(boards, new Comparator<Board>() {
			@Override
			public int compare(Board o1, Board o2) {
				return o2.getBoardLikesCount() - o1.getBoardLikesCount();
			}
		});

		return boards;
	}

	public boolean duplicationCheck(String boardRandom) {
		List<Board> boards = jpaQueryFactory.select(qBoard).from(qBoard).where(qBoard.boardRandom.eq(boardRandom))
				.fetch();
		if (boards.size() == 0)
			return true;
		else
			return false;

	}

	public Board findBoardIdByBoardRandom(BoardSearchBoardIdByBoardRandomPostReq searchBoardIdByBoardRandomInfo) {
		Board board = jpaQueryFactory.select(qBoard).from(qBoard)
				.where(qBoard.boardRandom.eq(searchBoardIdByBoardRandomInfo.getBoardRandom())).fetchFirst();
		return board;
	}

	public List<Board> findByUserId(BoardSearchByUserIdPostReq boardSearchByUserIdInfo) {
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

	public List<Board> findByUserIdFinish(BoardSearchByUserIdPostReq boardSearchByUserIdInfo) {
		List<Integer> myBoardIds = jpaQueryFactory.select(qBoardParticipant.boardParticipantBoardId)
				.from(qBoardParticipant)
				.where(qBoardParticipant.boardParticipantUserId.eq(boardSearchByUserIdInfo.getUserId())).fetch();

		List<Board> boardList = new LinkedList<Board>();

		for (int boardId : myBoardIds) {
			Board board = jpaQueryFactory.select(qBoard).from(qBoard).where(qBoard.boardId.eq(boardId))
					.where(qBoard.boardIsActive.eq(true)).fetchFirst();
			if (board != null)
				boardList.add(board);
		}

		return boardList;
	}

	public List<Board> findByUserIdProceeding(BoardSearchByUserIdPostReq boardSearchByUserIdInfo) {
		List<Integer> myBoardIds = jpaQueryFactory.select(qBoardParticipant.boardParticipantBoardId)
				.from(qBoardParticipant)
				.where(qBoardParticipant.boardParticipantUserId.eq(boardSearchByUserIdInfo.getUserId())).fetch();

		List<Board> boardList = new LinkedList<Board>();

		for (int boardId : myBoardIds) {
			Board board = jpaQueryFactory.select(qBoard).from(qBoard).where(qBoard.boardId.eq(boardId))
					.where(qBoard.boardIsActive.eq(false)).fetchFirst();
			if (board != null)
				boardList.add(board);
		}

		return boardList;
	}
}