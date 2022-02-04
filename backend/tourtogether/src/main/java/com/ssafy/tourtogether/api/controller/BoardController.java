package com.ssafy.tourtogether.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.tourtogether.api.request.BoardCreatePostReq;
import com.ssafy.tourtogether.api.service.BoardService;
import com.ssafy.tourtogether.common.model.response.BaseResponseBody;
import com.ssafy.tourtogether.db.entity.Board;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 보드 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "게시판 API", tags = { "board" })
@RestController
@RequestMapping("/board")
public class BoardController {
	@Autowired
	BoardService boardService;
	
	@PostMapping("/create")
	@ApiOperation(value = "보드생성", notes = "새로운 보드를 생성한다")
	@ApiResponses({
//			 TODO 필요한 response 종류와 메시지 작성
        @ApiResponse(code = 200, message = "성공", response = BaseResponseBody.class),
        @ApiResponse(code = 401, message = "인증 실패", response = BaseResponseBody.class),
        @ApiResponse(code = 404, message = "보드 없음", response = BaseResponseBody.class),
        @ApiResponse(code = 500, message = "서버 오류", response = BaseResponseBody.class)
	})
	
	public ResponseEntity<? extends BaseResponseBody> create(
			@RequestBody @ApiParam(value="보드 생성 정보", required = true) BoardCreatePostReq boardCreateInfo) {
		
		//임의로 리턴된 User 인스턴스. 현재 코드는 회원 가입 성공 여부만 판단하기 때문에 굳이 Insert 된 유저 정보를 응답하지 않음.
		Board board = boardService.createBoard(boardCreateInfo);
		
		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
	}

}
