package com.ssafy.tourtogether.api.service;

//import com.ssafy.tourtogether.api.request.UserRegisterPostReq;
import com.ssafy.tourtogether.db.entity.User;

public interface UserService {
//	User createUser(UserRegisterPostReq userRegisterInfo);
	User getUserByUserId(String userId);

}
