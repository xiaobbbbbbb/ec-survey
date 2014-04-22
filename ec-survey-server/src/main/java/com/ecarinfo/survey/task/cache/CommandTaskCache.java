package com.ecarinfo.survey.task.cache;

import java.util.concurrent.ArrayBlockingQueue;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.ChannelHandlerContext;

import com.ecarinfo.survey.command.AbstractCommand;
import com.ecarinfo.survey.task.CommandTask;
import com.ecarinfo.survey.vo.BaseVO;

public class CommandTaskCache {
	public static final int CAPACITY = 1024;
	private static final ArrayBlockingQueue<CommandTask> TASKS = new ArrayBlockingQueue<CommandTask>(CAPACITY);
	private static final Logger logger = Logger.getLogger(ExecuteReceivedTaskCache.class);
	static {
		for (int i = 0; i < CAPACITY; i++) {
			TASKS.offer(new CommandTask(null, null,null));
		}
	}
	
	public static final CommandTask getTask(AbstractCommand command, ChannelHandlerContext ctx, BaseVO requestMessage) {
		try {
			//Retrieves and removes the head of this queue, 
			//waiting if necessary until an element becomes available.
			CommandTask task = TASKS.take();
			task.init(command,ctx,requestMessage);
			return task;
		} catch (InterruptedException e) {
			logger.error("",e);
			return null;
		}
	}
	
	public static final void free(CommandTask task) {
		if(task == null) {
			return;
		}
		task.init(null,null,null);
		TASKS.offer(task);
	}
}
