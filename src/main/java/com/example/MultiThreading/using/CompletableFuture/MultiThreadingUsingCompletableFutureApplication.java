package com.example.MultiThreading.using.CompletableFuture;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MultiThreadingUsingCompletableFutureApplication {

	/*
	 * This Completable Future concept was introduced in JDK 8 and it provides an
	 * easy way to write asynchronous multithreaded code
	 */

	public static void main(String[] args) {
		SpringApplication.run(MultiThreadingUsingCompletableFutureApplication.class, args);
	}

}
