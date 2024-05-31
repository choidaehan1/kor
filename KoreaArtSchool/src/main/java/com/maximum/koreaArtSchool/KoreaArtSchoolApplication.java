package com.maximum.koreaArtSchool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableCaching
@ComponentScan
public class KoreaArtSchoolApplication {

	public static void main(String[] args) {
		SpringApplication.run(KoreaArtSchoolApplication.class, args);
	}
}
