package com.ssafy.tourtogether.api.response;

import java.util.Optional;

import com.ssafy.tourtogether.common.model.response.BaseResponseBody;
import com.ssafy.tourtogether.db.entity.Board;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

/**
 * 보드 ID로 보드 불러오기 API ([GET] /board/searchByBoardID) 요청에 대한 응답값 정리
 */

@Getter
@Setter
@ApiModel("BoardSearchByBoardIdRes")
public class BoardSearchByBoardIdPostRes extends BaseResponseBody {
	Optional<Board> board = null;

	public static BoardSearchByBoardIdPostRes of(Integer statusCode, String message, Optional<Board> board) {
		BoardSearchByBoardIdPostRes res = new BoardSearchByBoardIdPostRes();
		res.setStatusCode(statusCode);
		res.setMessage(message);
		res.setBoard(board);
		return res;
	}
}
