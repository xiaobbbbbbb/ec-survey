package com.ecarinfo.survey.codec;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandler.Sharable;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;
import org.springframework.stereotype.Component;

@Sharable
@Component("protobufEncoder")
public class ProtobufEncoder extends OneToOneEncoder {

    @Override
    public Object encode(ChannelHandlerContext ctx, Channel channel, Object obj)
            throws Exception {       
        
        return obj;
    }
}
