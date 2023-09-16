package com.cafe.example.cafeExample.rest;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/product")
public interface ProductRest {
	
	@PostMapping("add")
	ResponseEntity<?> addNewProduct(@RequestBody(required = true ) Map<String,String>  productAddReq);
	
	@PutMapping("update")
	ResponseEntity<?> updateProduct(@RequestBody(required = true ) Map<String,String>  productUpdateReq);
	
	@GetMapping("/get")
	ResponseEntity<?> getAllProduct(@RequestParam(required = false) String filteredValue);
	
	@PutMapping("/updateStatus")
	ResponseEntity<?> updateProductStatus(@RequestBody(required = true ) Map<String,String>  productStatusReq);
	
	@GetMapping("/getProductByCatagory/{catId}")
	ResponseEntity<?> getProductByCatagory(@PathVariable("catId") String id);
	
	@GetMapping("/getProductById/{pId}")
	ResponseEntity<?> getProductById(@PathVariable("pId") String id);
	
	@DeleteMapping("/deleteProductById/{pId}")
	ResponseEntity<?> deleteProductById(@PathVariable("pId") String id);

}
