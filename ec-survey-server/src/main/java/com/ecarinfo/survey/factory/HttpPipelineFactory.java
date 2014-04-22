package com.ecarinfo.survey.factory;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.codec.http.HttpRequestDecoder;
import org.jboss.netty.handler.codec.http.HttpResponseEncoder;
import org.springframework.stereotype.Component;

@Component("httpPipelineFactory")
public class HttpPipelineFactory implements ChannelPipelineFactory {
	
	public ChannelPipeline getPipeline() throws Exception {
		 // Create a default pipeline implementation.
		 ChannelPipeline pipeline = Channels.pipeline();
		 pipeline.addLast("decoder", new HttpRequestDecoder());
		 pipeline.addLast("encoder", new HttpResponseEncoder());
		 //http处理handler
		 return pipeline;
	}
}
