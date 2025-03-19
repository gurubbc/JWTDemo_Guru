package com.trivium.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

	@GetMapping("/admin")
	public String helloAdmin() {
		return "hello admin !";
	}

	@GetMapping("/user")
	public String helloUser() {
		return "hello user wednesday";
	}

}
