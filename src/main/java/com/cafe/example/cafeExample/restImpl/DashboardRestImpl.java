package com.cafe.example.cafeExample.restImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.cafe.example.cafeExample.constant.CafeConstant;
import com.cafe.example.cafeExample.rest.DashboardRest;
import com.cafe.example.cafeExample.service.DashboardService;
import com.cafe.example.cafeExample.utils.CafeUtils;

@RestController
public class DashboardRestImpl implements DashboardRest{

	@Autowired
	DashboardService dashboard;
	
	
	@Override
	public ResponseEntity<?> getDetailsCount() {
		try {
			return dashboard.getDetailsCount();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return CafeUtils.getResponseHndler(CafeConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
