package com.ecarinfo.survey.test;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import junit.SimpleTest;

import org.junit.Assert;

import com.ecarinfo.common.utils.DateUtils;
import com.ecarinfo.persist.service.GenericService;
import com.ecarinfo.survey.po.DeviceInfo;
import com.ecarinfo.survey.service.DeviceInfoService;

public class DeviceInfoTest extends SimpleTest {

	@Resource
	private DeviceInfoService deviceInfoService;

	@Resource
	private GenericService genericService;

	// 保存
	@org.junit.Test
	public void testSave() {
		DeviceInfo dto = new DeviceInfo();
		genericService.save(dto);
		Assert.assertTrue(dto.getId() > 0);
	}

	// 修改
	@org.junit.Test
	public void testUpdate() {
		DeviceInfo dto = new DeviceInfo();
		genericService.save(dto);
		Date date = DateUtils.stringToDate("1988-01-17 12:12:12", "yyyy-MM-dd hh:mm:ss");
		dto.setCreateTime(date);
		genericService.update(dto);
		Integer id = dto.getId();
		DeviceInfo newDto = this.genericService.findByPK(DeviceInfo.class, id);
		Assert.assertEquals(date.getTime(), newDto.getCreateTime().getTime());
	}

	// 删除
	@org.junit.Test
	public void testDelete() {
		DeviceInfo dto = new DeviceInfo();
		genericService.save(dto);
		genericService.deleteEntity(dto);
		Integer id = dto.getId();
		Assert.assertNull(this.genericService.findByPK(DeviceInfo.class, id));

	}

	// 按Id查询
	@org.junit.Test
	public void testFindByPK() {
		DeviceInfo dto = this.genericService.findByPK(DeviceInfo.class, 1);
		Assert.assertTrue(dto.getId() > 0);
		System.err.println(dto);
	}

	// 获取所有
	@org.junit.Test
	public void testFindAll() {
		List<DeviceInfo> dots = this.genericService.findAll(DeviceInfo.class);
		System.err.println("DeviceInfo size:" + dots.size());
		for (DeviceInfo dto : dots) {
			System.err.println(dto);
		}
	}
}
