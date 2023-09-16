package com.cafe.example.cafeExample.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

public interface UserService {

	public ResponseEntity<?>signup(Map<String,String>signupreq);
	
	public ResponseEntity<?>loginUp(Map<String,String>loginreq);
	
	public ResponseEntity<?>getAllUser();
	
	public ResponseEntity<?>updateUserStatus(Map<String,String>updatereq);
	
	ResponseEntity<?>changePassword(Map<String,String> changepasswordReq);
	
	ResponseEntity<?>forgetPassword(Map<String,String> forgetpasswordReq);
}
