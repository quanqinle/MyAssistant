package com.quanqinle.myassistant.service.impl;

import com.quanqinle.myassistant.dao.SysDictRepository;
import com.quanqinle.myassistant.entity.po.SysDict;
import com.quanqinle.myassistant.service.SysDictService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private Logger log = LoggerFactory.getLogger(SysDictServiceImpl.class);

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
	public SysDict saveSysDict(SysDict sysdict) {
		String key = sysdict.getKey().toLowerCase();
		sysdict.setKey(key);

		LocalDateTime localDateTime = LocalDateTime.now();
		sysdict.setUpdateTime(localDateTime);
		if (this.getSysDict(key) == null) {
			sysdict.setCreateTime(localDateTime);
			log.info("new sysdict");
		} else {
			log.info("old sysdict");
		}

		log.info("sysdict: {}", sysdict);
		return sysdictRepository.save(sysdict);
	}

	@Override
	public SysDict saveSysDict(String key, String value) {
		SysDict dict = this.getSysDict(key.toLowerCase());
		if (dict == null) {
			dict = new SysDict();
		}
		dict.setKey(key.toLowerCase());
		dict.setValue(value);
		dict.setState(1);

		return this.saveSysDict(dict);
	}
}
