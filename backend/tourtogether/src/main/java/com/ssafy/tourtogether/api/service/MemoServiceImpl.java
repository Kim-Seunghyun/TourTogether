package com.ssafy.tourtogether.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.tourtogether.db.repository.MemoRepositorySupport;

@Service("memoService")
public class MemoServiceImpl implements MemoService {	
	@Autowired
	MemoRepositorySupport memoRepositorySupport;

	@Override
	public void updateMemoInMemoRoom(String memoBoardId, String content) {
		memoRepositorySupport.updateMemoInMemoRoom(memoBoardId, content);
	}
}
