package com.cafe.example.cafeExample.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.cafe.example.cafeExample.pojo.Bill;
//Used to genetrate Bill Stuffs
public interface BillDao extends JpaRepository<Bill, Integer> {
	
	List<Bill> getAllBillList();
	
	List<Bill> getCurrentUserBilList(@Param("username") String userName);

}
