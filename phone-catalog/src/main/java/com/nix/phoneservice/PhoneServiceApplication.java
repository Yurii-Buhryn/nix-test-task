package com.nix.phoneservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class PhoneServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhoneServiceApplication.class, args);
	}
}
