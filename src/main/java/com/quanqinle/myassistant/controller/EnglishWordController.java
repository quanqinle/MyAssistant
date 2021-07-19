package com.quanqinle.myassistant.controller;

import com.quanqinle.myassistant.entity.PageInfo;
import com.quanqinle.myassistant.service.EnglishWordService;
import com.quanqinle.myassistant.entity.Result;
import com.quanqinle.myassistant.entity.po.EnglishWord;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author quanql
 * @version 2021/7/9
 */
@Controller
@RequestMapping("/english")
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

    /**
     * 获取分页英语资料
     * @param model -
     * @param type 资料类型
     * @param pageNum 当前页数，从0开始
     * @param limit 每页数据量
     * @return -
     */
    @GetMapping("/list.html")
    public String getList(Model model,
                          @RequestParam(name = "type", defaultValue = "4") int type,
                          @RequestParam(name = "page", defaultValue = "0") int pageNum,
                          @RequestParam(name = "limit", defaultValue = "10") int limit) {
        Page<EnglishWord> data = englishWordService.getList(type, pageNum, limit);
        model.addAttribute("result", Result.success(data.getContent(), new PageInfo(data)));
        return "/pages/english/list";
    }

    @GetMapping("/list.json")
    @ResponseBody
    @ApiOperation(value = "获取分页英语资料")
    public Result<List<EnglishWord>> getList(
                          @RequestParam(name = "type", defaultValue = "4") int type,
                          @RequestParam(name = "page", defaultValue = "0") int pageNum,
                          @RequestParam(name = "limit", defaultValue = "10") int limit) {
        Page<EnglishWord> data = englishWordService.getList(type, pageNum, limit);
        return Result.success(data.getContent(), new PageInfo(data));
    }
}
