package com.ecarinfo.survey.job;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.persist.paging.PagingManager;
import com.ecarinfo.persist.service.GenericService;
import com.ecarinfo.survey.po.CarInfo;
import com.ecarinfo.survey.po.EventInfo;
import com.ecarinfo.survey.rm.CarInfoRM;
import com.ecarinfo.survey.rm.EventInfoRM;
/**
 * 案件定时器
 * 	30分钟后自动终结
	案件查询手工终结（在报案管理中已经有了该功能）    
	系统对接获取数据未调度案件终结，案件查询界面手工作废
 * @author ecxiaodx
 *
 */
@Component("eventInfoJob")
public class EventInfoJob {
	private static final Logger logger = Logger.getLogger(EventInfoJob.class);
	@Resource
	private GenericService genericService;
	private static final int CAR_STATUS_INTERVAL = 30*60*1000;//15分钟不提交请求，则服务器将其踢下线,更新在线状态，并从缓存中删除
	
	public void execute() {
		logger.info("EventInfoJob.execute");
		int pageNo = 1;
		while(true) {
			ECPage<EventInfo> page = PagingManager.list(genericService, EventInfo.class, new Criteria()
			.eq(EventInfoRM.status,1).setPage(pageNo++, ECPage.DEFAULT_SIZE));//查找已经调度的案件
			if(page.getList().size() == 0) {
				break;
			}
			for(EventInfo e:page.getList()) {
				if(e.getProcessTime() == null) {
					continue;
				}
				if(System.currentTimeMillis() - e.getProcessTime().getTime() > CAR_STATUS_INTERVAL) {//已经调度
					e.setStatus(4);//超时终结
					genericService.updateWithCriteria(EventInfo.class, new Criteria()
					.update(EventInfoRM.status, e.getStatus())
					.eq(EventInfoRM.id, e.getId()));
					Integer carId = e.getSurveyCarId();
					if(carId != null) {
						Long count = genericService.count(EventInfo.class, new Criteria()
						.eq(EventInfoRM.surveyCarId, carId)
						.eq(EventInfoRM.status, 1));
						if(count != null && count == 0) {
							genericService.updateWithCriteria(CarInfo.class, new Criteria()
							.update(CarInfoRM.status, 1)
							.eq(CarInfoRM.id, carId));//没有案件时，待命中
						}
					}
				}
			}
		}
	}
}
