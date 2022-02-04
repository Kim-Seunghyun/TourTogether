package com.ssafy.tourtogether.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.tourtogether.api.request.BoardCreatePostReq;
import com.ssafy.tourtogether.api.request.BoardDeleteDeleteReq;
import com.ssafy.tourtogether.db.entity.Board;
import com.ssafy.tourtogether.db.repository.BoardRepository;
import com.ssafy.tourtogether.db.repository.BoardRepositorySupport;

/**
 *	보드 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("userService")
public class BoardServiceImpl implements BoardService {

	@Autowired
	BoardRepository boardRepository;
	
	@Autowired
	BoardRepositorySupport boardRepositorySupport;
	
	@Override
	public Board createBoard(BoardCreatePostReq boardCreateInfo) {
		Board board = new Board();
		board.setBoardName(boardCreateInfo.getBoardName());
		return boardRepository.save(board);
	}

	@Override
	public String deleteBoard(BoardDeleteDeleteReq boardDeleteInfo) {
		String userClientId = boardDeleteInfo.getUserClientId();
		int userPlatFormNum = boardDeleteInfo.getPlatformNum();
		// 가입한 플랫폼이 카카오라면 카카오 db에서 찾기
		if (userPlatFormNum == 1) {
			
		}
		return boardRepositorySupport.deleteBoard();
	}

}