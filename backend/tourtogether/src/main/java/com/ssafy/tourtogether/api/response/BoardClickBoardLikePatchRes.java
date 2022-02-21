package com.ssafy.tourtogether.api.response;

import java.util.LinkedList;
import java.util.List;

import com.ssafy.tourtogether.common.model.response.BaseResponseBody;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

/**
 * 보드 좋아요 누르기 API ([POST] /board/clickLike,cancelLike) 요청에 대한 응답값 정리
 */

@Getter
@Setter
@ApiModel("BoardClickBoardLikePatchRes")
public class BoardClickBoardLikePatchRes extends BaseResponseBody {
	List<Integer> favoriteBoardId = new LinkedList<Integer>();

	public static BoardClickBoardLikePatchRes of(Integer statusCode, String message, List<Integer> favoriteBoardId) {
		BoardClickBoardLikePatchRes res = new BoardClickBoardLikePatchRes();
		res.setStatusCode(statusCode);
		res.setMessage(message);
		res.setFavoriteBoardId(favoriteBoardId);
		return res;
	}
}
