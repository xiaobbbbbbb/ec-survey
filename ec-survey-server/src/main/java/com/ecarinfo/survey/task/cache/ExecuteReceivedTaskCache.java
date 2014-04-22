package com.ecarinfo.survey.task.cache;

import java.util.concurrent.ArrayBlockingQueue;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.springframework.context.ApplicationContext;

import com.ecarinfo.survey.cache.EcOnline;
import com.ecarinfo.survey.task.ExecuteReceivedTask;
import com.ecarinfo.survey.vo.BaseVO;

public class ExecuteReceivedTaskCache {
	public static final int CAPACITY = 2048;
	private static final ArrayBlockingQueue<ExecuteReceivedTask> TASKS = new ArrayBlockingQueue<ExecuteReceivedTask>(CAPACITY);
	private static final Logger logger = Logger.getLogger(ExecuteReceivedTaskCache.class);
	static {
		for (int i = 0; i < CAPACITY; i++) {
			TASKS.offer(new ExecuteReceivedTask());
		}
	}
	
	public static final ExecuteReceivedTask getTask(ApplicationContext context,BaseVO requestMessage, ChannelHandlerContext ctx,EcOnline online) {
		try {
			//Retrieves and removes the head of this queue, 
			//waiting if necessary until an element becomes available.
			ExecuteReceivedTask task = TASKS.take();
			task.init(context,requestMessage, ctx);
			return task;
		} catch (InterruptedException e) {
			logger.error("",e);
			return null;
		}
	}
	
	public static final void free(ExecuteReceivedTask task) {
		if(task == null) {
			return;
		}
		task.init(null,null, null);
		TASKS.offer(task);
	}
	
	
}
