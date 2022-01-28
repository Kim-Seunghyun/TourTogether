package com.ssafy.tourtogether.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.tourtogether.db.entity.QUser;
import com.ssafy.tourtogether.db.entity.User;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
                .where(qUser.userClientId.eq(userClientId).and(qUser.userLoginPlatform.eq(userLoginPlatform))).fetchOne();
        if(user == null) return Optional.empty();
        return Optional.ofNullable(user);
    }

    public Optional<User> findByUserNickname(String userNickname) {
        User user = jpaQueryFactory.select(qUser).from(qUser)
                .where(qUser.userNickname.eq(userNickname)).fetchOne();
        if(user == null) return Optional.empty();
        return Optional.ofNullable(user);
    }
}
