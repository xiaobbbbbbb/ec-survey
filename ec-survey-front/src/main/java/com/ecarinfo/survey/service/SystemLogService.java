package com.ecarinfo.survey.service;

import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.survey.po.SystemLog;

public interface SystemLogService {

	// 保存日志
	void saveLog(SystemLog dto);

	ECPage<SystemLog> querySystemLogPages(Integer orgId, String userName, String action, String type, String startDate, String endDate);
}
