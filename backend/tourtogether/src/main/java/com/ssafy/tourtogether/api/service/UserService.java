package com.ssafy.tourtogether.api.service;

import com.ssafy.tourtogether.api.request.UserLoginPostReq;
import com.ssafy.tourtogether.db.entity.User;

public interface UserService {
	User getUserByUserId(String userId);
	User createUser(UserLoginPostReq loginInfo); 
}
