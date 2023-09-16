package com.cafe.example.cafeExample.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

public interface CategoryService {
	
	ResponseEntity<?> addCategory(Map<String,String> addCatReq);
	
	ResponseEntity<?> getAllCategory(String filterValie);
	
	ResponseEntity<?> updateCategory(Map<String,String> updateCatReq);

}
