package com.ecarinfo.survey.main;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.Executors;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.ecarinfo.common.utils.MD5Utils;
import com.ecarinfo.common.utils.PropUtil;
import com.ecarinfo.survey.cache.EcOnline;
import com.ecarinfo.survey.cache.OnlineManager;
import com.ecarinfo.survey.common.Const;
import com.ecarinfo.survey.factory.HttpPipelineFactory;
import com.ecarinfo.survey.factory.ServerPipelineFactory;

public class Application {
	static Logger logger = Logger.getLogger(Application.class.getName());
	private static ServerBootstrap serverBootstrap;
	
	public ServerPipelineFactory serverPipelineFactory;
	private HttpPipelineFactory httpPipelineFactory;
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;
	private OnlineManager<Channel,EcOnline> ecOnlineManager;

	public void setServerPipelineFactory(ServerPipelineFactory serverPipelineFactory) {
		this.serverPipelineFactory = serverPipelineFactory;
	}

	public void setHttpPipelineFactory(HttpPipelineFactory httpPipelineFactory) {
		this.httpPipelineFactory = httpPipelineFactory;
	}

	public void setThreadPoolTaskExecutor(
			ThreadPoolTaskExecutor threadPoolTaskExecutor) {
		this.threadPoolTaskExecutor = threadPoolTaskExecutor;
	}

	public void setEcOnlineManager(OnlineManager<Channel, EcOnline> ecOnlineManager) {
		this.ecOnlineManager = ecOnlineManager;
	}

	public Application() {
		super();
	}

	public void run() {
		startTcp();
		startHttp();
	}
	
	private void startTcp() {
		// Configure the server.
		serverBootstrap = new ServerBootstrap(
				new NioServerSocketChannelFactory(
						Executors.newCachedThreadPool(),
						Executors.newCachedThreadPool()));
		// Configure the pipeline factory.
		// bootstrap.setPipelineFactory(new ApplicatonServerPipelineFactory());
		serverBootstrap.setPipelineFactory(serverPipelineFactory);

		/**
		 * 优化参数
		 */
		serverBootstrap.setOption("child.tcpNoDelay", true);
		serverBootstrap.setOption("child.keepAlive", true);
		serverBootstrap.setOption("child.reuseAddress", true);// ReuseAddress选项配置为True将允许将套接字绑定到已在使用中的地址
														// 即同一个端口可以被多个应用绑定

		// http://docs.jboss.org/netty/3.2/xref/org/jboss/netty/channel/DefaultChannelConfig.html
		serverBootstrap.setOption("child.connectTimeoutMillis", 5000);// 如1000 (1 秒)
																// netty 默认10000
																// (10 seconds)
		// bootstrap.setOption("readWriteFair", true);
		serverBootstrap.bind(new InetSocketAddress(Integer.valueOf(PropUtil.getProp("server.properties", "tcp_port"))));
	}
	private void startHttp() {
		// Configure the server.
		ServerBootstrap httpBootstrap = new ServerBootstrap(
				new NioServerSocketChannelFactory(
						Executors.newCachedThreadPool(),
						Executors.newCachedThreadPool()));
		httpBootstrap.setPipelineFactory(httpPipelineFactory);

		/**
		 * 优化参数
		 */
		httpBootstrap.setOption("child.tcpNoDelay", true);
		httpBootstrap.setOption("child.keepAlive", true);
		httpBootstrap.setOption("child.reuseAddress", true);// ReuseAddress选项配置为True将允许将套接字绑定到已在使用中的地址
															// 即同一个端口可以被多个应用绑定

		// http://docs.jboss.org/netty/3.2/xref/org/jboss/netty/channel/DefaultChannelConfig.html
		httpBootstrap.setOption("child.connectTimeoutMillis", 5000);// 如1000 (1
																	// 秒) netty
																	// 默认10000
																	// (10
																	// seconds)
		// bootstrap.setOption("readWriteFair", true);
		httpBootstrap.bind(new InetSocketAddress(Integer.valueOf(PropUtil.getProp("server.properties", "http_port"))));
	}

}

