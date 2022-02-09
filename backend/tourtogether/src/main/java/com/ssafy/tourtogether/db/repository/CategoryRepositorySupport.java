package com.ssafy.tourtogether.db.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class CategoryRepositorySupport {
	@Autowired
	private JPAQueryFactory jpaQueryFactory;
}
