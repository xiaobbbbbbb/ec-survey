package com.ecarinfo.survey.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.criteria.Criteria.CondtionSeparator;
import com.ecarinfo.persist.criteria.Criteria.OrderType;
import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.persist.util.RowMapperUtils;
import com.ecarinfo.ralasafe.dto.Constant;
import com.ecarinfo.ralasafe.dto.SystemContext;
import com.ecarinfo.ralasafe.utils.EcUtil;
import com.ecarinfo.ralasafe.utils.PageHelper;
import com.ecarinfo.survey.dao.TagInfoDao;
import com.ecarinfo.survey.rm.TagInfoRM;
import com.ecarinfo.survey.service.TagInfoService;
import com.ecarinfo.survey.view.TablePrefix;
import com.ecarinfo.survey.view.TagInfoView;

@Service("tagInfoService")
public class TagInfoServiceImpl implements TagInfoService {

	@Resource
	private TagInfoDao tagInfoDao;

	@Override
	public ECPage<TagInfoView> queryTagInfoPages(Integer disabled, String serialNo, Boolean online) {
		int pagerOffset = SystemContext.getPageOffset();
		Criteria whereBy = new Criteria();
		// 是否停用
		if (disabled == null) {
			whereBy.eq(TablePrefix.TagInfoFix + TagInfoRM.disabled, 1);
		} else {
			if (disabled == -1) {
				whereBy.greateThenOrEquals(TablePrefix.TagInfoFix + TagInfoRM.disabled, 0);
			} else {
				whereBy.eq(TablePrefix.TagInfoFix + TagInfoRM.disabled, disabled);
			}
		}

		if (StringUtils.isNotEmpty(serialNo)) {
			whereBy.like(TablePrefix.TagInfoFix + TagInfoRM.serialNo, "%" + serialNo + "%", CondtionSeparator.AND);
		}
		if (online != null) {
			whereBy.eq(TablePrefix.TagInfoFix + TagInfoRM.online, online, CondtionSeparator.AND);
		}
		whereBy.eq(TablePrefix.TagInfoFix + TagInfoRM.orgId, EcUtil.getCurrentUser().getOrgId(), CondtionSeparator.AND);
		long counts = tagInfoDao.findTagInfoCountByCriteria(whereBy);
		whereBy.setPage(pagerOffset, Constant.DEFAULT_SIZE).orderBy(TablePrefix.TagInfoFix + TagInfoRM.pk, OrderType.ASC);

		List<Map<String, Object>> map = tagInfoDao.findTagInfoByCriteria(whereBy);
		List<TagInfoView> list = RowMapperUtils.map2List(map, TagInfoView.class);

		ECPage<TagInfoView> page = PageHelper.list(counts, list, whereBy);
		return page;
	}

}
