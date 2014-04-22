package com.ecarinfo.survey.dao;

import java.util.List;
import java.util.Map;

import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.exdao.ECDao;
import com.ecarinfo.survey.po.MarkInfo;

public interface MarkInfoDao extends ECDao<MarkInfo> {

	List<Map<String, Object>> findMarkInfoByCriteria(Criteria whereBy);
	long findMarkInfoCountByCriteria(Criteria whereBy);
}
