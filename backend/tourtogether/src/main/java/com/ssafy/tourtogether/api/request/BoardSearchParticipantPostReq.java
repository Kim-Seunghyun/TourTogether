
package com.ssafy.tourtogether.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 보드에 참여한 참가자인지 확인 API ([POST] /board/searchParticipant) 요청에 필요한 리퀘스트 바디 정의.
 */

@Getter
@Setter
@ApiModel("BoardSearchParticipantPostReq")
public class BoardSearchParticipantPostReq {

	@ApiModelProperty(name = "보드 아이디", example = "보드 아이디")
	int boardId;

	@ApiModelProperty(name = "유저 아이디", example = "유저 아이디")
	int userId;
}