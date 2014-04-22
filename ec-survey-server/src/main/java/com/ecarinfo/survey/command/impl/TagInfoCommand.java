package com.ecarinfo.survey.command.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Component;

import com.ecarinfo.persist.service.GenericService;
import com.ecarinfo.survey.cache.EcOnline;
import com.ecarinfo.survey.cache.OnlineManager;
import com.ecarinfo.survey.command.AbstractCommand;
import com.ecarinfo.survey.po.TagInfo;
import com.ecarinfo.survey.po.TagOnlineData;
import com.ecarinfo.survey.rm.TagInfoRM;
import com.ecarinfo.survey.vo.BaseVO;
import com.ecarinfo.survey.vo.TagVO;

@Component("TagInfoCommand")
public class TagInfoCommand extends AbstractCommand {
	private static final Logger logger = Logger.getLogger(TagInfoCommand.class);
	@Resource
	private GenericService genericService;
	@Resource
	private OnlineManager<Channel,EcOnline> ecOnlineManager;
	
	private EcOnline getEcOnline(Channel channel,List<String> tagNos) {
		EcOnline online = ecOnlineManager.get(channel);
		return online;
	}
	
	@Override
	public Object execute(ChannelHandlerContext ctx, BaseVO requestMessage) {
		System.err.println("TagInfoCommand invoke.");
		TagVO vo = (TagVO)requestMessage;
		System.err.println(vo);
		EcOnline online = getEcOnline(ctx.getChannel(), vo.getTagIds());
		if(online == null) {
			//设备不在线时，先不统计电子标签
			logger.error("device no login yet.");
			return null;
		}
		
		for(String id:vo.getTagIds()) {
			TagOnlineData onlineData = online.getTagMap().get(id);
			if(onlineData == null) {//第一次上线
				TagInfo tagInfo = genericService.findOneByAttr(TagInfo.class, TagInfoRM.serialNo, id);
				if(tagInfo != null) {//第一次上线，更新上线状态
					Date now = new Date();
					tagInfo.setUpdateTime(now);
					tagInfo.setOnline(true);
					tagInfo.setLastUploadTime(now);
					genericService.update(tagInfo);
					
					onlineData = new TagOnlineData();
					onlineData.setCreateTime(now);
					onlineData.setSerialNo(tagInfo.getSerialNo());
					onlineData.setUserId(tagInfo.getUserId());
					onlineData.setUpdateTime(now);
					genericService.save(onlineData);
					online.getTagMap().put(id, onlineData);
				}
			} else {
				//修改最后数据上来时间
				onlineData.setUpdateTime(new Date());
			}
		}
	
		return null;
	}

}
