package com.quanqinle.myworld.entity.po;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class EstateCommunity {
	/**
	 * Constructor for jpa
	 */
	protected EstateCommunity() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String xqid;

	private String xqmc;

}
