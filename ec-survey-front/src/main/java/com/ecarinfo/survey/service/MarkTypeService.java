package com.ecarinfo.survey.service;

import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.survey.po.MarkType;

public interface MarkTypeService {

	ECPage<MarkType> queryMarkTypePages(Integer disabled, String name);

}

