package com.cafe.example.cafeExample.serviceImpl;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.cafe.example.cafeExample.constant.CafeConstant;
import com.cafe.example.cafeExample.dao.UserDao;
import com.cafe.example.cafeExample.jwt.CustomUserDetailsService;
import com.cafe.example.cafeExample.jwt.Jwt;
import com.cafe.example.cafeExample.jwt.JwtFilter;
import com.cafe.example.cafeExample.pojo.User;
import com.cafe.example.cafeExample.service.UserService;
import com.cafe.example.cafeExample.utils.CafeUtils;
import com.cafe.example.cafeExample.utils.EmailUtils;
//import com.cafe.example.cafeExample.utils.UserWrapper;
import com.cafe.example.cafeExample.utils.UserWrapper;
import com.google.common.base.Strings;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	Jwt jwt;
	
	@Autowired
	JwtFilter jwtFilter;
	
	@Autowired
	EmailUtils emailutils;

	@Override
	public ResponseEntity<?> signup(Map<String, String> signupreq) {
		
		if(validateSignupReq(signupreq)) {
			User findByEmail = userDao.findByEmail(signupreq.get("email"));
			  if(Objects.isNull(findByEmail)) {
				  userDao.save(getUserFromMap(signupreq));
				  return CafeUtils.getResponseHndler(CafeConstant.USER_REGISTERED_SUCCESSFULLY,HttpStatus.OK);
			  }else {
				  return CafeUtils.getResponseHndler(CafeConstant.USER_ALREADY_REGISTERED,HttpStatus.PRECONDITION_FAILED);
			  }
		}else {
			return CafeUtils.getResponseHndler(CafeConstant.INVALID_USER_INFO,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
	}
	
	private boolean validateSignupReq(Map<String, String> signupreq) {
		//validate signup request
		if (signupreq.containsKey("contactNumber") && signupreq.containsKey("password")
				&& signupreq.containsKey("email") && signupreq.containsKey("name")) {
			return true;
		} else {
			return false;
		}

	}
	
	private User getUserFromMap(Map<String, String> signupreq) {
		
		User user=new User();
		user.setContactNumber(signupreq.get("contactNumber"));
		user.setEmail(signupreq.get("email"));
		user.setName(signupreq.get("name"));
		user.setPassword(signupreq.get("password"));
		user.setRole(signupreq.get("role"));
		user.setStatus("ACTIVE");
		
		return user; 
		
	}

	@Override
	public ResponseEntity<?> loginUp(Map<String, String> loginreq) {
		try {
			/*Here while login first authenticate with UsernamePasswordAuthenticationToken  
			 * Where need to give Username and password parameter for signedup user.
			 */
			Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginreq.get("email"),loginreq.get("password")));
			if(authenticate.isAuthenticated()) {
				if(customUserDetailsService.getUserDetail().getStatus().equalsIgnoreCase("ACTIVE")) {
					return new ResponseEntity<String>("{\"token\":\""+jwt.generateToken(customUserDetailsService.getUserDetail().getEmail(),customUserDetailsService.getUserDetail().getRole())+"\"}",HttpStatus.OK);
				}else {
					return new ResponseEntity<String>("{\"Message\":\"Wait For Admin Approval\"}",HttpStatus.BAD_REQUEST);
				}
				
			}
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		
		return new ResponseEntity<String>("{\"Message\":\"Bad Credential\"}",HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<?> getAllUser() {
		
		try {
			if(jwtFilter.IsAdmin()) {
		          List<UserWrapper> allUser = userDao.getAllUser();
		          return new ResponseEntity<>(allUser,HttpStatus.OK);
			}else {
				return CafeUtils.getResponseHndler(CafeConstant.UNAUTHORIZED_USER,HttpStatus.UNAUTHORIZED);
			}
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		// TODO Auto-generated method stub
		return new ResponseEntity<>(Collections.EMPTY_LIST,HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<?> updateUserStatus(Map<String, String> updatereq) {

         try {
        	 //update status only allowed for admin,its valid through token
        	      if(jwtFilter.IsAdmin()) {
        	    	     //Request user existance from DB
        	    	 User user = userDao.findById(Integer.parseInt(updatereq.get("id"))).get();
        	    	     if(!Objects.isNull(user)){
        	    	    	    userDao.updateStatus(updatereq.get("status"), Integer.parseInt(updatereq.get("id"))) ;
        	    	    	       //mail sending to the Admin
        	    	    	     sendMail(updatereq.get("status"), user.getEmail(), userDao.getAllAdmin());
        	    	    	     return CafeUtils.getResponseHndler("User Status "+updatereq.get("status")+" successfully", HttpStatus.OK);
        	    	     }else {
        	    	    	 return CafeUtils.getResponseHndler("User Id is Invalid", HttpStatus.PRECONDITION_FAILED);
        	    	     }
        	      }else {
        	    	  return CafeUtils.getResponseHndler(CafeConstant.UNAUTHORIZED_USER, HttpStatus.UNAUTHORIZED);
        	      }
        	 
        	 
         }catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return CafeUtils.getResponseHndler(CafeConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	//Mail Sending Process
	private void sendMail(String status,String email ,List<String>ccUsers) {
		//ccUsers.remove(jwtFilter.currentUser());
		if(status.equalsIgnoreCase("ACTIVE") && status!=null) {
			emailutils.sendSimpleMessage(jwtFilter.currentUser(), "Account Enable","User "+email +"is approved by Admin"+jwtFilter.currentUser() , ccUsers);
			System.out.println("sucess message");
		}else {
			emailutils.sendSimpleMessage(jwtFilter.currentUser(), "Account disable","User "+email +" is approved by Admin "+jwtFilter.currentUser() , ccUsers);
			System.out.println("failed message");
		}  
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}

	@Override
	public ResponseEntity<?> changePassword(Map<String, String> changepasswordReq) {
		try {
			// CHECK LOGGED IN USER EXISTANCE FROM DB ,if yes allow for password change
			User userDetails = userDao.findByEmail(jwtFilter.currentUser());
			if (!Objects.isNull(userDetails)) {
				if (userDetails.getPassword().equalsIgnoreCase(changepasswordReq.get("oldPassword"))) {
					userDetails.setPassword(changepasswordReq.get("newPassword"));
					userDao.save(userDetails);
					return new ResponseEntity<>("New Password Updated Successfully", HttpStatus.OK);
				}
				return new ResponseEntity<>("Incorrect Old Password", HttpStatus.PRECONDITION_FAILED);
			}
			return new ResponseEntity<>(CafeConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return new ResponseEntity<>(CafeConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<?> forgetPassword(Map<String, String> forgetpasswordReq) {
		try {
			//checking the request email from DB,if present send mail (both vaid & invalid case)
			User userobj = userDao.findByEmail(forgetpasswordReq.get("email"));
			if(!Objects.isNull(userobj) && !Strings.isNullOrEmpty(userobj.getEmail())) {
				emailutils.forgetPasswordMail(userobj.getEmail(),"Credentials by Cafe Management",userobj.getPassword());
				return new ResponseEntity<>("Credential send for Mail approval", HttpStatus.OK);
			}
			return new ResponseEntity<>("Invalid user", HttpStatus.PRECONDITION_FAILED);
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return new ResponseEntity<>(CafeConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	
	
}
