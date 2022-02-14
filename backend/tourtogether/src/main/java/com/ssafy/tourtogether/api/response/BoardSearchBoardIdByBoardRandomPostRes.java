package com.ssafy.tourtogether.api.response;

import com.ssafy.tourtogether.common.model.response.BaseResponseBody;

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
	int boardId;

	public static BoardSearchBoardIdByBoardRandomPostRes of(Integer statusCode, String message, int boardId) {
		BoardSearchBoardIdByBoardRandomPostRes res = new BoardSearchBoardIdByBoardRandomPostRes();
		res.setStatusCode(statusCode);
		res.setMessage(message);
		res.setBoardId(boardId);
		return res;
	}
}
