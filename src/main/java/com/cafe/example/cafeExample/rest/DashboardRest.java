package com.cafe.example.cafeExample.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/dashBoard")
public interface DashboardRest {
	
	@GetMapping("/count")
	ResponseEntity<?>getDetailsCount();

}
