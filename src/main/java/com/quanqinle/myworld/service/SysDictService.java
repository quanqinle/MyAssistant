package com.quanqinle.myworld.service;

import com.quanqinle.myworld.entity.po.SysDict;

import java.util.List;

/**
 * 系统字典、系统参数
 * @author quanql
 */
public interface SysDictService {

	/**
	 * 获取所有字典
	 * @return
	 */
	List<SysDict> getAll();

	/**
	 * 获取1个字典对象
	 * @param key
	 * @return
	 */
	SysDict getSysDict(String key);

	/**
	 * 获取1个字典值
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	String getValue(String key, String defaultValue);

	/**
	 * key的值是否为true
	 * @param key
	 * @return
	 */
	boolean isValueTrue(String key);

	/**
	 * 新增/修改字典
	 * @param sysdict
	 * @return
	 */
	SysDict saveSysDict(SysDict sysdict);

	/**
	 * 新增/修改字典
	 * @param key
	 * @param value
	 * @return
	 */
	SysDict saveSysDict(String key, String value);
}
