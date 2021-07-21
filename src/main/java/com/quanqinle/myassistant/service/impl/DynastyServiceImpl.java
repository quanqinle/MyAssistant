package com.quanqinle.myassistant.service.impl;

import com.quanqinle.myassistant.dao.DynastyRepository;
import com.quanqinle.myassistant.entity.po.Dynasty;
import com.quanqinle.myassistant.entity.vo.DynastyVO;
import com.quanqinle.myassistant.service.DynastyService;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author quanql
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DynastyServiceImpl implements DynastyService {

    private Logger log = LoggerFactory.getLogger(DynastyService.class);

    @Autowired
    private Mapper mapper;

    @Autowired
    private DynastyRepository dynastyRepository;

    public DynastyServiceImpl() {
    }

    @Override
    public Dynasty insert(Dynasty dynasty) {
        return dynastyRepository.save(dynasty);
    }

    @Override
    public Dynasty update(Dynasty dynasty) {
        return dynastyRepository.save(dynasty);
    }

    @Override
    public boolean deleteById(Long id) {
        Boolean result = true;
        try {
            dynastyRepository.deleteById(id);
        } catch (Exception e) {
            result = false;
        }
        return result;
    }

    @Override
    public Optional<Dynasty> queryById(Long id) {
        return dynastyRepository.findById(id);
    }

    @Override
    public void saveFromJson(List<DynastyVO> dynastyVOList) {
        LocalDateTime localDateTime = LocalDateTime.now();

        List<Dynasty> targetList = new ArrayList<>();
        for (DynastyVO vo : dynastyVOList) {
            Dynasty po = mapper.map(vo, Dynasty.class);
            String years = vo.getYears();
            List<String> spList = List.of("-", "~");
            for (String sp: spList) {
                if (years.contains(sp)) {
                    String[] year = years.split(sp);
                    po.setYearStart(year[0].replace("年", "").trim());
                    po.setYearEnd(year[1].replace("年", "").trim());
                }
            }

            po.setCreateTime(localDateTime);
            po.setUpdateTime(localDateTime);

            targetList.add(po);
        }

        dynastyRepository.saveAll(targetList);
    }

    /**
     * @return
     */
    @Override
    public List<Dynasty> getAll() {
        return dynastyRepository.findAll();
    }
}
