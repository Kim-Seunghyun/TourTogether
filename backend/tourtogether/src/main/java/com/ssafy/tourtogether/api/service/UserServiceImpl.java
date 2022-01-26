package com.ssafy.tourtogether.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.tourtogether.api.request.UserLoginPostReq;
//import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.tourtogether.db.entity.User;
import com.ssafy.tourtogether.db.repository.UserRepository;
//import com.ssafy.db.repository.UserRepository;
//import com.ssafy.db.repository.UserRepositorySupport;
import com.ssafy.tourtogether.db.repository.UserRepositorySupport;

/**
 * 유저 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;

	@Autowired
	UserRepositorySupport userRepositorySupport;

//	@Override
//	public User createUser(UserRegisterPostReq userRegisterInfo) {
//		User user = new User();
//		user.setUserId(userRegisterInfo.getId());
//		// 보안을 위해서 유저 패스워드 암호화 하여 디비에 저장.
//		user.setPassword(passwordEncoder.encode(userRegisterInfo.getPassword()));
//		user.setName(userRegisterInfo.getName());
//		user.setDepartment(userRegisterInfo.getDepartment());
//		user.setPosition(userRegisterInfo.getPosition());
//		return userRepository.save(user);
//	}

	@Override
	public User getUserByUserId(String userId) {
		// 디비에 유저 정보 조회 (userId 를 통한 조회).
		try {
			User user = userRepositorySupport.findByUserClientId(userId).get();
			return user;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public User createUser(UserLoginPostReq loginInfo) {
		System.out.println("save");
		User user = new User(loginInfo);
		user.setUserClientId(loginInfo.getUserClientId());
		user.setUserName(loginInfo.getUserName());
		user.setUserProfileImage(loginInfo.getUserProfileImage());
		user.setUserEmail(loginInfo.getUserEmail());
		userRepository.save(user);
		return user;
	}
}
