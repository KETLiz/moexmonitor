// MoexMonitorApplication.java
package com.example.moexmonitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MoexMonitorApplication {
	public static void main(String[] args) {
		SpringApplication.run(MoexMonitorApplication.class, args);
	}
}
