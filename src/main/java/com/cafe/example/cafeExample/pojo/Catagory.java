package com.cafe.example.cafeExample.pojo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;

@NamedQuery(name = "Catagory.getAllCategory",query = "select c from Catagory c where c.id IN(select p.catagory from Product p where p.status='true')")



@Data
@Entity
@Table(name="Catagory")
@DynamicInsert
@DynamicUpdate
public class Catagory implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String name;
	

}
