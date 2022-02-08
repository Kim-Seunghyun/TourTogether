
# TOUR TOGETHER

---

![스크린샷 2022-02-08 오후 1.33.05.png](README%2048bbeef906c4451e9323428998bc0572/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2022-02-08_%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE_1.33.05.png)

**실시간 여행계획 공유 서비스**

> 친구들과 실시간으로 소통하며 여행 계획을 짜고 싶은 사람! <br/>
다른 사람들의 기깔나는 여행 계획을 추천받고 싶은 사람!<br/>
여행지는 정했지만 어디 가야할지 도저히 모르겠는 사람!<br/><br/>
Tour Together을 통해 실시간으로 소통하고, 인기있는 여행계획을 둘러보고, 관광지를 추천받으세요!<br/>
당신의 완벽한 여행을 위해 Tour Together가 도와줍니다.



## 📎  프로젝트 목차

---

[TOUR TOGETHER](#TOUR-TOGETHER) <br>
[🖥  프로젝트 소개](#프로젝트-소개) <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[⚙️ 기술 스택](#기술-스택)<br>
[🗂  프로젝트 파일 구조](#프로젝트-파일-구조)<br>
[📌  프로젝트 산출물](#프로젝트-산출물)

## 🖥  프로젝트 소개

---

1. 일정 : 22.1.10 - 22.2.18 (총 6주)
    - Sub1 : 22.01.10 - 22.02.14
    - Sub2 : 22.01.17 - 22.01.28
    - Sub3 : 22.01.31 - 22.02.18 (🔅 진행중🔅 )
2. 인원 (총 6인)
    - 김승현 : Front-end, Front-end Tech Leader
    - 박세진 : Front-end, 기획
    - 박소율 : 부팀장, Back-end, QA
    - 손형선 : Front-end, QA
    - 오재우 : Back-end, Back-end Tech Leader
    - 이민정 : 팀장, Server, Back-end, 기획
    

### ⚙️ 기술 스택

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
    

## 🗂  프로젝트 파일 구조

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
    

## 📌  프로젝트 산출물

---

- 프로젝트 관리 : [Notion](https://www.notion.so/SSAFY-2-5-ba42d6abf58c4708a3257429d4c31bc9)
- 기획서 (산출물 폴더 링크 걸기)
- 와이어프레임 (산출물 폴더 링크 걸기)
- ERD (산출물 폴더 링크 걸기)
- 시퀀스 다이어그램 (산출물 폴더 링크 걸기)
- 서비스 아키텍처 (산출물 폴더 링크 걸기)
