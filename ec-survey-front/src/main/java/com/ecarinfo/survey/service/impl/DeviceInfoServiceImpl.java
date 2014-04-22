package com.ecarinfo.survey.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.criteria.Criteria.CondtionSeparator;
import com.ecarinfo.persist.criteria.Criteria.OrderType;
import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.persist.paging.PagingManager;
import com.ecarinfo.ralasafe.dto.Constant;
import com.ecarinfo.ralasafe.dto.SystemContext;
import com.ecarinfo.ralasafe.utils.EcUtil;
import com.ecarinfo.survey.dao.DeviceInfoDao;
import com.ecarinfo.survey.po.DeviceInfo;
import com.ecarinfo.survey.rm.DeviceInfoRM;
import com.ecarinfo.survey.service.DeviceInfoService;

@Service("deviceInfoService")
public class DeviceInfoServiceImpl implements DeviceInfoService {

	@Resource
	private DeviceInfoDao deviceInfoDao;

	
	@Override
	public ECPage<DeviceInfo> queryDeviceInfoPages(Integer disabled, String serialNo, Boolean online) {
		int pagerOffset = SystemContext.getPageOffset();
		Criteria whereBy = new Criteria();
		// 是否停用
		if (disabled == null) {
			whereBy.eq(DeviceInfoRM.disabled, 1);
		} else {
			if (disabled == -1) {
				whereBy.greateThenOrEquals(DeviceInfoRM.disabled, 0);
			} else {
				whereBy.eq(DeviceInfoRM.disabled, disabled);
			}
		}

		if (StringUtils.isNotEmpty(serialNo)) {
			whereBy.like(DeviceInfoRM.serialNo, "%" + serialNo + "%", CondtionSeparator.AND);
		}
		if (online != null) {
			whereBy.eq(DeviceInfoRM.online, online, CondtionSeparator.AND);
		}
		
		whereBy.eq(DeviceInfoRM.orgId, EcUtil.getCurrentUser().getOrgId(), CondtionSeparator.AND);
		whereBy.setPage(pagerOffset, Constant.DEFAULT_SIZE).orderBy(DeviceInfoRM.pk, OrderType.ASC);
		ECPage<DeviceInfo> page = PagingManager.list(deviceInfoDao, whereBy);
		return page;
	}
}
