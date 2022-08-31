/**
 * @author Rongali Jaswant Kumar
 * The class Discount
 * Instantiates a new Discount Coupon
 */
package com.wipro.springboot.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Table(name = "discount")
@Entity
public class Discount {

	@Id
	@Column(name = "id")
	private String id;
	@Column(name = "status")
	private Long status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Discount() {

	}

}
