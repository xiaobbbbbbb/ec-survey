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
import com.ecarinfo.survey.dao.SurveyUserInfoDao;
import com.ecarinfo.survey.rm.CarReportRM;
import com.ecarinfo.survey.rm.SurveyUserInfoRM;
import com.ecarinfo.survey.service.SurveyUserInfoService;
import com.ecarinfo.survey.view.SurveyUserInfoView;
import com.ecarinfo.survey.view.TablePrefix;

@Service("surveyUserInfoService")
public class SurveyUserInfoServiceImpl implements SurveyUserInfoService {

	@Resource
	private SurveyUserInfoDao surveyUserInfoDao;

	@Override
	public ECPage<SurveyUserInfoView> querySurveyUserInfoPages(String name, Integer status, Integer disabled) {
		int pagerOffset = SystemContext.getPageOffset();
		Criteria whereBy = new Criteria();
		// 是否停用
		if (disabled == null) {
			whereBy.eq(TablePrefix.SurveyUserInfoFix + SurveyUserInfoRM.disabled, 1);
		} else {
			if (disabled == -1) {
				whereBy.greateThenOrEquals(TablePrefix.SurveyUserInfoFix + SurveyUserInfoRM.disabled, 0);
			} else {
				whereBy.eq(TablePrefix.SurveyUserInfoFix + SurveyUserInfoRM.disabled, disabled);
			}
		}

		if (StringUtils.isNotEmpty(name)) {
			whereBy.like(TablePrefix.SurveyUserInfoFix + SurveyUserInfoRM.name, "%" + name + "%", CondtionSeparator.AND);
		}

		if (status != null) {
			whereBy.eq(TablePrefix.SurveyUserInfoFix + SurveyUserInfoRM.status, status, CondtionSeparator.AND);
		}
		whereBy.eq(TablePrefix.SurveyUserInfoFix + SurveyUserInfoRM.orgId, EcUtil.getCurrentUser().getOrgId(), CondtionSeparator.AND);
		long counts = surveyUserInfoDao.findSurveyUserInfoCountByCriteria(whereBy);
		whereBy.setPage(pagerOffset, Constant.DEFAULT_SIZE).orderBy(TablePrefix.SurveyUserInfoFix + SurveyUserInfoRM.pk, OrderType.ASC);

		List<Map<String, Object>> map = surveyUserInfoDao.findSurveyUserInfoByCriteria(whereBy);
		List<SurveyUserInfoView> list = RowMapperUtils.map2List(map, SurveyUserInfoView.class);

		ECPage<SurveyUserInfoView> page = PageHelper.list(counts, list, whereBy);
		return page;
	}

	// 查勘员出车报告
	public ECPage<SurveyUserInfoView> querySurveyUserInfoReportPages(String startTime, String endTime, String name) {
		int pagerOffset = SystemContext.getPageOffset();
		Criteria whereBy = new Criteria();
		whereBy.eq(TablePrefix.SurveyUserInfoFix + SurveyUserInfoRM.disabled, 1);

		if (StringUtils.isNotEmpty(name)) {
			whereBy.like(TablePrefix.SurveyUserInfoFix + SurveyUserInfoRM.name, "%" + name + "%", CondtionSeparator.AND);
		}

		if (StringUtils.isNotEmpty(startTime)) {
			whereBy.greateThenOrEquals(TablePrefix.CarReportFix + CarReportRM.createTime, startTime + " 00:00:00", CondtionSeparator.AND);
		}
		if (StringUtils.isNotEmpty(endTime)) {
			whereBy.lessThenOrEquals(TablePrefix.CarReportFix + CarReportRM.createTime, endTime + " 23:59:59", CondtionSeparator.AND);
		}
		whereBy.groupBy(TablePrefix.CarReportFix + CarReportRM.surveyUid);
		long counts = surveyUserInfoDao.findSurveyUserInfoReportCountByCriteria(whereBy);
		whereBy.setPage(pagerOffset, Constant.DEFAULT_SIZE).orderBy(TablePrefix.SurveyUserInfoFix + SurveyUserInfoRM.pk, OrderType.ASC);

		List<Map<String, Object>> map = surveyUserInfoDao.findSurveyUserInfoReportByCriteria(whereBy);
		List<SurveyUserInfoView> list = RowMapperUtils.map2List(map, SurveyUserInfoView.class);

		ECPage<SurveyUserInfoView> page = PageHelper.list(counts, list, whereBy);
		return page;
	}

	@Override
	public List<SurveyUserInfoView> querySurveyUserInfos(String startTime,
			String endTime, String name) {
		Criteria whereBy = new Criteria();
		whereBy.eq(TablePrefix.SurveyUserInfoFix + SurveyUserInfoRM.disabled, 1);

		if (StringUtils.isNotEmpty(name)) {
			whereBy.like(TablePrefix.SurveyUserInfoFix + SurveyUserInfoRM.name, "%" + name + "%", CondtionSeparator.AND);
		}

		if (StringUtils.isNotEmpty(startTime)) {
			whereBy.greateThenOrEquals(TablePrefix.CarReportFix + CarReportRM.createTime, startTime + " 00:00:00", CondtionSeparator.AND);
		}
		if (StringUtils.isNotEmpty(endTime)) {
			whereBy.lessThenOrEquals(TablePrefix.CarReportFix + CarReportRM.createTime, endTime + " 23:59:59", CondtionSeparator.AND);
		}
		whereBy.groupBy(TablePrefix.CarReportFix + CarReportRM.surveyUid);
		whereBy.orderBy(TablePrefix.SurveyUserInfoFix + SurveyUserInfoRM.pk, OrderType.ASC);

		List<Map<String, Object>> map = surveyUserInfoDao.findSurveyUserInfoReportByCriteria(whereBy);
		List<SurveyUserInfoView> list = RowMapperUtils.map2List(map, SurveyUserInfoView.class);

		return list;
	}

}
