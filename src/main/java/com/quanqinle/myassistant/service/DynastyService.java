package com.quanqinle.myassistant.service;

import com.quanqinle.myassistant.entity.po.Dynasty;
import com.quanqinle.myassistant.entity.vo.DynastyVO;

import java.util.List;
import java.util.Optional;

/**
 * @author quanql
 */
public interface DynastyService {

    /**
     *
     * @param dynastyVOList -
     */
    void saveFromJson(List<DynastyVO> dynastyVOList);

    /**
     * save
     * @param dynasty -
     * @return
     */
    Dynasty insert(Dynasty dynasty);

    /**
     * update
     * @param dynasty -
     * @return
     */
    Dynasty update(Dynasty dynasty);

    /**
     * delete data by id
     * @param id -
     * @return
     */
    boolean deleteById(Long id);

    /**
     * get data by id
     * @param id -
     * @return
     */
    Optional<Dynasty> queryById(Long id);


    /**
     * get all data
     * @return
     */
    List<Dynasty> getAll();
}
