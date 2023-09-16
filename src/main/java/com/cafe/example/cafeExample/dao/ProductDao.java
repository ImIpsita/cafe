package com.cafe.example.cafeExample.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import com.cafe.example.cafeExample.Wrapper.productWrapper;
import com.cafe.example.cafeExample.pojo.Product;

public interface ProductDao extends JpaRepository<Product,Integer> {
	
	List<productWrapper> getAllProduct ();
	
	@Modifying
	@Transactional
	Integer updateProductStatus(@Param("status") String status,@Param("id") Integer id);
	
	List<productWrapper> getProductByCatagoryId (@Param("id") Integer id);
	
	productWrapper ProductgetById (@Param("id") Integer id);

}
