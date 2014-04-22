package com.ecarinfo.survey.task;

import org.apache.log4j.Logger;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.springframework.context.ApplicationContext;

import com.ecarinfo.survey.command.AbstractCommand;
import com.ecarinfo.survey.task.cache.ExecuteReceivedTaskCache;
import com.ecarinfo.survey.vo.BaseVO;

public class ExecuteReceivedTask implements Runnable {
	private static final Logger logger = Logger.getLogger(ExecuteReceivedTask.class);
	
	private BaseVO requestMessage;
	private ChannelHandlerContext ctx =null;
	private ApplicationContext context;
	
	public void init(ApplicationContext context,BaseVO requestMessage, ChannelHandlerContext ctx) {
		this.context = context;
		this.requestMessage = requestMessage;
		this.ctx = ctx;
	}
	
	public void run() {
		try {
			String requestMessageType = requestMessage.getDescriptorForType();
			AbstractCommand command = (AbstractCommand) context.getBean(requestMessageType);
			Object resp = command.execute(ctx,requestMessage);	
			sendToClient(resp,ctx.getChannel());
			
		} catch (Exception e) {
			logger.error("",e);
		} finally {
			ExecuteReceivedTaskCache.free(this);
		}
	}
	
	/**
	 * 发送消息
	 * @param resp
	 * @param channel
	 */
	public static void sendToClient(Object resp,Channel channel) {
		if(resp != null){
			if(resp.getClass().isArray()) {
//				Message [] responses = (Message [])resp;
//				for(Message response:responses) {
//					if(response == null) {
//						continue;
//					}
//					sendToClient(response,channel);
//				}
			} else {
				String response = (String)resp;
//				sendToClient(ChannelBuffers.wrappedBuffer(response.getBytes()),channel);
				channel.write(ChannelBuffers.wrappedBuffer(response.getBytes()));
				logger.warn("sendToClient=["+response+"]");
			}
		}
	}
	
}
