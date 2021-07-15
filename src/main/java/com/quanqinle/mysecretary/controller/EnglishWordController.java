package com.quanqinle.mysecretary.controller;

import com.quanqinle.mysecretary.entity.PageInfo;
import com.quanqinle.mysecretary.service.EnglishWordService;
import com.quanqinle.mysecretary.entity.Result;
import com.quanqinle.mysecretary.entity.po.EnglishWord;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author quanql
 * @version 2021/7/9
 */
@RestController
@RequestMapping("/api/englishWord")
@Api(value = "EnglishWordController", tags = {"英语资料"})
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

    @GetMapping("/all.json")
    @ResponseBody
    @ApiOperation(value = "获取所有英语资料")
    public Result<List<EnglishWord>> queryAll() {
        List all =  englishWordService.queryAll();
        return Result.success(all);
    }

    @GetMapping("/list.json")
    @ResponseBody
    @ApiOperation(value = "获取分页英语资料")
    public Result<Page<EnglishWord>> getList(@RequestParam(name = "type") int type, @RequestParam(name = "page") int pageNum, @RequestParam(name = "limit") int limit) {
//        int pageNum = (int) model.getAttribute("page");
//        int limit = (int) model.getAttribute("limit");
        Page<EnglishWord> data = englishWordService.getList(type, pageNum, limit);
        return Result.success(data);
    }
}
