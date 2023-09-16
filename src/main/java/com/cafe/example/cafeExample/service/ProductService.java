package com.cafe.example.cafeExample.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface ProductService {

	ResponseEntity<?> addNewProduct(@RequestBody(required = true) Map<String, String> productAddReq);

	ResponseEntity<?> updateProduct(@RequestBody(required = true) Map<String, String> productUpdateReq);

	ResponseEntity<?> getAllProduct(String filteredValue);

	ResponseEntity<?> updateProductStatus(@RequestBody(required = true) Map<String, String> productStatusReq);

	ResponseEntity<?> getProductByCatagory(@PathVariable("catId") String id);

	ResponseEntity<?> getProductById(@PathVariable("pId") String id);
	
	ResponseEntity<?> deleteProductById(@PathVariable("pId") String id);
	
	

}
