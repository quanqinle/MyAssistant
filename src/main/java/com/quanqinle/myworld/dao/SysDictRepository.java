package com.quanqinle.myworld.dao;

import com.quanqinle.myworld.entity.po.SysDict;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 系统字典
 * @author quanql
 */
public interface SysDictRepository extends JpaRepository<SysDict, Integer> {

	/**
	 * 根据key获取
	 * @param key
	 * @return
	 */
	SysDict findByKey(String key);

	@Override
	SysDict save(SysDict sysDict);
}
