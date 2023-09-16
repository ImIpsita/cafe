package com.cafe.example.cafeExample.restImpl;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.cafe.example.cafeExample.constant.CafeConstant;
import com.cafe.example.cafeExample.rest.UserRest;
import com.cafe.example.cafeExample.service.UserService;
import com.cafe.example.cafeExample.utils.CafeUtils;

@RestController
public class UserRestImpl implements UserRest{
	
	@Autowired
	UserService userService;

	@Override
	public ResponseEntity<?> signUp(Map<String, String> signupReq) {
	try {
		return userService.signup(signupReq);
	}catch (Exception e) {
		System.out.println(e.getMessage());
		
		//return CafeUtils.getResponseHndler("something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	return CafeUtils.getResponseHndler("something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
}

	@Override
	public ResponseEntity<?> login(Map<String, String> loginreq) {
		try {
			
		return userService.loginUp(loginreq);
		
		}catch(Exception e) {
			System.out.println(e.getMessage());
			//return CafeUtils.getResponseHndler("something went wrong",HttpStatus.INTERNAL_SERVER_ERROR);
			
			
		}
		return CafeUtils.getResponseHndler("something went wrong",HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<?> getAllUser() {
		try {
			return userService.getAllUser();
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return new ResponseEntity<>(Collections.EMPTY_LIST,HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<?> updateUserStatus(Map<String, String> updatereq) {
		try {
			return userService.updateUserStatus(updatereq);
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		// TODO Auto-generated method stub
		return new ResponseEntity<>(CafeConstant.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<?> checkToken() {
		
		return new ResponseEntity<>(Boolean.toString(true),HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> changePassword(Map<String, String> changepasswordReq) {
          try {
        	  return userService.changePassword(changepasswordReq);
          }catch (Exception e) {
        	  System.out.println(e.getMessage());
		}
		return new ResponseEntity<>(CafeConstant.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<?> forgetPassword(Map<String, String> forgetpasswordReq) {
		try {
			return userService.forgetPassword(forgetpasswordReq);
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return new ResponseEntity<>(CafeConstant.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
