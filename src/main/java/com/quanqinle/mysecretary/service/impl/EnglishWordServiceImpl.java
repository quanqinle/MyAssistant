package com.quanqinle.mysecretary.service.impl;

import com.quanqinle.mysecretary.dao.EnglishWordRepository;
import com.quanqinle.mysecretary.entity.po.EnglishWord;
import com.quanqinle.mysecretary.service.EnglishWordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author quanql
 * @version 2021/7/9
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class EnglishWordServiceImpl implements EnglishWordService {

    private Logger log = LoggerFactory.getLogger(EnglishWordService.class);

    @Autowired
    private EnglishWordRepository repository;

    public EnglishWordServiceImpl() {
    }

    @Override
    public EnglishWord insert(EnglishWord englishWord) {
        return repository.save(englishWord);
    }

    @Override
    public EnglishWord update(EnglishWord englishWord) {
        return repository.save(englishWord);
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
    public Optional<EnglishWord> queryById(Long id) {
        return repository.findById(id);
    }

}