package com.ssafy.tourtogether.db.entity;

//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * 유저 모델 정의.
 */
@Entity
@Getter
@Setter
public class User  extends BaseEntity{
	String userClientId; // fk, api로 발급되는 id
	String userName; // 이름
	String userNickname; // unique
	String userProfileImage; // 프로필 이미지
	String userPhone; // 휴대폰 번호
	String userJoindate; // TODO 날짜처리: 일단 string로 선언해뒀는데 수정해야함
	int userLoginPlatform; // (1.카카오, 2.네이버 , 3.구글)
	int userScore; // 경험치

//    @JsonIgnore
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
//    String password;
}
