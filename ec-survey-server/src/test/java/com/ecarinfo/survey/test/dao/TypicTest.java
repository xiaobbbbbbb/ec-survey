package com.ecarinfo.survey.test.dao;

import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;

import com.ecarinfo.persist.service.GenericService;
import com.ecarinfo.persist.util.RowMapperUtils;
import com.ecarinfo.survey.po.DeviceData;
import com.ecarinfo.test.ECTest;

public class TypicTest extends ECTest {
	@Resource
	private GenericService genericService;
	@Test
	public void mysqlPoint() {
		Map<String,Object> map = genericService.findOneBySql("select * from device_data where id=1");
		System.err.println(map);
		DeviceData dd = RowMapperUtils.map2Entity(map, DeviceData.class);
		System.err.println(dd);
	}
}
