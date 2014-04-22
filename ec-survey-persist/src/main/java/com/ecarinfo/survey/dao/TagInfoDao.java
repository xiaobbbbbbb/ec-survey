package com.ecarinfo.survey.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.exdao.ECDao;
import com.ecarinfo.survey.po.TagInfo;

public interface TagInfoDao extends ECDao<TagInfo> {
	
	List<Map<String, Object>> findTagInfoByCarId(@Param("carId") Integer carId);

	List<Map<String, Object>> findTagInfoByCriteria(Criteria whereBy);

	long findTagInfoCountByCriteria(Criteria whereBy);
}
