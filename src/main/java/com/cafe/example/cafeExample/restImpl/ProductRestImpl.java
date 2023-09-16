package com.cafe.example.cafeExample.restImpl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.cafe.example.cafeExample.constant.CafeConstant;
import com.cafe.example.cafeExample.rest.ProductRest;
import com.cafe.example.cafeExample.service.ProductService;
import com.cafe.example.cafeExample.utils.CafeUtils;
import com.google.common.base.Strings;

@RestController
public class ProductRestImpl implements ProductRest {

	@Autowired
	ProductService productservice;

	@Override
	public ResponseEntity<?> addNewProduct(Map<String, String> productAddReq) {
		try {
			return productservice.addNewProduct(productAddReq);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return CafeUtils.getResponseHndler(CafeConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<?> updateProduct(Map<String, String> productUpdateReq) {
		try {
			return productservice.updateProduct(productUpdateReq);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return CafeUtils.getResponseHndler(CafeConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<?> getAllProduct(String filteredValue) {
		try {
			return productservice.getAllProduct(filteredValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return CafeUtils.getResponseHndler(CafeConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<?> updateProductStatus(Map<String, String> productStatusReq) {
		try {
			return productservice.updateProductStatus(productStatusReq);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return CafeUtils.getResponseHndler(CafeConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<?> getProductByCatagory(String id) {
		try {
			return productservice.getProductByCatagory(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return CafeUtils.getResponseHndler(CafeConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<?> getProductById(String id) {
		try {
			return productservice.getProductById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return CafeUtils.getResponseHndler(CafeConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<?> deleteProductById(String id) {
		try {
			return productservice.deleteProductById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return CafeUtils.getResponseHndler(CafeConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	

}
