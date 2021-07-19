package com.quanqinle.myassistant.dao;

import com.quanqinle.myassistant.entity.po.LogPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * 演示db REST
 * 默认访问url/logPoes，通过path=修改
 * @author quanqinle
 */
@RepositoryRestResource(path = "log")
public interface SyslogRepository extends JpaRepository<LogPo, Long> {
}
