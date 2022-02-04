package com.ssafy.tourtogether.api.service;

import com.ssafy.tourtogether.api.request.BoardCreatePostReq;
import com.ssafy.tourtogether.api.request.BoardDeleteDeleteReq;
import com.ssafy.tourtogether.db.entity.Board;


public interface BoardService {
	
	Board createBoard(BoardCreatePostReq boardCreateInfo);

	String deleteBoard(BoardDeleteDeleteReq boardDeleteInfo);
}