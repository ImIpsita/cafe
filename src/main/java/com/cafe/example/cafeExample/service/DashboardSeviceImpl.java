package com.cafe.example.cafeExample.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cafe.example.cafeExample.dao.BillDao;
import com.cafe.example.cafeExample.dao.CatagoryDao;
import com.cafe.example.cafeExample.dao.ProductDao;

@Service
public class DashboardSeviceImpl implements DashboardService {

	@Autowired
	CatagoryDao catagoryDao;
	
	@Autowired
	ProductDao productDao;
	
	@Autowired
	BillDao billdao;
	
	@Override
	public ResponseEntity<?> getDetailsCount() {
		Map<String,Object> count=new HashMap<String,Object>();
		count.put("Catagory", catagoryDao.count());
		count.put("Product", productDao.count());
		count.put("Bill", billdao.count());
		return new ResponseEntity<>(count,HttpStatus.OK);
	}

}
