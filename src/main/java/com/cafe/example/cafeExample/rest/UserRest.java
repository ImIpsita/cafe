package com.cafe.example.cafeExample.rest;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = "/user")
public interface UserRest {
	
	@PostMapping(path= "/signup")
	public ResponseEntity<?>signUp(@RequestBody(required = true)Map<String,String> signupReq);
		
    @PostMapping(path = "/login")
	public ResponseEntity<?>login(@RequestBody(required = true) Map<String,String> loginreq);
    
    @GetMapping(path = "/getAllUser")
    public ResponseEntity<?>getAllUser();
    
    @PutMapping(path = "/UpdateUserStatus")
    public ResponseEntity<?>updateUserStatus(@RequestBody(required = true) Map<String,String> updatereq);
    
    @GetMapping(path = "/checkToken")
    public ResponseEntity<?>checkToken();
    
    @PostMapping(path = "/changePassword")
   	public ResponseEntity<?>changePassword(@RequestBody(required = true) Map<String,String> changepasswordReq);
    
    @PostMapping(path = "/forgetPassword")
   	public ResponseEntity<?>forgetPassword(@RequestBody(required = true) Map<String,String> forgetpasswordReq);
    
    
}
