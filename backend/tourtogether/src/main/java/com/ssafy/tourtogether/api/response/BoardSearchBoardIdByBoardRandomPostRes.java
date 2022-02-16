package com.ssafy.tourtogether.api.response;

import com.ssafy.tourtogether.common.model.response.BaseResponseBody;
import com.ssafy.tourtogether.db.entity.Board;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

/**
 * 보드 생성하기 API ([POST] /board/create) 요청에 대한 응답값 정리
 */

@Getter
@Setter
@ApiModel("BoardSearchBoardIdByBoardRandomPostRes")
public class BoardSearchBoardIdByBoardRandomPostRes extends BaseResponseBody {
	Board board;

	public static BoardSearchBoardIdByBoardRandomPostRes of(Integer statusCode, String message, Board board) {
		BoardSearchBoardIdByBoardRandomPostRes res = new BoardSearchBoardIdByBoardRandomPostRes();
		res.setStatusCode(statusCode);
		res.setMessage(message);
		res.setBoard(board);
		return res;
	}
}
