package com.ecarinfo.survey.test;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import junit.SimpleTest;

import org.junit.Assert;

import com.ecarinfo.common.utils.DateUtils;
import com.ecarinfo.persist.service.GenericService;
import com.ecarinfo.survey.po.CarInfo;
import com.ecarinfo.survey.service.CarInfoService;

public class CarInfoTest extends SimpleTest {

	@Resource
	private CarInfoService carInfoService;

	@Resource
	private GenericService genericService;

	// 保存
	@org.junit.Test
	public void testSave() {
		CarInfo dto = new CarInfo();
		genericService.save(dto);
		Assert.assertTrue(dto.getId() > 0);
	}

	// 修改
	@org.junit.Test
	public void testUpdate() {
		CarInfo dto = new CarInfo();
		genericService.save(dto);
		Date date = DateUtils.stringToDate("1988-01-17 12:12:12", "yyyy-MM-dd hh:mm:ss");
		dto.setCreateTime(date);
		genericService.update(dto);
		Integer id = dto.getId();
		CarInfo newDto = this.genericService.findByPK(CarInfo.class, id);
		Assert.assertEquals(date.getTime(), newDto.getCreateTime().getTime());
	}

	// 删除
	@org.junit.Test
	public void testDelete() {
		CarInfo dto = new CarInfo();
		genericService.save(dto);
		genericService.deleteEntity(dto);
		Integer id = dto.getId();
		Assert.assertNull(this.genericService.findByPK(CarInfo.class, id));

	}

	// 按Id查询
	@org.junit.Test
	public void testFindByPK() {
		CarInfo dto = this.genericService.findByPK(CarInfo.class, 1);
		Assert.assertTrue(dto.getId() > 0);
		System.err.println(dto);
	}

	// 获取所有
	@org.junit.Test
	public void testFindAll() {
		List<CarInfo> dots = this.genericService.findAll(CarInfo.class);
		System.err.println("CarInfo size:" + dots.size());
		for (CarInfo dto : dots) {
			System.err.println(dto);
		}
	}
}
