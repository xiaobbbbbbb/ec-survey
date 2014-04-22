package com.ecarinfo.survey.factory;

import static org.jboss.netty.channel.Channels.pipeline;

import javax.annotation.Resource;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.handler.codec.frame.DelimiterBasedFrameDecoder;
import org.jboss.netty.handler.codec.frame.Delimiters;
import org.springframework.stereotype.Component;

import com.ecarinfo.survey.codec.ProtobufDecoder;
import com.ecarinfo.survey.codec.ProtobufEncoder;
import com.ecarinfo.survey.handler.ServerHandler;

@Component("serverPipelineFactory")
public class ServerPipelineFactory implements ChannelPipelineFactory {
	
	@Resource
	private ProtobufDecoder protobufDecoder;
	
	@Resource
	private ProtobufEncoder protobufEncoder;
	
	@Resource
	private ServerHandler serverHandler;
	//客户端请求最大字节数10K
	private static int maxFrameLength = 102400;//10K
	
	public ChannelPipeline getPipeline() throws Exception {
		ChannelPipeline p = pipeline();
		ChannelBuffer [] customDelimiters = new ChannelBuffer[] {
                ChannelBuffers.wrappedBuffer(new byte[] { '\r', '\n','#','#' })
        };
//		Delimiters.lineDelimiter()
		p.addLast("frameDecoder",new DelimiterBasedFrameDecoder(maxFrameLength,true,customDelimiters));
		p.addLast("protobufDecoder", protobufDecoder);// 解码
		
//		p.addLast("frameEncode", new LengthFieldPrepender(4, false));
		p.addLast("protobufEncoder", protobufEncoder);// 编码

		// 业务逻辑处理
		p.addLast("handler", serverHandler);
		return p;
	}
	
	public static void main(String[] args) {
		System.err.println(Delimiters.lineDelimiter());
	}

}
