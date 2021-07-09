package com.quanqinle.mysecretary.dao;

import com.quanqinle.mysecretary.entity.po.EnglishWord;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author quanql
 * @version 2021/7/9
 */
@Repository
public interface EnglishWordRepository extends JpaRepository<EnglishWord, Long> {
}