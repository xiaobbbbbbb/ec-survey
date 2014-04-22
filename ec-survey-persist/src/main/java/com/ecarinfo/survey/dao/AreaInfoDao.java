package com.ecarinfo.survey.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.exdao.ECDao;
import com.ecarinfo.survey.po.AreaInfo;

public interface AreaInfoDao extends ECDao<AreaInfo> {
	/**
	 * 根据关键字查找片区信息
	 * @param keyWord (carNo,surveyUser's name,surveyUser's tel)
	 * @return
	 */
	List<AreaInfo> findAreaByKeyWord(@Param("keyWord") String keyWord,@Param("orgId") Integer orgId);
	
	List<Map<String, Object>> findAreaByCriteria(Criteria whereBy);
	long findAreaCountByCriteria(Criteria whereBy);
	
}
