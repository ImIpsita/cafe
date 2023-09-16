package com.cafe.example.cafeExample.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface BillService {

	ResponseEntity<?> generatedReport(@RequestBody(required = true)Map<String,Object> billmapReq);
	
	ResponseEntity<?> getBillList();
	
	ResponseEntity<?> getPdfDownload(@RequestBody(required = true)Map<String,Object> billmapReq);
	
	ResponseEntity<?> deleteBill(@PathVariable String id);
}
