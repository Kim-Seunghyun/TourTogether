package com.ssafy.tourtogether;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.ssafy.tourtogether")
@EntityScan(basePackages = {"com.ssafy.tourtogether.domain"})
@ComponentScan(basePackages = {"com.ssafy.tourtogether"})
@EnableJpaRepositories(basePackages = {"com.ssafy.tourtogether.repository"})
public class TourtogetherApplication {

	public static void main(String[] args) {
		SpringApplication.run(TourtogetherApplication.class, args);
	}

}
