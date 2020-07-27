package com.quanqinle.mysecretary.biz.batchjob;

import org.springframework.batch.item.validator.ValidatingItemProcessor;
import org.springframework.batch.item.validator.ValidationException;

/**
 * 数据处理
 *
 * @author quanqinle
 */
public class CsvItemProcessor extends ValidatingItemProcessor<CsvPerson> {
	@Override
	public CsvPerson process(CsvPerson item) throws ValidationException {
		// 需要有此句才会调用自定义校验器
		super.process(item);

		String hanNation = "汉族";
		if (hanNation.equals(item.getNation())) {
			item.setNation("01");
		} else {
			item.setNation("02");
		}

		return item;
	}
}
