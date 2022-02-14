package com.ssafy.tourtogether.db.repository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.tourtogether.api.request.UserDeleteDeleteReq;
import com.ssafy.tourtogether.db.entity.QUser;
import com.ssafy.tourtogether.db.entity.User;

/**
 * 유저 모델 관련 디비 쿼리 생성을 위한 구현 정의.
 */
@Repository
public class UserRepositorySupport {
	@Autowired
	private JPAQueryFactory jpaQueryFactory;
	QUser qUser = QUser.user;

	public Optional<User> findByUserClientId(String userClientId, int userLoginPlatform) {
		User user = jpaQueryFactory.select(qUser).from(qUser)
				.where(qUser.userClientId.eq(userClientId).and(qUser.userLoginPlatform.eq(userLoginPlatform)))
				.fetchOne();
		if (user == null)
			return Optional.empty();
		return Optional.ofNullable(user);
	}

	public Optional<User> findByUserNickname(String userNickname) {
		User user = jpaQueryFactory.select(qUser).from(qUser).where(qUser.userNickname.eq(userNickname)).fetchOne();
		if (user == null)
			return Optional.empty();
		return Optional.ofNullable(user);
	}

	@Transactional
	public void updateUserNickname(String userNickname, String newUserNickname) {
		jpaQueryFactory.update(qUser).where(qUser.userNickname.eq(userNickname))
				.set(qUser.userNickname, newUserNickname).execute();
	}

	@Transactional
	public void updateProfileImage(String userClientId, String userProfileImage) {
		jpaQueryFactory.update(qUser).where(qUser.userClientId.eq(userClientId))
				.set(qUser.userProfileImage, userProfileImage).execute();
	}

	public boolean checkUser(UserDeleteDeleteReq deleteInfo) {
		System.out.println(deleteInfo.getUserId() + ", " + deleteInfo.getUserEmail());
		User user = null;
		user = jpaQueryFactory.select(qUser).from(qUser).where(qUser.userId.eq(deleteInfo.getUserId()))
				.where(qUser.userEmail.eq(deleteInfo.getUserEmail())).fetchOne();
		System.out.println("USER -> " + user);
		if (user != null) {
			return true;
		} else {
			return false;
		}
	}

	@Transactional
	public void deleteUser(UserDeleteDeleteReq deleteInfo) {
		System.out.println(deleteInfo.getUserId() + " , " + deleteInfo.getUserEmail());
		jpaQueryFactory.delete(qUser).where(qUser.userId.eq(deleteInfo.getUserId()))
				.where(qUser.userEmail.eq(deleteInfo.getUserEmail())).execute();

	}
}
