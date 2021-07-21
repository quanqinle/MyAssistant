package com.quanqinle.myassistant.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quanqinle.myassistant.entity.Result;
import com.quanqinle.myassistant.entity.vo.DynastyVO;
import com.quanqinle.myassistant.service.DynastyService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

/**
 * Read JSON (including chinese dynasty), and save into DB
 * @author quanql
 */
@Controller
@RequestMapping("/readjson")
@Api(value = "ReadJsonController")
public class ReadJsonController {
    private final Logger log = LoggerFactory.getLogger(ReadJsonController.class);

    @Value("classpath:chinese-dynasty.json")
    Resource resourceFile;

    @Autowired
    DynastyService dynastyService;

    @GetMapping("/dynasty")
    @ResponseBody
    public Result<Object> parseDynastyFromJson() {

        try {
            File resource = new ClassPathResource("chinese-dynasty.json").getFile();
            String text = new String(Files.readAllBytes(resource.toPath()));

            ObjectMapper mapper = new ObjectMapper();
            List<DynastyVO> dynastyVOList = Arrays.asList(mapper.readValue(text, DynastyVO[].class));

            dynastyVOList.forEach(System.out::println);

            dynastyService.saveFromJson(dynastyVOList);

            return Result.success(dynastyVOList);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(log);
            return Result.fail(e);
        }

    }

}
