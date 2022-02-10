package com.ssafy.tourtogether.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.tourtogether.api.request.BoardAddParticipantPostReq;
import com.ssafy.tourtogether.api.request.BoardCategoryPostReq;
import com.ssafy.tourtogether.api.request.BoardClickBoardLikePatchReq;
import com.ssafy.tourtogether.api.request.BoardCreatePostReq;
import com.ssafy.tourtogether.api.request.BoardDeleteDeleteReq;
import com.ssafy.tourtogether.api.request.BoardFinishPatchReq;
import com.ssafy.tourtogether.api.request.BoardSearchByBoardIdPostReq;
import com.ssafy.tourtogether.api.request.BoardSearchByCategoryPostReq;
import com.ssafy.tourtogether.api.request.BoardSearchByUserIdPostReq;
import com.ssafy.tourtogether.api.response.BoardSearchAllPostRes;
import com.ssafy.tourtogether.api.response.BoardSearchByBoardIdPostRes;
import com.ssafy.tourtogether.api.response.BoardSearchByUserIdPostRes;
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
	@ApiResponses({ @ApiResponse(code = 200, message = "성공", response = BaseResponseBody.class),
			@ApiResponse(code = 401, message = "인증 실패", response = BaseResponseBody.class),
			@ApiResponse(code = 404, message = "보드 없음", response = BaseResponseBody.class),
			@ApiResponse(code = 500, message = "서버 오류", response = BaseResponseBody.class) })

	public ResponseEntity<? extends BaseResponseBody> create(
			@RequestBody @ApiParam(value = "보드 생성 정보", required = true) BoardCreatePostReq boardCreateInfo) {

		// 보드 생성
		boardService.createBoard(boardCreateInfo);
		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
	}

	@PatchMapping("/finish")
	@ApiOperation(value = "보드완료", notes = "보드를 완료한다")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공", response = BaseResponseBody.class),
			@ApiResponse(code = 401, message = "인증 실패", response = BaseResponseBody.class),
			@ApiResponse(code = 404, message = "보드 없음", response = BaseResponseBody.class),
			@ApiResponse(code = 500, message = "서버 오류", response = BaseResponseBody.class) })

	public ResponseEntity<? extends BaseResponseBody> finish(
			@RequestBody @ApiParam(value = "보드 완료 정보", required = true) BoardFinishPatchReq boardFinishInfo) {

		// 보드 생성
		boardService.finishBoard(boardFinishInfo);
		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
	}

	@DeleteMapping("/delete")
	@ApiOperation(value = "보드삭제", notes = "보드를 삭제한다")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공", response = BaseResponseBody.class),
			@ApiResponse(code = 401, message = "인증 실패", response = BaseResponseBody.class),
			@ApiResponse(code = 404, message = "보드 없음", response = BaseResponseBody.class),
			@ApiResponse(code = 500, message = "서버 오류", response = BaseResponseBody.class) })

	public ResponseEntity<? extends BaseResponseBody> delete(
			@RequestBody @ApiParam(value = "보드 삭제 정보", required = true) BoardDeleteDeleteReq boardDeleteInfo) {

		// 보드 삭제
		boardService.deleteBoard(boardDeleteInfo);
		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
	}

	@PostMapping("/addParticipant")
	@ApiOperation(value = "보드에 참가자 추가하기", notes = "보드에 새로운 참가차를 추가한다")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공", response = BaseResponseBody.class),
			@ApiResponse(code = 401, message = "인증 실패", response = BaseResponseBody.class),
			@ApiResponse(code = 404, message = "보드 없음", response = BaseResponseBody.class),
			@ApiResponse(code = 500, message = "서버 오류", response = BaseResponseBody.class) })

	public ResponseEntity<? extends BaseResponseBody> addParticipant(
			@RequestBody @ApiParam(value = "추가할 유저의 아이디와 보드 아이 정보", required = true) BoardAddParticipantPostReq boardAddParticipantInfo) {

		// 보드 생성
		boardService.addParticipant(boardAddParticipantInfo);
		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
	}

	@GetMapping
	@ApiOperation(value = "모든 보드 중 완료된 보드만 가져오기", notes = "모든 보드 가져온다")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공", response = BaseResponseBody.class),
			@ApiResponse(code = 401, message = "인증 실패", response = BaseResponseBody.class),
			@ApiResponse(code = 404, message = "보드 없음", response = BaseResponseBody.class),
			@ApiResponse(code = 500, message = "서버 오류", response = BaseResponseBody.class) })

	public ResponseEntity<? extends BaseResponseBody> searchAll() {

		// 보드 ID로 불러오기
		List<Board> boards = boardService.searchAll();
		return ResponseEntity.status(200).body(BoardSearchAllPostRes.of(200, "Success", boards));
	}

	@PostMapping("/searchByBoardId")
	@ApiOperation(value = "보드 ID로 보드 가져오기", notes = "보드 ID로 보드 가져온다")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공", response = BaseResponseBody.class),
			@ApiResponse(code = 401, message = "인증 실패", response = BaseResponseBody.class),
			@ApiResponse(code = 404, message = "보드 없음", response = BaseResponseBody.class),
			@ApiResponse(code = 500, message = "서버 오류", response = BaseResponseBody.class) })

	public ResponseEntity<? extends BaseResponseBody> searchByBoardId(
			@RequestBody @ApiParam(value = "불러올 보드 ID", required = true) BoardSearchByBoardIdPostReq boardSearchByBoardIdInfo) {

		// 보드 ID로 불러오기
		Optional<Board> board = boardService.searchByBoardId(boardSearchByBoardIdInfo);
		return ResponseEntity.status(200).body(BoardSearchByBoardIdPostRes.of(200, "Success", board));
	}

	@PostMapping("/searchByUserId")
	@ApiOperation(value = "유저 ID로 모든 보드 가져오기", notes = "유저 ID로 모든 보드 가져온다")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공", response = BaseResponseBody.class),
			@ApiResponse(code = 401, message = "인증 실패", response = BaseResponseBody.class),
			@ApiResponse(code = 404, message = "보드 없음", response = BaseResponseBody.class),
			@ApiResponse(code = 500, message = "서버 오류", response = BaseResponseBody.class) })

	public ResponseEntity<? extends BaseResponseBody> searchByUserId(
			@RequestBody @ApiParam(value = "불러올 보드 ID", required = true) BoardSearchByUserIdPostReq boardSearchByUserIdInfo) {

		// 보드 ID로 불러오기
		List<Board> myBoards = boardService.searchByUserId(boardSearchByUserIdInfo);
		return ResponseEntity.status(200).body(BoardSearchByUserIdPostRes.of(200, "Success", myBoards));
	}

	@PostMapping("/searchLikeBoardByUserId")
	@ApiOperation(value = "유저 ID로 좋아요 누른 모든 보드 가져오기", notes = "유저 ID로 좋아요 누른 모든 보드 가져온다")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공", response = BaseResponseBody.class),
			@ApiResponse(code = 401, message = "인증 실패", response = BaseResponseBody.class),
			@ApiResponse(code = 404, message = "보드 없음", response = BaseResponseBody.class),
			@ApiResponse(code = 500, message = "서버 오류", response = BaseResponseBody.class) })

	public ResponseEntity<? extends BaseResponseBody> searchLikeBoardByUserId(
			@RequestBody @ApiParam(value = "불러올 보드 ID", required = true) BoardSearchByUserIdPostReq boardSearchByUserIdInfo) {

		// 보드 ID로 불러오기
		List<Board> myBoards = boardService.searchLikeBoardByUserId(boardSearchByUserIdInfo);
		return ResponseEntity.status(200).body(BoardSearchByUserIdPostRes.of(200, "Success", myBoards));
	}

	@PatchMapping("/clickBoardLike")
	@ApiOperation(value = "보드에 좋아요 누르기", notes = "보드에 좋아요를 누른다")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공", response = BaseResponseBody.class),
			@ApiResponse(code = 401, message = "인증 실패", response = BaseResponseBody.class),
			@ApiResponse(code = 404, message = "보드 없음", response = BaseResponseBody.class),
			@ApiResponse(code = 500, message = "서버 오류", response = BaseResponseBody.class) })

	public ResponseEntity<? extends BaseResponseBody> clickBoardLike(
			@RequestBody @ApiParam(value = "좋아요 누른 보드 아이디와 유저 아이디 정보", required = true) BoardClickBoardLikePatchReq boardclickBoardLikeInfo) {

		// 보드 생성
		boardService.clickBoardLike(boardclickBoardLikeInfo);
		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
	}

	@PatchMapping("/cancelBoardLike")
	@ApiOperation(value = "보드에 좋아요 취소하기", notes = "보드에 좋아요를 취소한다")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공", response = BaseResponseBody.class),
			@ApiResponse(code = 401, message = "인증 실패", response = BaseResponseBody.class),
			@ApiResponse(code = 404, message = "보드 없음", response = BaseResponseBody.class),
			@ApiResponse(code = 500, message = "서버 오류", response = BaseResponseBody.class) })

	public ResponseEntity<? extends BaseResponseBody> cancelBoardLike(
			@RequestBody @ApiParam(value = "좋아요 취소할 보드 아이디와 유저 아이디 정보", required = true) BoardClickBoardLikePatchReq boardclickBoardLikeInfo) {

		// 보드 생성
		boardService.cancelBoardLike(boardclickBoardLikeInfo);
		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
	}

	@PostMapping("/category")
	@ApiOperation(value = "보드에 유형 설정하기", notes = "보드에 유형 설정한다")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공", response = BaseResponseBody.class),
			@ApiResponse(code = 401, message = "인증 실패", response = BaseResponseBody.class),
			@ApiResponse(code = 404, message = "보드 없음", response = BaseResponseBody.class),
			@ApiResponse(code = 500, message = "서버 오류", response = BaseResponseBody.class) })

	public ResponseEntity<? extends BaseResponseBody> category(
			@RequestBody @ApiParam(value = "추가할 유저의 아이디와 보드 아이 정보", required = true) BoardCategoryPostReq boardCategoryInfo) {

		// 보드 생성
		boardService.category(boardCategoryInfo);
		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
	}

	@PostMapping("/searchByCategory")
	@ApiOperation(value = "선택된 유형에 해당되는 보드만 가져오기", notes = "선택된 유형에 해당되는 보드 가져온다")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공", response = BaseResponseBody.class),
			@ApiResponse(code = 401, message = "인증 실패", response = BaseResponseBody.class),
			@ApiResponse(code = 404, message = "보드 없음", response = BaseResponseBody.class),
			@ApiResponse(code = 500, message = "서버 오류", response = BaseResponseBody.class) })

	public ResponseEntity<? extends BaseResponseBody> searchByCategory(
			@RequestBody @ApiParam(value = "추가할 유저의 아이디와 보드 아이 정보", required = true) BoardSearchByCategoryPostReq boardSearchByCategoryInfo) {

		// 보드 ID로 불러오기
		List<Board> boards = boardService.searchByCategory(boardSearchByCategoryInfo);
		return ResponseEntity.status(200).body(BoardSearchAllPostRes.of(200, "Success", boards));
	}
}
