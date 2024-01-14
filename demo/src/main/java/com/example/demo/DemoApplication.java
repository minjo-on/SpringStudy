package com.example.demo;

import chap07.Calculator;
import chap07.ExeTimeCalculator;
import chap07.ImpeCalculator;
import chap07.RecCalculator;
import config.AppCtx;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class DemoApplication {
	public static void main(String[] args) {
//		SpringApplication.run(DemoApplication.class, args);

		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppCtx.class);

		Calculator cal = ctx.getBean("calculator",Calculator.class);
		long fiveFact = cal.factorial(5);
		System.out.println(fiveFact);
		System.out.println(cal.getClass().getName());
		ctx.close();
	}

}
