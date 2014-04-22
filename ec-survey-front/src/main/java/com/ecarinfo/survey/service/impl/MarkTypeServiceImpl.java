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
import com.ecarinfo.survey.dao.MarkTypeDao;
import com.ecarinfo.survey.po.MarkType;
import com.ecarinfo.survey.rm.DeviceInfoRM;
import com.ecarinfo.survey.rm.MarkTypeRM;
import com.ecarinfo.survey.service.MarkTypeService;

@Service("markTypeService")
public class MarkTypeServiceImpl implements MarkTypeService {

	@Resource
	private MarkTypeDao markTypeDao;

	@Override
	public ECPage<MarkType> queryMarkTypePages(Integer disabled, String name) {
		int pagerOffset = SystemContext.getPageOffset();
		Criteria whereBy = new Criteria();
		// 是否停用
		if (disabled == null) {
			whereBy.eq(DeviceInfoRM.disabled, 1);
		} else {
			if (disabled == -1) {
				whereBy.greateThenOrEquals(MarkTypeRM.disabled, 0);
			} else {
				whereBy.eq(MarkTypeRM.disabled, disabled);
			}
		}
		if (StringUtils.isNotEmpty(name)) {
			whereBy.like(MarkTypeRM.name, "%" + name + "%", CondtionSeparator.AND);
		}
		whereBy.eq(MarkTypeRM.orgId, EcUtil.getCurrentUser().getOrgId(), CondtionSeparator.AND);
		whereBy.setPage(pagerOffset, Constant.DEFAULT_SIZE).orderBy(DeviceInfoRM.pk, OrderType.ASC);
		ECPage<MarkType> page = PagingManager.list(markTypeDao, whereBy);
		return page;
	}

}
