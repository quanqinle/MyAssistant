package com.quanqinle.myworld.entity.po;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 系统字典
 * @author quanql
 */
@Entity
@Data
@Table(name = "sys_dict")
public class SysDict {
	/**
	 * Constructor for jpa
	 */
	public SysDict() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "dict_key")
	private String key;
	@Column(name = "dict_value")
	private String value;
	private int state;
	@Column(name = "dict_comment")
	private String comment;
	@Column(name = "create_time")
	private LocalDateTime createTime;
	@Column(name = "update_time")
	private LocalDateTime updateTime;
}
