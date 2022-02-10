package com.ssafy.tourtogether.api.service;

import java.util.List;
import java.util.Optional;

import com.ssafy.tourtogether.api.request.BoardAddParticipantPostReq;
import com.ssafy.tourtogether.api.request.BoardCategoryPostReq;
import com.ssafy.tourtogether.api.request.BoardClickBoardLikePatchReq;
import com.ssafy.tourtogether.api.request.BoardCreatePostReq;
import com.ssafy.tourtogether.api.request.BoardDeleteDeleteReq;
import com.ssafy.tourtogether.api.request.BoardFinishPatchReq;
import com.ssafy.tourtogether.api.request.BoardSearchByBoardIdGetReq;
import com.ssafy.tourtogether.api.request.BoardSearchByCategoryGetReq;
import com.ssafy.tourtogether.api.request.BoardSearchByUserIdGetReq;
import com.ssafy.tourtogether.db.entity.Board;

public interface BoardService {

	void createBoard(BoardCreatePostReq boardCreateInfo);

	void deleteBoard(BoardDeleteDeleteReq boardDeleteInfo);

	void finishBoard(BoardFinishPatchReq boardFinishInfo);

	void addParticipant(BoardAddParticipantPostReq boardAddParticipantInfo);

	List<Board> searchAll();

	Optional<Board> searchByBoardId(BoardSearchByBoardIdGetReq boardSearchByBoardIdInfo);

	List<Board> searchByUserId(BoardSearchByUserIdGetReq boardSearchByUserIdInfo);

	void clickBoardLike(BoardClickBoardLikePatchReq boardclickBoardLikeInfo);

	void cancelBoardLike(BoardClickBoardLikePatchReq boardclickBoardLikeInfo);

	List<Board> searchLikeBoardByUserId(BoardSearchByUserIdGetReq boardSearchByUserIdInfo);

	void category(BoardCategoryPostReq boardCategoryInfo);

	List<Board> searchByCategory(BoardSearchByCategoryGetReq boardSearchByCategoryInfo);

}