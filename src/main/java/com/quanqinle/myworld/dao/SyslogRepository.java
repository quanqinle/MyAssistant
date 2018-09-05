package com.quanqinle.myworld.dao;

import com.quanqinle.myworld.entity.po.LogPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * 演示db rest
 * 默认访问url/logPoes，通过path=修改
 * @author quanqinle
 */
@RepositoryRestResource(path = "log")
public interface SyslogRepository extends JpaRepository<LogPo, Long> {
}