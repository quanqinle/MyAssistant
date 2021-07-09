package com.quanqinle.mysecretary.service;

import com.quanqinle.mysecretary.entity.po.EnglishWord;

import java.util.Optional;

/**
 * @author quanql
 * @version 2021/7/9
 */
public interface EnglishWordService {

    /**
     * insert
     *
     * @param englishWord a EnglishWord object
     * @return
     */
    EnglishWord insert(EnglishWord englishWord);

    /**
     * update
     *
     * @param englishWord a EnglishWord object
     * @return
     */
    EnglishWord update(EnglishWord englishWord);

    /**
     * query by id
     *
     * @param id EnglishWord id
     * @return
     */
    Optional<EnglishWord> queryById(Long id);

    /**
     * delete by id
     *
     * @param id EnglishWord id
     * @return
     */
    boolean deleteById(Long id);

}