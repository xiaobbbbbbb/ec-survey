package com.ecarinfo.survey.main;

import java.io.IOException;
import java.net.Socket;

import org.apache.log4j.Logger;

import com.ecarinfo.common.utils.MD5Utils;
import com.ecarinfo.common.utils.PropUtil;
import com.ecarinfo.survey.common.Const;

public class Shutdown {
	private static final Logger logger = Logger.getLogger(Shutdown.class);
	
	private final static void applyToShutdownServer() {
		try {
			Socket socket = new Socket("127.0.0.1",Integer.valueOf(PropUtil.getProp("server.properties", "stop_port")));
			String closeOrder = "shutdown_"+MD5Utils.md5("shutdown_"+PropUtil.getProp("server.properties", "stop_key"));
			socket.getOutputStream().write(closeOrder.getBytes(Const.DEFAULT_CHARSET));
			byte[]buf = new byte[1];
			int count = socket.getInputStream().read(buf);
			if(count == -1) {//收到服务器断开连接消息
				return;
			}
			String res = new String(buf,0,count,Const.DEFAULT_CHARSET);
			if(res.equals("1")) {
				logger.info("关服成功。");
			} else if(res.equals("-1")) {
				logger.info("关服失败。");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		applyToShutdownServer();
	}
}
