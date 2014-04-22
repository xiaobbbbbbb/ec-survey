package com.ecarinfo.survey.test;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import junit.SimpleTest;

import org.junit.Assert;

import com.ecarinfo.common.utils.DateUtils;
import com.ecarinfo.persist.service.GenericService;
import com.ecarinfo.survey.po.AlarmInfo;
import com.ecarinfo.survey.service.AlarmInfoService;

public class AlarmInfoTest extends SimpleTest {

	@Resource
	private AlarmInfoService alarmInfoService;

	@Resource
	private GenericService genericService;

	// 保存
	@org.junit.Test
	public void testSave() {
		AlarmInfo dto = new AlarmInfo();
		genericService.save(dto);
		Assert.assertTrue(dto.getId() > 0);
	}

	// 修改
	@org.junit.Test
	public void testUpdate() {
		AlarmInfo dto = new AlarmInfo();
		genericService.save(dto);
		Date date = DateUtils.stringToDate("1988-01-17 12:12:12", "yyyy-MM-dd hh:mm:ss");
		dto.setCreateTime(date);
		genericService.update(dto);
		Integer id = dto.getId();
		AlarmInfo newDto = this.genericService.findByPK(AlarmInfo.class, id);
		Assert.assertEquals(date.getTime(), newDto.getCreateTime().getTime());
	}

	// 删除
	@org.junit.Test
	public void testDelete() {
		AlarmInfo dto = new AlarmInfo();
		genericService.save(dto);
		genericService.deleteEntity(dto);
		Integer id = dto.getId();
		Assert.assertNull(this.genericService.findByPK(AlarmInfo.class, id));

	}

	// 按Id查询
	@org.junit.Test
	public void testFindByPK() {
		AlarmInfo dto = this.genericService.findByPK(AlarmInfo.class, 1);
		Assert.assertTrue(dto.getId() > 0);
		System.err.println(dto);
	}

	// 获取所有
	@org.junit.Test
	public void testFindAll() {
		List<AlarmInfo> dots = this.genericService.findAll(AlarmInfo.class);
		System.err.println("AlarmInfo size:" + dots.size());
		for (AlarmInfo dto : dots) {
			System.err.println(dto);
		}
	}
}
