package com.quanqinle.myassistant.dao;

import com.quanqinle.myassistant.entity.po.Dynasty;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author quanql
 */
public interface DynastyRepository extends JpaRepository<Dynasty, Long> {
    Dynasty findByName(String name);
}
