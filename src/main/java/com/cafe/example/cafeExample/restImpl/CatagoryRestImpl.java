package com.cafe.example.cafeExample.restImpl;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.cafe.example.cafeExample.constant.CafeConstant;
import com.cafe.example.cafeExample.rest.CategoryRest;
import com.cafe.example.cafeExample.service.CategoryService;
import com.cafe.example.cafeExample.utils.CafeUtils;

@RestController
public class CatagoryRestImpl implements CategoryRest {

	@Autowired
	CategoryService categoryService;
	
	@Override
	public ResponseEntity<?> addNewCategory(Map<String, String> catagoryAddReq) {
		try {
			return categoryService.addCategory(catagoryAddReq);
		}catch (Exception e) {
		e.printStackTrace();
		}
		return CafeUtils.getResponseHndler(CafeConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<?> getAllCategory(String filteredValue) {
		try {
			return categoryService.getAllCategory(filteredValue);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<> (new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<?> updateNewCategory(Map<String, String> catagoryUpdateReq) {
		try {
			return categoryService.updateCategory(catagoryUpdateReq);
		}catch (Exception e) {
		e.printStackTrace();
		}
		return CafeUtils.getResponseHndler(CafeConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
