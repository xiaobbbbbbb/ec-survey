package com.ecarinfo.survey.service.impl;

import java.util.Date;

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
import com.ecarinfo.ralasafe.po.RalUser;
import com.ecarinfo.ralasafe.utils.EcUtil;
import com.ecarinfo.survey.dao.SystemLogDao;
import com.ecarinfo.survey.po.SystemLog;
import com.ecarinfo.survey.rm.SystemLogRM;
import com.ecarinfo.survey.service.SystemLogService;
import com.ecarinfo.utils.IPUtil;

@Service("systemLogService")
public class SystemLogServiceImpl implements SystemLogService {

	@Resource
	private SystemLogDao systemLogDao;

	// 保存日志
	public void saveLog(SystemLog dto) {
		RalUser user = EcUtil.getCurrentUser();
		SystemLog log = new SystemLog();
		log.setCreateTime(new Date());
		log.setAction(dto.getAction());
		log.setIp(IPUtil.getInetAddress());
		log.setType(dto.getType());
		log.setUserId((long) user.getUserId());
		log.setOrgId(user.getOrgId());
		log.setUserName(user.getName());
		log.setOrgId(user.getOrgId());
		systemLogDao.insert(log);
	}

	// 分页
	public ECPage<SystemLog> querySystemLogPages(Integer orgId, String userName, String action, String type, String startDate, String endDate) {
		int pagerOffset = SystemContext.getPageOffset();
		Criteria whereBy = new Criteria();
		whereBy.eq(SystemLogRM.orgId, orgId);
		if (StringUtils.isNotEmpty(userName)) {
			whereBy.like(SystemLogRM.userName, "%" + userName + "%", CondtionSeparator.AND);
		}
		if (StringUtils.isNotEmpty(action)) {
			whereBy.like(SystemLogRM.action, "%" + action + "%", CondtionSeparator.AND);
		}
		if (StringUtils.isNotEmpty(type)) {
			whereBy.like(SystemLogRM.type, "%" + type + "%", CondtionSeparator.AND);
		}
		if (StringUtils.isNotEmpty(startDate)) {
			whereBy.greateThenOrEquals(SystemLogRM.createTime, startDate + " 00:00:00", CondtionSeparator.AND);
		}
		if (StringUtils.isNotEmpty(endDate)) {
			whereBy.lessThenOrEquals(SystemLogRM.createTime, endDate + " 23:59:59", CondtionSeparator.AND);
		}
		whereBy.setPage(pagerOffset, Constant.DEFAULT_SIZE).orderBy(SystemLogRM.pk, OrderType.ASC);
		ECPage<SystemLog> page = PagingManager.list(systemLogDao, whereBy);
		return page;
	}
}
