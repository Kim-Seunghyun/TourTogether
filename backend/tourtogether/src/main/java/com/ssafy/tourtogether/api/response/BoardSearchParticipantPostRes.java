
package com.ssafy.tourtogether.api.response;

import com.ssafy.tourtogether.common.model.response.BaseResponseBody;

import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 보드에 참여한 참가자인지 확인 API ([POST] /board/searchPariticipant) 요청에 대한 응답값 정의.
 */

@Getter
@Setter
@ApiModel("BoardSearchParticipantPostRes")
public class BoardSearchParticipantPostRes extends BaseResponseBody {
	boolean isIncluded;

	public static BoardSearchParticipantPostRes of(Integer statusCode, String message, Boolean isIncluded) {
		BoardSearchParticipantPostRes res = new BoardSearchParticipantPostRes();
		res.setStatusCode(statusCode);
		res.setMessage(message);
		res.setIncluded(isIncluded);
		return res;
	}
}
