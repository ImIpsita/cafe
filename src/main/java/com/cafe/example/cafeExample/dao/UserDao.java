package com.cafe.example.cafeExample.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cafe.example.cafeExample.pojo.User;
import com.cafe.example.cafeExample.utils.UserWrapper;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {

	User findByEmailId(@Param("email") String email);
	
	List<UserWrapper> getAllUser();
	
	@Transactional
	@Modifying
	Integer updateStatus(@Param("status") String status,@Param("id") Integer id);
	
	//only needs email info of all admins role users
	List<String> getAllAdmin();
	
	User findByEmail(String email);
	
	
}
