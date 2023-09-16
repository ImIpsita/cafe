package com.cafe.example.cafeExample.restImpl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cafe.example.cafeExample.constant.CafeConstant;
import com.cafe.example.cafeExample.rest.BillRest;
import com.cafe.example.cafeExample.service.BillService;
import com.cafe.example.cafeExample.utils.CafeUtils;

@RestController

public class BillRestImpl implements BillRest{
	
	@Autowired
	BillService billservice;

	@Override
	public ResponseEntity<?> generatedReport(Map<String, Object> billmapReq) {
		try {
			return billservice.generatedReport(billmapReq);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return CafeUtils.getResponseHndler(CafeConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<?> getBillList() {
		try {
			return billservice.getBillList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return CafeUtils.getResponseHndler(CafeConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<?> getPdfDownload(Map<String, Object> billmapReq) {
		try {
			return billservice.getPdfDownload(billmapReq);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return CafeUtils.getResponseHndler(CafeConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<?> deleteBill(String id) {
		try {
			return billservice.deleteBill(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return CafeUtils.getResponseHndler(CafeConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	
	
	
	

}
