package com.quanqinle.mysecretary.controller;

import com.quanqinle.mysecretary.service.EnglishWordService;
import com.quanqinle.mysecretary.entity.Result;
import com.quanqinle.mysecretary.entity.po.EnglishWord;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author quanql
 * @version 2021/7/9
 */
@RestController
@RequestMapping("api/englishWord")
public class EnglishWordController {

    @Resource
    private EnglishWordService englishWordService;

    @PostMapping
    public Result<EnglishWord> create(@RequestBody EnglishWord record) {
        EnglishWord englishWord = englishWordService.insert(record);
        return Result.success(englishWord);
    }

    @PutMapping
    public Result<EnglishWord> update(@RequestBody EnglishWord record) {
        EnglishWord englishWord = englishWordService.update(record);
        return Result.success(englishWord);
    }

    @DeleteMapping("{id}")
    public Result<Void> deleteById(@PathVariable Long id) {
        boolean success = englishWordService.deleteById(id);
        return success ? Result.success() : Result.fail();
    }

    @GetMapping("{id}")
    public Result<EnglishWord> queryById(@PathVariable Long id) {
        var optional = englishWordService.queryById(id);
        if (optional.isPresent()) {
            return Result.success(optional.get());
        } else {
            return Result.success();
        }
    }

}