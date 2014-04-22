package com.ecarinfo.survey.server;

import javax.annotation.Resource;

import org.junit.Test;

import com.ecarinfo.persist.service.GenericService;
import com.ecarinfo.survey.command.impl.DeviceDataCommand;
import com.ecarinfo.test.ECTest;

/**
 * Unit test for simple App.
 */
public class AppTest extends ECTest {
	
	@Resource
	private GenericService genericService;
	@Resource
	private DeviceDataCommand deviceDataCommand;
	@Test
	public void deviceData() {
		
//		deviceDataCommand.execute(null, requestMessage);
	}
}
