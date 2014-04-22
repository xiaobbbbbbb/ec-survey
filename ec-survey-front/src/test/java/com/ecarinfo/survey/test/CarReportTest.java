package com.ecarinfo.survey.test;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import junit.SimpleTest;

import org.junit.Assert;

import com.ecarinfo.common.utils.DateUtils;
import com.ecarinfo.persist.service.GenericService;
import com.ecarinfo.survey.po.CarReport;
import com.ecarinfo.survey.service.CarStatusService;

public class CarReportTest extends SimpleTest {

	@Resource
	private CarStatusService carStatusService;

	@Resource
	private GenericService genericService;

	// 保存
	@org.junit.Test
	public void testSave() {
		CarReport dto = new CarReport();
		genericService.save(dto);
		Assert.assertTrue(dto.getId() > 0);
	}

	// 修改
	@org.junit.Test
	public void testUpdate() {
		CarReport dto = new CarReport();
		genericService.save(dto);
		Date date = DateUtils.stringToDate("1988-01-17 12:12:12", "yyyy-MM-dd hh:mm:ss");
		dto.setCreateTime(date);
		genericService.update(dto);
		Long id = dto.getId();
		CarReport newDto = this.genericService.findByPK(CarReport.class, id);
		Assert.assertEquals(date.getTime(), newDto.getCreateTime().getTime());
	}

	// 删除
	@org.junit.Test
	public void testDelete() {
		CarReport dto = new CarReport();
		genericService.save(dto);
		genericService.deleteEntity(dto);
		Long id = dto.getId();
		Assert.assertNull(this.genericService.findByPK(CarReport.class, id));

	}

	// 按Id查询
	@org.junit.Test
	public void testFindByPK() {
		CarReport dto = this.genericService.findByPK(CarReport.class, 1);
		Assert.assertTrue(dto.getId() > 0);
		System.err.println(dto);
	}

	// 获取所有
	@org.junit.Test
	public void testFindAll() {
		List<CarReport> dots = this.genericService.findAll(CarReport.class);
		System.err.println("CarReport size:" + dots.size());
		for (CarReport dto : dots) {
			System.err.println(dto);
		}
	}
}
