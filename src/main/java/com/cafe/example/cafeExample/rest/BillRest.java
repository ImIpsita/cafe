package com.cafe.example.cafeExample.rest;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/bill")
public interface BillRest {
	
	@PostMapping("/generatedReport")
	ResponseEntity<?> generatedReport(@RequestBody(required = true)Map<String,Object> billmapReq);

	@GetMapping("/get")
	ResponseEntity<?> getBillList();
	
	@PostMapping("/getPdfDownload")
	ResponseEntity<?> getPdfDownload(@RequestBody(required = true)Map<String,Object> billmapReq);
	
	@DeleteMapping("/delete/{id}")
	ResponseEntity<?> deleteBill(@PathVariable String id);
}
