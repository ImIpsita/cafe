package com.cafe.example.cafeExample.utils;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.google.common.base.Strings;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

public class CafeUtils {

	public static ResponseEntity<String> getResponseHndler(String responseMessage, HttpStatus status) {
		return new ResponseEntity<String>("{\"message\":\"" + responseMessage + "\"}", status);

	}

	public static String getuuid() {
		Date date = new Date();
		long time = date.getTime();
		return "Bill"+time;

	}
      public static JSONArray getJsonArray(String data) throws JSONException {
		return new JSONArray(data);
    	  
      }
      
      @SuppressWarnings("serial")
	public static Map<String,Object> getMapFromJson(String data){
    	  
    	  if(Strings.isNullOrEmpty(data)) {
    		  return new HashMap<String, Object>(); 
   	  }
    	  //convert json to map<string,object> type
    	  return new Gson().fromJson(data, new TypeToken<Map<String,Object>>(){
    		  
    	  }.getType()) ;
		}
      
      public static Boolean isFileExist(String filepath) {
    	  File file=new File(filepath);
    	  return null!=file && file.exists()?true:false;
      }
    	  
      
}
