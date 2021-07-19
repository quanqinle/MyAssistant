package com.quanqinle.myassistant.service;

import com.quanqinle.myassistant.entity.po.EnglishWord;
import org.springframework.data.domain.Page;

import java.util.List;
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
     * @return -
     */
    EnglishWord insert(EnglishWord englishWord);

    /**
     * update
     *
     * @param englishWord a EnglishWord object
     * @return -
     */
    EnglishWord update(EnglishWord englishWord);

    /**
     * query by id
     *
     * @param id EnglishWord id
     * @return -
     */
    Optional<EnglishWord> queryById(Long id);

    /**
     * delete by id
     *
     * @param id EnglishWord id
     * @return -
     */
    boolean deleteById(Long id);

    /**
     * get count of all data
     * @return -
     */
    long getCount();

    /**
     * query all
     *
     * @return -
     */
    List<EnglishWord> queryAll();

    /**
     * query list under page
     * @param type
     * @param pageNum
     * @param limit
     * @return
     */
    Page<EnglishWord> getList(int type, int pageNum, int limit);
}