package com.cafe.example.cafeExample.pojo;

import java.io.Serializable;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;

@NamedQuery(name = "Bill.getAllBillList",query = "select a from Bill a order by a.id desc")
@NamedQuery(name="Bill.getCurrentUserBilList",query = "select a from Bill a where a.createdBy=:username order by a.id desc")

@Data
@Entity
@Table(name="Bill")
@DynamicInsert
@DynamicUpdate

public class Bill implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String uuid;
	private String name;
	private String email;
	private String contactNumber;
	private String paymentMethod;
	private Integer total;
	@Column(length = 10000)
	private String productDetails;
	private String createdBy;
	
    
}
