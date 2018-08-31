package com.quanqinle.myworld.demo.batchjob;

import org.springframework.batch.item.validator.ValidatingItemProcessor;
import org.springframework.batch.item.validator.ValidationException;

/**
 * 数据处理
 */
public class CsvItemProcessor extends ValidatingItemProcessor<CsvPerson> {
	@Override
	public CsvPerson process(CsvPerson item) throws ValidationException {
		super.process(item); // 需要此才会调用自定义校验器

		if (item.getNation().equals("汉族")) {
			item.setNation("01");
		} else {
			item.setNation("02");
		}

		return item;
	}
}
