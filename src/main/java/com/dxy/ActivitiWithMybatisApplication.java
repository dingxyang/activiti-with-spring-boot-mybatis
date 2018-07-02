package com.dxy;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.dxy.mapper")
public class ActivitiWithMybatisApplication {

	private static Logger logger = LoggerFactory.getLogger(ActivitiWithMybatisApplication.class);

	public static void main(String[] args) {
		logger.info("应用启动----------------------------");
		SpringApplication.run(ActivitiWithMybatisApplication.class, args);
	}
}


