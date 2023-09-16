package com.cafe.example.cafeExample.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;

@Service
public class JwtFilter extends OncePerRequestFilter  {
	
	
	@Autowired
	Jwt jwt;

	@Autowired
	CustomUserDetailsService customeUserService;
	
	String token = null;
	String Username = null;
	
	Claims claims;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
	   //By pass these three endpoints as they don't required any authentication process/token
		if(request.getServletPath().matches("/user/login | /user/signup | /user/forgetPassword")) {
			//filter method calling in both preprocessing and postprocessing request
            filterChain.doFilter(request, response);
  
		}else {
		
			/*Except /user/login | /user/signup | /user/forgetPassword
			 * Checking token validation for other endpoints 
			 * 
			 */
		String authorizationheader=request.getHeader("Authorization");
		String token= null;
		    if(authorizationheader != null && authorizationheader.startsWith("Bearer ") ) {
		    	token =authorizationheader.substring(7) ;
		    	Username = jwt.extractUsername(token);
		    	claims=jwt.extractAllClaims(token);
		    }
		    //checking the user name and session on first time
		    if(Username !=null && SecurityContextHolder.getContext().getAuthentication() ==null) {
		    	UserDetails userdetails = customeUserService.loadUserByUsername(Username);
		    	   if(jwt.validateToken(token, userdetails)) {
		    		   UsernamePasswordAuthenticationToken userNamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(token,"", userdetails.getAuthorities());
		    		   userNamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		    		   SecurityContextHolder.getContext().setAuthentication(userNamePasswordAuthenticationToken);
		    	   }
		    }
		    filterChain.doFilter(request, response);
	}
	
		
	
}
    public Boolean IsAdmin() {
		return "Admin".equalsIgnoreCase((String)claims.get("role"));
    	
    }
    
    public Boolean IsUser() {
		return "User".equalsIgnoreCase((String)claims.get("role"));
    	
    }
    public String currentUser() {
    	return Username;
    }
}
