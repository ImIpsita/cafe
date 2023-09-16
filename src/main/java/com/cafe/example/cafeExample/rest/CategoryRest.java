package com.cafe.example.cafeExample.rest;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping(path="/Category")
public interface CategoryRest {
	
	@PostMapping(path="/add")
	public ResponseEntity<?> addNewCategory(@RequestBody(required = true) Map<String,String>catagoryAddReq);

	//in admin case we re passing filtervalue true for getting all catagory with product associate or not 
	//But in user case only product associate catagory will be fetched
	@GetMapping(path="/get")
	public ResponseEntity<?> getAllCategory(@RequestParam(required = false) String filteredValue);
	
	@PutMapping(path="/update")
	public ResponseEntity<?> updateNewCategory(@RequestBody(required = true) Map<String,String>catagoryUpdateReq);
}
