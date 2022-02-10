package com.ssafy.tourtogether.api.response;

import java.util.List;

import com.ssafy.tourtogether.common.model.response.BaseResponseBody;
import com.ssafy.tourtogether.db.entity.Board;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

/**
 * 유형별로 보드 불러오기 API ([GET] /board/searchByCategory) 요청에 대한 응답값 정리
 */

@Getter
@Setter
@ApiModel("BoardSearchByCategoryGetRes")
public class BoardSearchByCategoryGetRes extends BaseResponseBody {
	List<Board> boards = null;

	public static BoardSearchByCategoryGetRes of(Integer statusCode, String message, List<Board> boards) {
		BoardSearchByCategoryGetRes res = new BoardSearchByCategoryGetRes();
		res.setStatusCode(statusCode);
		res.setMessage(message);
		res.setBoards(boards);
		return res;
	}
}
