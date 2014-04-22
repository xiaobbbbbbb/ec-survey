package com.ecarinfo.survey.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ecarinfo.persist.exdao.ECDao;
import com.ecarinfo.survey.po.FenceCar;

public interface FenceCarDao extends ECDao<FenceCar> {
	List<Map<String,Object>> findFenCarInfo(
			@Param("fenceId")Integer fenceId);
}
