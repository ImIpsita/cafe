package com.cafe.example.cafeExample.pojo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;

@NamedQuery(name="Product.getAllProduct" ,query = "select new com.cafe.example.cafeExample.Wrapper.productWrapper(p.id,p.name,p.price,p.description,p.status,p.catagory.id,p.catagory.name) from Product p ")
@NamedQuery(name="Product.updateProductStatus" ,query = "update Product p set p.status =:status where p.id=:id ")
@NamedQuery(name="Product.getProductByCatagoryId" ,query = "select new com.cafe.example.cafeExample.Wrapper.productWrapper(p.id,p.name) from Product p where p.catagory.id=:id and p.status='true'")
@NamedQuery(name="Product.ProductgetById" ,query = "select new com.cafe.example.cafeExample.Wrapper.productWrapper(p.id,p.name,p.price,p.description,p.status) from Product p where p.id=:id")



@Data
@Entity
@Table(name="Product")
@DynamicInsert
@DynamicUpdate
public class Product implements Serializable {
	
	public static final long serialVersionUID =1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private Integer price;
	private String description;
	private String status;
	@ManyToOne
	@JoinColumn(name = "Cata_fk",nullable = false)
	private Catagory catagory;
	
	

}
