package com.ssafy.tourtogether.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 보드 삭제 API ([DELETE] /board/{boardId}) 요청에 필요한 리퀘스트 바디 정의.
 */

@Getter
@Setter
@ApiModel("BoardDeleteDeleteRequest")
public class BoardDeleteDeleteReq {
	// user 정보를 가져온 후 자격이 되는 사람만 가능
	
	@ApiModelProperty(name="user 아이디", example = "user1")
	String userClientId;
	@ApiModelProperty(name="user 가입 플랫폼", example = "1")
	int platformNum;
}
