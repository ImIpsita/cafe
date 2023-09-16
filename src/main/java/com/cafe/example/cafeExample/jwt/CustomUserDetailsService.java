package com.cafe.example.cafeExample.jwt;

import java.util.ArrayList;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cafe.example.cafeExample.dao.UserDao;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	UserDao userdao;
	
	private com.cafe.example.cafeExample.pojo.User userDetail;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		userDetail = userdao.findByEmail(username);
		if(!Objects.isNull(userDetail)) 
			return new User(userDetail.getEmail(),userDetail.getPassword(),new ArrayList<>());
		else 
			throw new UsernameNotFoundException("User Not Found ");
	}
	
	public com.cafe.example.cafeExample.pojo.User getUserDetail(){
		return userDetail;
		
	}

}
