package com.charter.rewards;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main entry point for the Customer Rewards API application.
 * <p>
 * This class uses the {@link SpringBootApplication} annotation to enable Spring
 * Boot auto-configuration, component scanning, and configuration properties.
 * Running this class will bootstrap the application context and start the
 * embedded web server to serve the API.
 * </p>
 */
@SpringBootApplication
public class RewardsApplication {

	/**
	 * The standard main method used to launch the Spring Boot application.
	 * <p>
	 * Delegates to {@link SpringApplication#run(Class, String...)} to start the
	 * application.
	 * </p>
	 *
	 * @param args command-line arguments passed during application startup
	 */
	public static void main(String[] args) {
		SpringApplication.run(RewardsApplication.class, args);
	}
}