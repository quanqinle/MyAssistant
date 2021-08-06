package com.quanqinle.myassistant.controller;

import cn.gb2260.Division;
import cn.gb2260.GB2260;
import cn.gb2260.Revision;
import com.quanqinle.myassistant.enums.RegionType;
import com.quanqinle.myassistant.enums.State;
import com.quanqinle.myassistant.service.ChineseRegionService;
import com.quanqinle.myassistant.entity.Result;
import com.quanqinle.myassistant.entity.po.ChineseRegion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author quanql
 * @version 2021/7/26
 */
@RestController
@RequestMapping("/api/regions")
public class ChineseRegionController {
    private Logger log = LoggerFactory.getLogger(ChineseRegionController.class);

    @Resource
    private ChineseRegionService chineseRegionService;

    @PostMapping
    public Result<ChineseRegion> create(@RequestBody ChineseRegion record) {
        ChineseRegion chineseRegion = chineseRegionService.insert(record);
        return Result.success(chineseRegion);
    }

    @PutMapping
    public Result<ChineseRegion> update(@RequestBody ChineseRegion record) {
        ChineseRegion chineseRegion = chineseRegionService.update(record);
        return Result.success(chineseRegion);
    }

    @DeleteMapping("/id/{id}")
    public Result<Void> deleteById(@PathVariable Long id) {
        boolean success = chineseRegionService.deleteById(id);
        return success ? Result.success() : Result.fail();
    }

    @GetMapping("/id/{id}")
    public Result<ChineseRegion> queryById(@PathVariable Long id) {
        var optional = chineseRegionService.queryById(id);
        if (optional.isPresent()) {
            return Result.success(optional.get());
        } else {
            return Result.success();
        }
    }

    /**
     * Get region list based on region code
     *
     * @param code region code
     * @return region list
     */
    @GetMapping("/code/{code}")
    public Result<List<ChineseRegion>> queryByCode(@PathVariable String code) {
        List<ChineseRegion> regionList = chineseRegionService.getByCode(code);
        return Result.success(regionList);
    }

    /**
     * Parse data and save from file which is organized by revision
     *
     * @param revision revision string, such as 'V2021'
     * @return the data have been saved in db
     */
    @GetMapping("/parse/revision/{revision}")
    public Result<Object> parse(@PathVariable String revision) {
        GB2260 gb2260;
        Revision revisionObj;
        try {
            revisionObj = Revision.valueOf(revision);
            gb2260 = new GB2260(Revision.valueOf(revision));
        } catch (IllegalArgumentException e) {
            return Result.fail("No this revision");
        } catch (NullPointerException e) {
            return Result.fail("Not found the revision data");
        }

        LinkedHashMap<String, String> data = gb2260.getData();

        if (data != null) {
            List<ChineseRegion> regionList = new ArrayList<>();
            LocalDateTime localDateTime = LocalDateTime.now();

            for(Map.Entry<String, String> entry : data.entrySet()) {
                ChineseRegion region = new ChineseRegion();
                String code = entry.getKey();
                region.setCode(code);
                region.setName(entry.getValue());
                region.setRevision(revisionObj.getCode());
                int type;
                if (code.endsWith(gb2260.PROVINCE_END_WITH)) {
                    type = RegionType.PROVINCE.getValue();
                } else if (code.endsWith(gb2260.PREFECTURE_END_WITH)) {
                    type = RegionType.CITY.getValue();
                } else {
                    type = RegionType.COUNTY.getValue();
                }
                region.setType(type);
                Division division = gb2260.getDivision(code);
                region.setProvince(division.getProvince());
                region.setCity(division.getPrefecture());
                region.setState(State.VALID.getValue());
                region.setCreateTime(localDateTime);
                region.setUpdateTime(localDateTime);

                regionList.add(region);
            }

            try {
                chineseRegionService.insertAll(regionList);
            } catch (Exception e) {
                return Result.fail(e.toString());
            }

            return Result.success(regionList);
        } else {
            return Result.success(null);
        }
    }

    /**
     * Get province list based on TODO id
     * @param id -
     * @return province list
     */
    @GetMapping("/province/{id}")
    public Result<List<ChineseRegion>> queryProvinceById(@PathVariable Long id) {
        return Result.success(chineseRegionService.queryProvince());
    }

    /**
     * Get all revision
     *
     * @return revision list
     */
    @GetMapping("/revisions")
    public Result<List<String>> queryRevisions() {
        return Result.success(chineseRegionService.getRevisions());
    }

    /**
     * Get all Code, even including the invalid code, with the latest revision
     *
     * @return -
     */
    @GetMapping("/all")
    public Result<List<ChineseRegion>> getAllCodeIncludingInvalidCodeWithLatestRevision() {
        return Result.success(chineseRegionService.getAllCodeIncludingInvalidCodeWithLatestRevision());
    }

}
