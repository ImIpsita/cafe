package com.cafe.example.cafeExample.serviceImpl;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cafe.example.cafeExample.constant.CafeConstant;
import com.cafe.example.cafeExample.dao.CatagoryDao;
import com.cafe.example.cafeExample.jwt.JwtFilter;
import com.cafe.example.cafeExample.pojo.Catagory;
import com.cafe.example.cafeExample.service.CategoryService;
import com.cafe.example.cafeExample.utils.CafeUtils;
import com.google.common.base.Strings;

@Service
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	JwtFilter jwtFilter;
	
	@Autowired
	CatagoryDao categoryDao;
	
	@Override
	public ResponseEntity<?> addCategory(Map<String, String> addCatReq) {
		try {
			//Permit admin for only add catagory
			if(jwtFilter.IsAdmin()) {
				 if(validateCatagoryReq(addCatReq, false)) {
					 categoryDao.save(getCategory(addCatReq, false));
					 return CafeUtils.getResponseHndler("Category added successfully", HttpStatus.OK);
				 }else {
					 return CafeUtils.getResponseHndler("Failed Category add", HttpStatus.PRECONDITION_FAILED);
				 }
			}else {
				return CafeUtils.getResponseHndler(CafeConstant.UNAUTHORIZED_USER, HttpStatus.UNAUTHORIZED);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return CafeUtils.getResponseHndler(CafeConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	private boolean validateCatagoryReq(Map<String, String> CatagoryReq, boolean validateId) {
		if (CatagoryReq.containsKey("name")) {
			// in updatecase it will check id and validate not add
			if (CatagoryReq.containsKey("id") && validateId) {
				return true;
			} else if (!validateId) {
				return true;
			}

		}

		return false;

	}
	
	private Catagory getCategory(Map<String, String> addCatReq,Boolean isAdd) {
		Catagory cat= new Catagory();
		
		if(isAdd) {
			cat.setId(Integer.parseInt(addCatReq.get("id")));
		}
		cat.setName(addCatReq.get("name"));
		return cat;
		
	}


	@Override
	public ResponseEntity<?> getAllCategory(String filterValue) {
		try {
			if(!Strings.isNullOrEmpty(filterValue) && filterValue.equalsIgnoreCase("true") ) {
				return new ResponseEntity<> (categoryDao.getAllCategory(),HttpStatus.OK);
			}
			return new ResponseEntity<> (categoryDao.findAll(),HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<> (new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
	}


	@Override
	public ResponseEntity<?> updateCategory(Map<String, String> updateCatReq) {
		try {
			// Permit admin for only update catagory
			if (jwtFilter.IsAdmin()) {
				if (validateCatagoryReq(updateCatReq, true)) {
					Optional<Catagory> catagoryobj = categoryDao.findById(Integer.parseInt(updateCatReq.get("id")));
					if (null!=catagoryobj) {
						categoryDao.save(getCategory(updateCatReq, true));
						return CafeUtils.getResponseHndler("Category Updated successfully",HttpStatus.OK);
					} else {
						return CafeUtils.getResponseHndler("Category Updation failed", HttpStatus.PRECONDITION_FAILED);
					}

				} else {
					return CafeUtils.getResponseHndler("Failed Category updation", HttpStatus.PRECONDITION_FAILED);
				}
			} else {
				return CafeUtils.getResponseHndler(CafeConstant.UNAUTHORIZED_USER, HttpStatus.UNAUTHORIZED);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return CafeUtils.getResponseHndler(CafeConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
