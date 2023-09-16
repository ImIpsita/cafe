package com.cafe.example.cafeExample.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cafe.example.cafeExample.pojo.Catagory;

@Repository
public interface CatagoryDao extends JpaRepository<Catagory, Integer> {
	
	List<Catagory> getAllCategory();

}
