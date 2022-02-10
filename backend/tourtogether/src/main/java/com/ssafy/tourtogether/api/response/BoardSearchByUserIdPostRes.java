package com.ssafy.tourtogether.api.response;

import java.util.List;

import com.ssafy.tourtogether.common.model.response.BaseResponseBody;
import com.ssafy.tourtogether.db.entity.Board;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

/**
 * 유저 ID로 보드 불러오기 API ([GET] /board/searchByUserID) 요청에 대한 응답값 정리
 */

@Getter
@Setter
@ApiModel("BoardSearchByUserIdRes")
public class BoardSearchByUserIdGetRes extends BaseResponseBody {
	List<Board> myBoards = null;

	public static BoardSearchByUserIdGetRes of(Integer statusCode, String message, List<Board> myBoards) {
		BoardSearchByUserIdGetRes res = new BoardSearchByUserIdGetRes();
		res.setStatusCode(statusCode);
		res.setMessage(message);
		res.setMyBoards(myBoards);
		return res;
	}
}
