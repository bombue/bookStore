package ru.akiselev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class WsUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(WsUserApplication.class, args);
	}

}
