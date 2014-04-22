package com.ecarinfo.survey.test;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import junit.SimpleTest;

import org.junit.Assert;

import com.ecarinfo.common.utils.DateUtils;
import com.ecarinfo.persist.service.GenericService;
import com.ecarinfo.survey.po.EventInfo;
import com.ecarinfo.survey.service.EventInfoService;

public class EventInfoTest extends SimpleTest {

	@Resource
	private EventInfoService eventInfoService;

	@Resource
	private GenericService genericService;

	// 保存
	@org.junit.Test
	public void testSave() {
		EventInfo dto = new EventInfo();
		genericService.save(dto);
		Assert.assertTrue(dto.getId() > 0);
	}

	// 修改
	@org.junit.Test
	public void testUpdate() {
		EventInfo dto = new EventInfo();
		genericService.save(dto);
		Date date = DateUtils.stringToDate("1988-01-17 12:12:12", "yyyy-MM-dd hh:mm:ss");
		dto.setCreateTime(date);
		genericService.update(dto);
		Long id = dto.getId();
		EventInfo newDto = this.genericService.findByPK(EventInfo.class, id);
		Assert.assertEquals(date.getTime(), newDto.getCreateTime().getTime());
	}

	// 删除
	@org.junit.Test
	public void testDelete() {
		EventInfo dto = new EventInfo();
		genericService.save(dto);
		genericService.deleteEntity(dto);
		Long id = dto.getId();
		Assert.assertNull(this.genericService.findByPK(EventInfo.class, id));

	}

	// 按Id查询
	@org.junit.Test
	public void testFindByPK() {
		EventInfo dto = this.genericService.findByPK(EventInfo.class, 1);
		Assert.assertTrue(dto.getId() > 0);
		System.err.println(dto);
	}

	// 获取所有
	@org.junit.Test
	public void testFindAll() {
		List<EventInfo> dots = this.genericService.findAll(EventInfo.class);
		System.err.println("EventInfo size:" + dots.size());
		for (EventInfo dto : dots) {
			System.err.println(dto);
		}
	}
}
