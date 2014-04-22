package com.ecarinfo.survey.task;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.ChannelHandlerContext;

import com.ecarinfo.survey.command.AbstractCommand;
import com.ecarinfo.survey.task.cache.CommandTaskCache;
import com.ecarinfo.survey.vo.BaseVO;

/**
 * 异步执行指令
 * @author ecxiaodx
 *
 */
public class CommandTask implements Runnable{
	private static final Logger logger = Logger.getLogger(CommandTask.class);
	private AbstractCommand command;
	private ChannelHandlerContext ctx;
	private BaseVO requestMessage;
	
	public void init(AbstractCommand command, ChannelHandlerContext ctx, BaseVO requestMessage) {
		this.command = command;
		this.ctx = ctx;
		this.requestMessage = requestMessage;
	}
	
	public CommandTask(AbstractCommand command, ChannelHandlerContext ctx,BaseVO requestMessage) {
		this.command = command;
		this.ctx = ctx;
		this.requestMessage = requestMessage;
	}

	@Override
	public void run() {
		try {
			Object response = command.execute(ctx, requestMessage);
			ExecuteReceivedTask.sendToClient(response, ctx.getChannel());
		} catch (Exception e) {
			logger.error("",e);
		} finally {
			CommandTaskCache.free(this);
		}
	}

}
