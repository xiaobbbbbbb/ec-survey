package com.ecarinfo.survey.service;

import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.survey.view.TagInfoView;

public interface TagInfoService {

	ECPage<TagInfoView> queryTagInfoPages(Integer disabled, String serialNo, Boolean online);

}
