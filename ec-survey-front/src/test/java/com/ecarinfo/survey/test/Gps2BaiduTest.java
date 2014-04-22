package com.ecarinfo.survey.test;

import javax.annotation.Resource;

import org.junit.Test;

import junit.SimpleTest;

import com.ecarinfo.common.utils.BaiduUtils;
import com.ecarinfo.common.utils.PropUtil;
import com.ecarinfo.common.utils.BaiduUtils.BaiduFromGpsResult;
import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.criteria.Criteria.OrderType;
import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.persist.paging.PagingManager;
import com.ecarinfo.persist.service.GenericService;
import com.ecarinfo.survey.po.DeviceData;
import com.ecarinfo.survey.rm.DeviceDataRM;

public class Gps2BaiduTest extends SimpleTest {
	@Resource
	private GenericService genericService;
	private static final String AK = PropUtil.getProp("server.properties", "baidu_key");
	@Test
	public void test() {
		logger.info("DeviceDataJob.execute");
		int pageNo = 1;
		while(true) {
			ECPage<DeviceData> page = PagingManager.list(genericService, DeviceData.class, new Criteria()
			.orderBy(DeviceDataRM.id, OrderType.DESC)
			.eq(DeviceDataRM.baiduLatitude, 0)
			.setPage(pageNo++, ECPage.DEFAULT_SIZE));
			
			logger.info("page.getList().size()="+page.getList().size());
			if(page.getList().size() == 0) {
				break;
			} 
			for(DeviceData d:page.getList()) {
				BaiduFromGpsResult result = BaiduUtils.getBaiduFromGps(AK, d.getGpsLongitude(), d.getGpsLatitude());
				if(result.getLocation() != null) {
					logger.info("error="+result.getError()+",lat,lng=("+result.getLocation().getLat()+","+result.getLocation().getLng()+")");
					d.setBaiduLatitude(result.getLocation().getLat());
					d.setBaiduLongitude(result.getLocation().getLng());
					genericService.updateWithCriteria(DeviceData.class, new Criteria()
					.update(DeviceDataRM.baiduLatitude, d.getBaiduLatitude())
					.update(DeviceDataRM.baiduLongitude, d.getBaiduLongitude())
					.eq(DeviceDataRM.id, d.getId()));
				} else {
					logger.info("error="+result.getError());
				}
			}
		}
	}
}
