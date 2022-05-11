# TOUR TOGETHER
![로고](/산출물/로고/IMG_0424.jpg)

---


**실시간 여행계획 공유 서비스**

> 친구들과 실시간으로 소통하며 여행 계획을 짜고 싶은 사람! <br/>
다른 사람들의 기깔나는 여행 계획을 추천받고 싶은 사람!<br/>
여행지는 정했지만 어디 가야할지 도저히 모르겠는 사람!<br/><br/>
Tour Together을 통해 실시간으로 소통하고, 인기있는 여행계획을 둘러보고, 관광지를 추천받으세요!<br/>
당신의 완벽한 여행을 위해 Tour Together가 도와줍니다.



## 📎  프로젝트 목차

---
- [TOUR TOGETHER](#TOUR TOGETHER)<br>
- [프로젝트 목차](#프로젝트-목차)
	- [1️⃣ 프로젝트 소개](#1️⃣-프로젝트-소개)
		- [기술 스택](#기술-스택)
	- [2️⃣ 프로젝트 파일 구조](#2️⃣-프로젝트-파일-구조)
	- [3️⃣ 프로젝트 산출물](#3️⃣-프로젝트-산출물)
    - [4️⃣ 프로젝트 빌드](#4️⃣-프로젝트-빌드)

## 1️⃣ 프로젝트 소개

---

1. 일정 : 22.1.10 - 22.2.18 (총 6주)
    - Sub1 : 22.01.10 - 22.02.14
    - Sub2 : 22.01.17 - 22.01.28
    - Sub3 : 22.01.31 - 22.02.18 
    - [세부 일정](https://amused-viper-137.notion.site/79098e3921524c51ab5f992d430e954a?v=b79c670d23a14315bfc79d2ac54af7e3)
2. 인원 (총 6인)
    - 김승현 : Front-end, Front-end Tech Leader
    - 박세진 : Front-end, 기획
    - 박소율 : 부팀장, Back-end, QA
    - 손형선 : Front-end, QA
    - 오재우 : Back-end, Back-end Tech Leader
    - 이민정 : 팀장, Server, Back-end, 기획
    

### 기술 스택

1. 이슈관리 : Jira
2. 형상관리 : Gitlab
3. 커뮤니케이션 : Mattermost, Notion
4. 개발환경
    - OS : Windows 10
    - IDE
        - Spring Tool Suite
        - Visual Studio Code
    - Frontend
        - Vue 3.0.0
        - Vuex 4.0.0-0
    - Backend
        - Java(jdk 1.8)
        - Spring Boot Gradle 2.6.2
        - lombok
        - swagger, Querydsl-Jpa redis
    - WebRTC
        - openvidu 2.20.0
    - Database : MySQL Workbench 8.0.22
    - AWS S3
    - CI/CD
        - Jenkins
        - nginx
        - aws ec2
    

## 2️⃣ 프로젝트 파일 구조
---

- Back
  
    ```bash
    ─ com
       └── ssafy
           └── tourtogether
               ├── api
               │   ├── controller
               │   ├── request
               │   ├── response
               │   └── service
               ├── common
               │   ├── auth
               │   ├── exception
               │   │   └── handler
               │   ├── model
               │   │   └── response
               │   └── util
               ├── config
               └── db
                   ├── entity
                 └── repository
    ```
    
- Front
  
    ```bash
    frontend
    ├── public
    └── src
    ├── assets
    │   └── img
    │       └── icons
    ├── components
    ├── router
    ├── store
    └── views
    ```
    

## 3️⃣ 프로젝트 산출물

---

- 프로젝트 관리 : [Notion](https://amused-viper-137.notion.site/SSAFY-2-5-ba42d6abf58c4708a3257429d4c31bc9)
- [기획서](https://github.com/Kim-Seunghyun/TourTogether/blob/develop/%EC%82%B0%EC%B6%9C%EB%AC%BC/%EA%B8%B0%ED%9A%8D%EC%84%9C/%EA%B3%B5%ED%86%B5PJT_%EA%B8%B0%ED%9A%8D%EB%B0%9C%ED%91%9C.pdf) 
- [와이어프레임](https://github.com/Kim-Seunghyun/TourTogether/tree/develop/%EC%82%B0%EC%B6%9C%EB%AC%BC/%EC%99%80%EC%9D%B4%EC%96%B4%ED%94%84%EB%A0%88%EC%9E%84)
- [ERD](https://github.com/Kim-Seunghyun/TourTogether/blob/develop/%EC%82%B0%EC%B6%9C%EB%AC%BC/erd/tt_erd.pdf) 
- [시퀀스 다이어그램](https://github.com/Kim-Seunghyun/TourTogether/tree/develop/%EC%82%B0%EC%B6%9C%EB%AC%BC/%EC%8B%9C%ED%80%80%EC%8A%A4%20%EB%8B%A4%EC%9D%B4%EC%96%B4%EA%B7%B8%EB%9E%A8)
- [서비스 아키텍처](https://github.com/Kim-Seunghyun/TourTogether/blob/develop/%EC%82%B0%EC%B6%9C%EB%AC%BC/%EC%8B%9C%EC%8A%A4%ED%85%9C%20%EA%B5%AC%EC%84%B1%EB%8F%84/tt_%EC%8B%9C%EC%8A%A4%ED%85%9C%EA%B5%AC%EC%84%B1%EB%8F%84.pdf)
- [화면정의서](https://github.com/Kim-Seunghyun/TourTogether/blob/develop/%EC%82%B0%EC%B6%9C%EB%AC%BC/%ED%99%94%EB%A9%B4%EC%A0%95%EC%9D%98%EC%84%9C/%ED%99%94%EB%A9%B4%EC%A0%95%EC%9D%98%EC%84%9C.pdf)


## 4️⃣ 프로젝트 빌드

---

- [포팅 메뉴얼](https://github.com/Kim-Seunghyun/TourTogether/blob/develop/exec/%ED%8F%AC%ED%8C%85%EB%A7%A4%EB%89%B4%EC%96%BC.pdf)
