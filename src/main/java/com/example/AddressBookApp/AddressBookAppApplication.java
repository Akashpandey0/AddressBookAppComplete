package com.example.AddressBookApp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EnableCaching
@Slf4j
public class AddressBookAppApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(AddressBookAppApplication.class, args);
		log.info("Address Book App Started");
	}

}