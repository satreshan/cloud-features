package com.sap.cc.greeting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class GreetingController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private GreetingService service;

	public GreetingController(GreetingService service) {
		this.service = service;
	}

	@GetMapping("/hello")
	public String greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		try {
			MDC.put("path", "/hello");
			return getGreeting("Hello", name);
		}finally {
			MDC.clear();
		}
	}

	@GetMapping("/howdy")
	public String deprecatedGreeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		try {
			//System.err.println("Deprecated endpoint used.");
			MDC.put("path", "/howdy");
			logger.warn("Deprecated endpoint used.");
			return getGreeting("Howdy", name);
		}finally {
			MDC.clear();
		}
	}

	private String getGreeting(String greeting, String name) {
		try {
			return service.createGreetingMessage(greeting, name);
		} catch (IllegalArgumentException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
		} finally {
		}
	}

}
