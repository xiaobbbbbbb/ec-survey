package com.ecarinfo.survey.test;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import junit.SimpleTest;

import org.junit.Assert;

import com.ecarinfo.common.utils.DateUtils;
import com.ecarinfo.persist.service.GenericService;
import com.ecarinfo.survey.po.FenceInfo;
import com.ecarinfo.survey.service.FenceInfoService;

public class FenceInfoTest extends SimpleTest {

	@Resource
	private FenceInfoService fenceInfoService;

	@Resource
	private GenericService genericService;

	// 保存
	@org.junit.Test
	public void testSave() {
		FenceInfo dto = new FenceInfo();
		genericService.save(dto);
		Assert.assertTrue(dto.getId() > 0);
	}

	// 修改
	@org.junit.Test
	public void testUpdate() {
		FenceInfo dto = new FenceInfo();
		genericService.save(dto);
		Date date = DateUtils.stringToDate("1988-01-17 12:12:12", "yyyy-MM-dd hh:mm:ss");
		dto.setCreateTime(date);
		genericService.update(dto);
		Integer id = dto.getId();
		FenceInfo newDto = this.genericService.findByPK(FenceInfo.class, id);
		Assert.assertEquals(date.getTime(), newDto.getCreateTime().getTime());
	}

	// 删除
	@org.junit.Test
	public void testDelete() {
		FenceInfo dto = new FenceInfo();
		genericService.save(dto);
		genericService.deleteEntity(dto);
		Integer id = dto.getId();
		Assert.assertNull(this.genericService.findByPK(FenceInfo.class, id));

	}

	// 按Id查询
	@org.junit.Test
	public void testFindByPK() {
		FenceInfo dto = this.genericService.findByPK(FenceInfo.class, 1);
		Assert.assertTrue(dto.getId() > 0);
		System.err.println(dto);
	}

	// 获取所有
	@org.junit.Test
	public void testFindAll() {
		List<FenceInfo> dots = this.genericService.findAll(FenceInfo.class);
		System.err.println("FenceInfo size:" + dots.size());
		for (FenceInfo dto : dots) {
			System.err.println(dto);
		}
	}
}
