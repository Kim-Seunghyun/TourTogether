package com.ssafy.tourtogether.db.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.ssafy.tourtogether.api.request.UserLoginPostReq;

/**
 * 유저 모델 정의.
 */
@Entity
@Getter
@Setter
@ToString
public class User extends BaseEntity {

	public User() {
	}

	public User(UserLoginPostReq loginInfo) {
		setUserNickname("newUser" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddTHHmmSSnn")));
		setUserJoindate(LocalDateTime.now().toString());
		setUserLoginPlatform(1);
		setUserScore(0);
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "INT UNSIGNED")
	int userId; // pk, ai
	String userClientId; // fk, api로 발급되는 id
	String userName; // 이름
	String userNickname; // unique
	String userProfileImage; // 프로필 이미지
	String userPhone; // 휴대폰 번호
	String userJoindate; // 가입한 날짜
	String userEmail;
	int userLoginPlatform; // (1.카카오, 2.네이버 , 3.구글)
	int userScore; // 경험치
}
