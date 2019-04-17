package com.quanqinle.myworld.service.impl;

import com.quanqinle.myworld.dao.SysDictRepository;
import com.quanqinle.myworld.entity.po.SysDict;
import com.quanqinle.myworld.service.SysDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author quanql
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysDictServiceImpl implements SysDictService {

	@Autowired
	SysDictRepository sysdictRepository;

	@Override
	@Cacheable("dict")
	public List<SysDict> getAll() {
		return sysdictRepository.findAll();
	}

	@Override
	@Cacheable("dict")
	public SysDict getSysDict(String key) {
		return sysdictRepository.findByKey(key.toLowerCase());
	}

	@Override
	@Cacheable("dict")
	public String getValue(String key, String defaultValue) {
		SysDict dict = sysdictRepository.findByKey(key.toLowerCase());
		if (dict == null) {
			return defaultValue;
		}
		return dict.getValue();
	}

	@Override
	public boolean isValueTrue(String key) {
		String value = this.getValue(key, "false");
		String expect = "true";
		return expect.equalsIgnoreCase(value);
	}

	@Override
	public SysDict save(SysDict sysdict) {
		String key = sysdict.getKey().toLowerCase();
		sysdict.setKey(key);
		if (this.getSysDict(key) == null) {
			LocalDateTime localDateTime = LocalDateTime.now();
			sysdict.setCreateTime(localDateTime);
			sysdict.setUpdateTime(localDateTime);
		} else {
			LocalDateTime localDateTime = LocalDateTime.now();
			sysdict.setUpdateTime(localDateTime);
		}

		System.out.println(sysdict);
		return sysdictRepository.save(sysdict);
	}

	@Override
	public SysDict save(String key, String value) {
		SysDict dict = this.getSysDict(key);
		if (dict == null) {
			dict = new SysDict();
			dict.setKey(key.toLowerCase());
		}
		dict.setValue(value);
		dict.setState(1);

		return this.save(dict);
	}
}
