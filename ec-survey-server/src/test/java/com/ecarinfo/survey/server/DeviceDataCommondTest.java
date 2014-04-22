package com.ecarinfo.survey.server;

import javax.annotation.Resource;

import org.junit.Test;

import com.ecarinfo.persist.service.GenericService;
import com.ecarinfo.survey.command.impl.DeviceDataCommand;
import com.ecarinfo.survey.vo.GprsVO;
import com.ecarinfo.test.ECTest;

public class DeviceDataCommondTest extends ECTest {
	@Resource
	private GenericService genericService;
	@Resource
	private DeviceDataCommand deviceDataCommand;
	@Test
	public void test() {
		System.err.println(deviceDataCommand);
		
		GprsVO vo = new GprsVO();
		
		deviceDataCommand.execute(null, vo);
	}
	
}
