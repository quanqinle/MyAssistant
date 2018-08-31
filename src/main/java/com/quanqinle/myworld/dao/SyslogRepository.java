package com.quanqinle.myworld.dao;

import com.quanqinle.myworld.entity.po.LogPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * 演示db rest
 */
@RepositoryRestResource(path = "log") // 默认访问url/logPoes
public interface SyslogRepository extends JpaRepository<LogPo, Long> {
}
