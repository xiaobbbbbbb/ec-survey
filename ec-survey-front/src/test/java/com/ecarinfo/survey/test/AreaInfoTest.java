package com.ecarinfo.survey.test;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import junit.SimpleTest;

import org.junit.Assert;

import com.ecarinfo.common.utils.DateUtils;
import com.ecarinfo.persist.service.GenericService;
import com.ecarinfo.survey.po.AreaInfo;
import com.ecarinfo.survey.service.AreaInfoService;

public class AreaInfoTest extends SimpleTest {

	@Resource
	private AreaInfoService areaInfoService;

	@Resource
	private GenericService genericService;

	// 保存
	@org.junit.Test
	public void testSave() {
		AreaInfo dto = new AreaInfo();
		genericService.save(dto);
		Assert.assertTrue(dto.getId() > 0);
	}

	// 修改
	@org.junit.Test
	public void testUpdate() {
		AreaInfo dto = new AreaInfo();
		genericService.save(dto);
		Date date = DateUtils.stringToDate("1988-01-17 12:12:12", "yyyy-MM-dd hh:mm:ss");
		dto.setCreateTime(date);
		genericService.update(dto);
		Integer id = dto.getId();
		AreaInfo newDto = this.genericService.findByPK(AreaInfo.class, id);
		Assert.assertEquals(date.getTime(), newDto.getCreateTime().getTime());
	}

	// 删除
	@org.junit.Test
	public void testDelete() {
		AreaInfo dto = new AreaInfo();
		genericService.save(dto);
		genericService.deleteEntity(dto);
		Integer id = dto.getId();
		Assert.assertNull(this.genericService.findByPK(AreaInfo.class, id));

	}

	// 按Id查询
	@org.junit.Test
	public void testFindByPK() {
		AreaInfo dto = this.genericService.findByPK(AreaInfo.class, 1);
		Assert.assertTrue(dto.getId() > 0);
		System.err.println(dto);
	}

	// 获取所有
	@org.junit.Test
	public void testFindAll() {
		List<AreaInfo> dots = this.genericService.findAll(AreaInfo.class);
		System.err.println("AreaInfo size:" + dots.size());
		for (AreaInfo dto : dots) {
			System.err.println(dto);
		}
	}
}
