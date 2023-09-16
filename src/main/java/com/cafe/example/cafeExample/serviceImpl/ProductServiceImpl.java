package com.cafe.example.cafeExample.serviceImpl;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import org.apache.tomcat.util.digester.ArrayStack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cafe.example.cafeExample.Wrapper.productWrapper;
import com.cafe.example.cafeExample.constant.CafeConstant;
import com.cafe.example.cafeExample.dao.ProductDao;
import com.cafe.example.cafeExample.jwt.JwtFilter;
import com.cafe.example.cafeExample.pojo.Catagory;
import com.cafe.example.cafeExample.pojo.Product;
import com.cafe.example.cafeExample.service.ProductService;
import com.cafe.example.cafeExample.utils.CafeUtils;
import com.google.common.base.Strings;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductDao productDao;

	@Autowired
	JwtFilter jwtFilter;

	@Override
	public ResponseEntity<?> addNewProduct(Map<String, String> productAddReq) {
		try {
			if (jwtFilter.IsAdmin()) {
				if (validateProductReq(productAddReq, false)) {
					productDao.save(getProductDetails(productAddReq, false));
					return CafeUtils.getResponseHndler("Product added successfully", HttpStatus.OK);
				} else {
					return CafeUtils.getResponseHndler(CafeConstant.INVALID_DATA, HttpStatus.PRECONDITION_FAILED);
				}
			} else {
				return CafeUtils.getResponseHndler(CafeConstant.UNAUTHORIZED_USER, HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return CafeUtils.getResponseHndler(CafeConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<?> updateProduct(Map<String, String> productUpdateReq) {
		try {
			if (jwtFilter.IsAdmin()) {
				if (validateProductReq(productUpdateReq, true)) {
					Optional<Product> productDetails = productDao
							.findById(Integer.parseInt(productUpdateReq.get("id")));
					if (productDetails.isPresent()) {
						Product prodDetails = getProductDetails(productUpdateReq, true);
						prodDetails.setStatus(productDetails.get().getStatus());
						productDao.save(prodDetails);
						return CafeUtils.getResponseHndler("Product Updated successfully", HttpStatus.OK);
					} else {
						return CafeUtils.getResponseHndler("Product Not Found", HttpStatus.PRECONDITION_FAILED);
					}
				} else {
					return CafeUtils.getResponseHndler(CafeConstant.INVALID_DATA, HttpStatus.PRECONDITION_FAILED);
				}
			} else {
				return CafeUtils.getResponseHndler(CafeConstant.UNAUTHORIZED_USER, HttpStatus.UNAUTHORIZED);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return CafeUtils.getResponseHndler(CafeConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<?> getAllProduct(String filteredValue) {
		try {
			if (!Strings.isNullOrEmpty(filteredValue) && filteredValue.equalsIgnoreCase(filteredValue)) {
				return new ResponseEntity<>(productDao.getAllProduct(), HttpStatus.OK);

			} else {
				return new ResponseEntity<>(productDao.getAllProduct(), HttpStatus.OK);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(Collections.EMPTY_LIST, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<?> updateProductStatus(Map<String, String> productStatusReq) {
		try {
			if (jwtFilter.IsAdmin()) {
				Optional<Product> productDetails = productDao.findById(Integer.parseInt(productStatusReq.get("id")));
				if (productDetails.isPresent()) {
					productDao.updateProductStatus(productStatusReq.get("status"),
							Integer.parseInt(productStatusReq.get("id")));
					return CafeUtils.getResponseHndler("Product status updated Successfully", HttpStatus.OK);
				} else {
					return CafeUtils.getResponseHndler("Product Not Found", HttpStatus.PRECONDITION_FAILED);
				}
			} else {
				return CafeUtils.getResponseHndler(CafeConstant.UNAUTHORIZED_USER, HttpStatus.UNAUTHORIZED);
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return CafeUtils.getResponseHndler(CafeConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<?> getProductByCatagory(String id) {
		try {
			if (jwtFilter.IsAdmin()) {
				return new ResponseEntity<>(productDao.getProductByCatagoryId(Integer.parseInt(id)), HttpStatus.OK);
			} else {
				return CafeUtils.getResponseHndler(CafeConstant.UNAUTHORIZED_USER, HttpStatus.UNAUTHORIZED);
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return new ResponseEntity<>(Collections.EMPTY_LIST, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<?> getProductById(String id) {
		try {
			if (jwtFilter.IsAdmin()) {
				Optional<Product> findById = productDao.findById(Integer.parseInt(id));
				if (findById.isPresent()) {
					return new ResponseEntity<>(productDao.ProductgetById(Integer.parseInt(id)), HttpStatus.OK);
				} else {
					return new ResponseEntity<>("Product not found", HttpStatus.PRECONDITION_FAILED);
				}
			} else {
				return CafeUtils.getResponseHndler(CafeConstant.UNAUTHORIZED_USER, HttpStatus.UNAUTHORIZED);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return CafeUtils.getResponseHndler(CafeConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private Boolean validateProductReq(Map<String, String> productReq, Boolean validateId) {

		if (productReq.containsKey("name")) {
			if (productReq.containsKey("id") && validateId) {
				return true;
			} else if (!validateId) {
				return true;
			}

		}
		return false;

	}

	private Product getProductDetails(Map<String, String> productReq, Boolean isAdd) {
		Product product = new Product();
		if (isAdd) {
			product.setId(Integer.parseInt(productReq.get("id")));
		} else {
			product.setStatus("true");
		}
		Catagory cat = new Catagory();
		cat.setId(Integer.parseInt(productReq.get("catId")));
		product.setName(productReq.get("name"));
		product.setDescription(productReq.get("description"));
		product.setPrice(Integer.parseInt(productReq.get("price")));
		product.setDescription(productReq.get("description"));
		product.setCatagory(cat);

		return product;

	}

	@Override
	public ResponseEntity<?> deleteProductById(String id) {
		try {
			if (jwtFilter.IsAdmin()) {
				Optional<Product> findById = productDao.findById(Integer.parseInt(id));
				if (findById.isPresent()) {
					productDao.deleteById(Integer.parseInt(id));
					return CafeUtils.getResponseHndler("product Deleted Successfully", HttpStatus.PRECONDITION_FAILED);
				} else {
					return CafeUtils.getResponseHndler("product not found", HttpStatus.PRECONDITION_FAILED);
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
