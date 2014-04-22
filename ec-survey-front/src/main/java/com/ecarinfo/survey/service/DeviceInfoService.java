package com.ecarinfo.survey.service;

import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.survey.po.DeviceInfo;

public interface DeviceInfoService {

	ECPage<DeviceInfo> queryDeviceInfoPages(Integer disabled, String serialNo, Boolean online);
}
