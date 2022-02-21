package com.ssafy.tourtogether.api.service;

import com.ssafy.tourtogether.api.request.UserDeleteDeleteReq;
import com.ssafy.tourtogether.api.request.UserLoginPostReq;
import com.ssafy.tourtogether.db.entity.User;

public interface UserService {
	User getUserByUserId(String userId, int userLoginPlatform);

	User createUser(UserLoginPostReq loginInfo);

	User updateUserNickname(String userNickname, String newUserNickname);

	User updateUserProfileImage(String userClientId, int userLoginPlatform, String userProfileImage);

	void deleteUser(UserDeleteDeleteReq deleteInfo);

	boolean checkUser(UserDeleteDeleteReq deleteInfo);
}
