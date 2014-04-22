package com.ecarinfo.survey.test;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import junit.SimpleTest;

import org.junit.Assert;

import com.ecarinfo.common.utils.DateUtils;
import com.ecarinfo.persist.service.GenericService;
import com.ecarinfo.survey.po.CityInfo;
import com.ecarinfo.survey.service.CityInfoService;

public class CityInfoTest extends SimpleTest {

	@Resource
	private CityInfoService cityInfoService;

	@Resource
	private GenericService genericService;

	// 保存
	@org.junit.Test
	public void testSave() {
		CityInfo dto = new CityInfo();
		genericService.save(dto);
		Assert.assertTrue(dto.getId() > 0);
	}

	// 修改
	@org.junit.Test
	public void testUpdate() {
		CityInfo dto = new CityInfo();
		genericService.save(dto);
		Date date = DateUtils.stringToDate("1988-01-17 12:12:12", "yyyy-MM-dd hh:mm:ss");
		dto.setCreateTime(date);
		genericService.update(dto);
		Integer id = dto.getId();
		CityInfo newDto = this.genericService.findByPK(CityInfo.class, id);
		Assert.assertEquals(date.getTime(), newDto.getCreateTime().getTime());
	}

	// 删除
	@org.junit.Test
	public void testDelete() {
		CityInfo dto = new CityInfo();
		genericService.save(dto);
		genericService.deleteEntity(dto);
		Integer id = dto.getId();
		Assert.assertNull(this.genericService.findByPK(CityInfo.class, id));

	}

	// 按Id查询
	@org.junit.Test
	public void testFindByPK() {
		CityInfo dto = this.genericService.findByPK(CityInfo.class, 1);
		Assert.assertTrue(dto.getId() > 0);
		System.err.println(dto);
	}

	// 获取所有
	@org.junit.Test
	public void testFindAll() {
		List<CityInfo> dots = this.genericService.findAll(CityInfo.class);
		System.err.println("CityInfo size:" + dots.size());
		for (CityInfo dto : dots) {
			System.err.println(dto);
		}
	}
}
