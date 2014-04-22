package com.ecarinfo.survey.handler;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;

import com.ecarinfo.persist.exdao.GenericDao;
import com.ecarinfo.survey.cache.EcOnline;
import com.ecarinfo.survey.cache.OnlineManager;
import com.ecarinfo.survey.task.cache.ExecuteReceivedTaskCache;
import com.ecarinfo.survey.vo.BaseVO;

@Controller("serverHandler")
public class ServerHandler extends SimpleChannelUpstreamHandler{
	
	private static final Logger logger = Logger.getLogger(ServerHandler.class.getName());
	
	@Resource
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;
	
	@Resource
	private ApplicationContext context;
	
	@Resource
	protected ThreadPoolTaskExecutor fiveServiceThreadPool;
	
	@Resource
	private OnlineManager<Channel,EcOnline> ecOnlineManager;
	
	@Resource
	private GenericDao genericDao;
	
	//一个临时的在线列表
	public static final Map<Channel,Long> tempChannelMap = new ConcurrentHashMap<Channel,Long>();
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
		try {
			BaseVO message = (BaseVO) e.getMessage();
			
			EcOnline online = ecOnlineManager.get(ctx.getChannel());
			if(online != null) {
				online.setLastUpdateTime(System.currentTimeMillis());
			}
			
			//分流处理（http和tcp请求分别在各自的线程池中处理）
			threadPoolTaskExecutor.execute(ExecuteReceivedTaskCache.getTask(context,message, ctx,online));
		} catch (Exception e2) {
			logger.error("[messageReceived] error:",e2);
		}
	}
	
	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		String info = "[channelConnected]:"+e.getChannel().getRemoteAddress();
		logger.info(info);
		Channel channel = e.getChannel();
		//添加一个空的对象到在线列表中
		tempChannelMap.put(channel, System.currentTimeMillis());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
			throws Exception {
		logger.error("[exceptionCaught]:"+e.getCause());
		e.getCause().printStackTrace();
		closeChannel(threadPoolTaskExecutor,genericDao,ecOnlineManager,ctx.getChannel());//关闭连接	
	}
	
	@Override
	public void channelDisconnected(ChannelHandlerContext ctx,
			ChannelStateEvent e) throws Exception {		
		logger.error("[channelDisconnected]:"+e.getChannel().getRemoteAddress());
		closeChannel(threadPoolTaskExecutor,genericDao,ecOnlineManager,ctx.getChannel());//关闭连接	
	}

	/**
	 * 关闭连接 服务器主动关掉
	 * @param ctx
	 */
	public static final void closeChannel(ThreadPoolTaskExecutor threadPoolTaskExecutor,GenericDao genericDao,OnlineManager<Channel,EcOnline> ecOnlineManager,Channel channel) {
		EcOnline online = ecOnlineManager.get(channel);
		if(online != null) {
			logger.info("用户退出。"+online);
			online.setLogoutTime(new Date());
//			threadPoolTaskExecutor.execute(new LoginLogTask(genericDao,online));
		}
		ecOnlineManager.remove(channel);
		if(channel.isConnected()) {
			channel.close();
		}
	}
	
	public static final void addChannel(OnlineManager<Channel,EcOnline> ecOnlineManager,Channel channel,EcOnline online) {
		ecOnlineManager.add(channel, online);
		logger.info("用户登录。"+online);
	}
	
}
