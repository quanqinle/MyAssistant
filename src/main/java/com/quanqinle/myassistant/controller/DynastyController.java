package com.quanqinle.myassistant.controller;

import com.quanqinle.myassistant.entity.Result;
import com.quanqinle.myassistant.entity.po.Dynasty;
import com.quanqinle.myassistant.service.DynastyService;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @author quanql
 * @version 2021/3/15
 */
@Controller
@RequestMapping("/dynasty")
@Api
public class DynastyController {

    @Resource
    private DynastyService dynastyService;

    @GetMapping("/list.html")
    public String showList(Model model) {
        model.addAttribute("dynastylist", dynastyService.getAll());
        //properties中设置了缺省.ftl，所以跳转ratelist.ftl
        return "/pages/history/dynasty";
    }

    @GetMapping("/all.json")
    @ResponseBody
    public Result<List<Dynasty>> getAll() {
        List<Dynasty> list = dynastyService.getAll();
        return Result.success(list);
    }

    @PostMapping
    public Result<Dynasty> create(@RequestBody Dynasty record) {
        Dynasty dynasty = dynastyService.insert(record);
        return Result.success(dynasty);
    }

    @PutMapping
    public Result<Dynasty> update(@RequestBody Dynasty record) {
        Dynasty dynasty = dynastyService.update(record);
        return Result.success(dynasty);
    }

    @DeleteMapping("{id}")
    public Result<Void> deleteById(@PathVariable Long id) {
        boolean success = dynastyService.deleteById(id);
        return success ? Result.success() : Result.fail();
    }

    @GetMapping("{id}")
    @ResponseBody
    public Result<Dynasty> queryById(@PathVariable Long id) {
        Optional<Dynasty> optional = dynastyService.queryById(id);
        if (optional.isPresent()) {
            return Result.success(optional.get());
        } else {
            return Result.success();
        }
    }

}