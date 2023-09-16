package com.cafe.example.cafeExample.Wrapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(value = Include.NON_NULL)
public class productWrapper {

	
	private Integer id;
	private String name;
	private Integer price;
	private String description;
	private String status;
	private Integer catagoryId;
	private String catagoryName;
	
	public productWrapper(Integer id, String name, Integer price, String description, String status, Integer catagoryId,
			String catagoryName) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.description = description;
		this.status = status;
		this.catagoryId = catagoryId;
		this.catagoryName = catagoryName;
	}

	public productWrapper(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public productWrapper(Integer id, String name, Integer price, String description, String status) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.description = description;
		this.status = status;
	}
	
	
	
	
}
