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
@ApiModel("BoardCreatePostRes")
public class BoardCreatePostRes extends BaseResponseBody {
	String boardRandom;

	public static BoardCreatePostRes of(Integer statusCode, String message, String boardRandom) {
		BoardCreatePostRes res = new BoardCreatePostRes();
		res.setStatusCode(statusCode);
		res.setMessage(message);
		res.setBoardRandom(boardRandom);
		return res;
	}
}
