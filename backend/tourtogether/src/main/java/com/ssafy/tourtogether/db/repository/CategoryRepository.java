package com.ssafy.tourtogether.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.tourtogether.db.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
