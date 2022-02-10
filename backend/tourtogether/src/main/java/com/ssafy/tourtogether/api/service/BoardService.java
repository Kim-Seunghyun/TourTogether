package com.ssafy.tourtogether.api.service;

import java.util.List;
import java.util.Optional;

import com.ssafy.tourtogether.api.request.BoardAddParticipantPostReq;
import com.ssafy.tourtogether.api.request.BoardCategoryPostReq;
import com.ssafy.tourtogether.api.request.BoardClickBoardLikePatchReq;
import com.ssafy.tourtogether.api.request.BoardCreatePostReq;
import com.ssafy.tourtogether.api.request.BoardDeleteDeleteReq;
import com.ssafy.tourtogether.api.request.BoardFinishPatchReq;
import com.ssafy.tourtogether.api.request.BoardSearchByBoardIdPostReq;
import com.ssafy.tourtogether.api.request.BoardSearchByCategoryPostReq;
import com.ssafy.tourtogether.api.request.BoardSearchByUserIdPostReq;
import com.ssafy.tourtogether.db.entity.Board;

public interface BoardService {

	void createBoard(BoardCreatePostReq boardCreateInfo);

	void deleteBoard(BoardDeleteDeleteReq boardDeleteInfo);

	void finishBoard(BoardFinishPatchReq boardFinishInfo);

	void addParticipant(BoardAddParticipantPostReq boardAddParticipantInfo);

	List<Board> searchAll();

	Optional<Board> searchByBoardId(BoardSearchByBoardIdPostReq boardSearchByBoardIdInfo);

	List<Board> searchByUserId(BoardSearchByUserIdPostReq boardSearchByUserIdInfo);

	void clickBoardLike(BoardClickBoardLikePatchReq boardclickBoardLikeInfo);

	void cancelBoardLike(BoardClickBoardLikePatchReq boardclickBoardLikeInfo);

	List<Board> searchLikeBoardByUserId(BoardSearchByUserIdPostReq boardSearchByUserIdInfo);

	void category(BoardCategoryPostReq boardCategoryInfo);

	List<Board> searchByCategory(BoardSearchByCategoryPostReq boardSearchByCategoryInfo);

}