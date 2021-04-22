package org.hamhub.service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

	@GetMapping("/")
	public String get(@RequestParam(value = "name", defaultValue = "World") String name) {
		return "Test";
	}
}
