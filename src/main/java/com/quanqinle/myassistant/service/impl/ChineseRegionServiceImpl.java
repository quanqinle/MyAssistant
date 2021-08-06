package com.quanqinle.myassistant.service.impl;

import com.quanqinle.myassistant.dao.ChineseRegionRepository;
import com.quanqinle.myassistant.entity.po.ChineseRegion;
import com.quanqinle.myassistant.enums.RegionType;
import com.quanqinle.myassistant.service.ChineseRegionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author quanql
 * @version 2021/7/26
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ChineseRegionServiceImpl implements ChineseRegionService {

    private Logger log = LoggerFactory.getLogger(ChineseRegionService.class);

    @Autowired
    private ChineseRegionRepository repository;

    public ChineseRegionServiceImpl() {
    }

    @Override
    public ChineseRegion insert(ChineseRegion chineseRegion) {
        return repository.save(chineseRegion);
    }

    @Override
    public ChineseRegion update(ChineseRegion chineseRegion) {
        return repository.save(chineseRegion);
    }

    @Override
    public boolean deleteById(Long id) {
        boolean result = true;
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            result = false;
        }
        return result;
    }

    @Override
    public Optional<ChineseRegion> queryById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<ChineseRegion> getByCode(String code) {
        return repository.findByCodeOrderByRevisionDesc(code);
    }

    /**
     * Insert all
     *
     * @param chineseRegions a ChineseRegion list
     * @return
     */
    @Override
    public List<ChineseRegion> insertAll(List<ChineseRegion> chineseRegions) {
        return repository.saveAll(chineseRegions);
    }

    /**
     * query all provinces
     *
     * @return
     */
    @Override
    public List<ChineseRegion> queryProvince() {
        List<String> revisions = repository.findRevisionsOrderByRevisionDesc();
        return repository.findByRevisionAndType(revisions.get(0), RegionType.PROVINCE.getValue());
    }

    /**
     * Get all revision
     *
     * @return revision list
     */
    @Override
    public List<String> getRevisions() {
        return repository.findRevisionsOrderByRevisionDesc();
    }

    /**
     * Get all Code, even including the invalid code, with the latest revision
     *
     * @return region list
     */
    @Override
    public List<ChineseRegion> getAllCodeIncludingInvalidCodeWithLatestRevision() {
        return repository.getAllWithLatestRevision();
    }
}