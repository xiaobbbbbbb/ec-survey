package com.ecarinfo.survey.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ecarinfo.persist.exdao.ECDao;
import com.ecarinfo.survey.po.FenceInfo;

public interface FenceInfoDao extends ECDao<FenceInfo> {
	
	List<FenceInfo> findFenceByCarId(@Param("carId")Integer carId);
	
}
