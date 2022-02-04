package com.ssafy.tourtogether.api.response;

import com.ssafy.tourtogether.common.model.response.BaseResponseBody;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

/**
 * 보드 삭제 API ([DELETE] /board/{boardId}) 요청에 대한 응답값 정의
 */

@Getter
@Setter
@ApiModel("BoardDeleteDeleteResponse")
public class BoardDeleteDeleteRes extends BaseResponseBody{
	// 없는거같기도... 삭제할까
}
