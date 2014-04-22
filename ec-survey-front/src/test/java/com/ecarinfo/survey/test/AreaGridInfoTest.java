package com.ecarinfo.survey.test;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import junit.SimpleTest;

import org.junit.Assert;

import com.ecarinfo.common.utils.DateUtils;
import com.ecarinfo.persist.service.GenericService;
import com.ecarinfo.survey.po.AreaGridInfo;
import com.ecarinfo.survey.service.AreaGridInfoService;

public class AreaGridInfoTest extends SimpleTest {

	@Resource
	private AreaGridInfoService areaGridInfoService;

	@Resource
	private GenericService genericService;

	// 保存
	@org.junit.Test
	public void testSave() {
		AreaGridInfo dto = new AreaGridInfo();
		genericService.save(dto);
		Assert.assertTrue(dto.getId() > 0);
	}

	// 修改
	@org.junit.Test
	public void testUpdate() {
		AreaGridInfo dto = new AreaGridInfo();
		genericService.save(dto);
		Date date = DateUtils.stringToDate("1988-01-17 12:12:12", "yyyy-MM-dd hh:mm:ss");
		dto.setCreateTime(date);
		genericService.update(dto);
		Integer id = dto.getId();
		AreaGridInfo newDto = this.genericService.findByPK(AreaGridInfo.class, id);
		Assert.assertEquals(date.getTime(), newDto.getCreateTime().getTime());
	}

	// 删除
	@org.junit.Test
	public void testDelete() {
		AreaGridInfo dto = new AreaGridInfo();
		genericService.save(dto);
		genericService.deleteEntity(dto);
		Integer id = dto.getId();
		Assert.assertNull(this.genericService.findByPK(AreaGridInfo.class, id));

	}

	// 按Id查询
	@org.junit.Test
	public void testFindByPK() {
		AreaGridInfo dto = this.genericService.findByPK(AreaGridInfo.class, 1);
		Assert.assertTrue(dto.getId() > 0);
		System.err.println(dto);
	}

	// 获取所有
	@org.junit.Test
	public void testFindAll() {
		List<AreaGridInfo> dots = this.genericService.findAll(AreaGridInfo.class);
		System.err.println("AreaGridInfo size:" + dots.size());
		for (AreaGridInfo dto : dots) {
			System.err.println(dto);
		}
	}
}
