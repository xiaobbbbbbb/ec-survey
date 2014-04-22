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
import com.ecarinfo.survey.dao.MarkInfoDao;
import com.ecarinfo.survey.rm.EventInfoRM;
import com.ecarinfo.survey.rm.MarkInfoRM;
import com.ecarinfo.survey.rm.MarkTypeRM;
import com.ecarinfo.survey.service.MarkInfoService;
import com.ecarinfo.survey.view.MarkInfoView;
import com.ecarinfo.survey.view.TablePrefix;

@Service("markInfoService")
public class MarkInfoServiceImpl implements MarkInfoService {

	@Resource
	private MarkInfoDao markInfoDao;

	// 标注分类、时间区间
	@Override
	public ECPage<MarkInfoView> queryMarkInfoPages(String typeName, String startTime, String endTime) {
		int pagerOffset = SystemContext.getPageOffset();
		Criteria whereBy = new Criteria();
		whereBy.eq("1", 1);
		if (StringUtils.isNotEmpty(typeName)) {
			whereBy.like(TablePrefix.MarkTypeFix + MarkTypeRM.name, "%" + typeName + "%", CondtionSeparator.AND);
		}
		if (StringUtils.isNotEmpty(startTime)) {
			whereBy.greateThenOrEquals(TablePrefix.MarkInfoFix + MarkInfoRM.createTime, startTime + " 00:00:00", CondtionSeparator.AND);
		}
		if (StringUtils.isNotEmpty(endTime)) {
			whereBy.lessThenOrEquals(TablePrefix.MarkInfoFix + MarkInfoRM.createTime, endTime + " 23:59:59", CondtionSeparator.AND);
		}
		whereBy.eq(TablePrefix.MarkInfoFix + MarkInfoRM.orgId, EcUtil.getCurrentUser().getOrgId(), CondtionSeparator.AND);
		long counts = markInfoDao.findMarkInfoCountByCriteria(whereBy);
		whereBy.setPage(pagerOffset, Constant.DEFAULT_SIZE).orderBy(TablePrefix.MarkInfoFix + MarkInfoRM.pk, OrderType.ASC);
		List<Map<String, Object>> map = markInfoDao.findMarkInfoByCriteria(whereBy);
		List<MarkInfoView> list = RowMapperUtils.map2List(map, MarkInfoView.class);
		ECPage<MarkInfoView> page = PageHelper.list(counts, list, whereBy);
		return page;
	}
	
	@Override
	public List<MarkInfoView> queryMarkInfos(String typeName,String startTime, String endTime) {
		Criteria whereBy = new Criteria();
		whereBy.eq("1", 1);
		if (StringUtils.isNotEmpty(typeName)) {
			whereBy.like(TablePrefix.MarkTypeFix + MarkTypeRM.name,	"%" + typeName + "%", CondtionSeparator.AND);
		}
		if (StringUtils.isNotEmpty(startTime)) {
			whereBy.greateThenOrEquals(TablePrefix.MarkInfoFix+ MarkInfoRM.createTime, startTime + " 00:00:00",CondtionSeparator.AND);
		}
		if (StringUtils.isNotEmpty(endTime)) {
			whereBy.lessThenOrEquals(TablePrefix.MarkInfoFix + MarkInfoRM.createTime, endTime + " 23:59:59",CondtionSeparator.AND);
		}
		whereBy.eq(TablePrefix.MarkInfoFix + MarkInfoRM.orgId, EcUtil.getCurrentUser().getOrgId(), CondtionSeparator.AND);
		whereBy.orderBy(TablePrefix.MarkInfoFix + EventInfoRM.pk, OrderType.ASC);
		List<Map<String, Object>> map = markInfoDao.findMarkInfoByCriteria(whereBy);
		List<MarkInfoView> list = RowMapperUtils.map2List(map, MarkInfoView.class);
		return list;
	}
	@Override
	public List<MarkInfoView> queryMarkInfoList(Integer status){
		Criteria whereBy = new Criteria();
		whereBy.eq("1", 1);
		if(status !=null && status > 0){
			whereBy.eq(TablePrefix.MarkInfoFix + MarkInfoRM.status, status, CondtionSeparator.AND);
		}
		whereBy.eq(TablePrefix.MarkInfoFix + MarkInfoRM.orgId, EcUtil.getCurrentUser().getOrgId(), CondtionSeparator.AND);
		whereBy.orderBy(TablePrefix.MarkInfoFix + EventInfoRM.pk, OrderType.ASC);
		List<Map<String, Object>> map = markInfoDao.findMarkInfoByCriteria(whereBy);
		List<MarkInfoView> list = RowMapperUtils.map2List(map, MarkInfoView.class);
		return list;
	}
}
