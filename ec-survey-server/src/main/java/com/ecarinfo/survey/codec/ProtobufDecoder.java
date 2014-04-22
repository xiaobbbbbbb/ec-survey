package com.ecarinfo.survey.codec;

import java.util.zip.Checksum;

import org.apache.log4j.Logger;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandler.Sharable;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneDecoder;
import org.springframework.stereotype.Component;

import com.ecarinfo.common.utils.CRC16;
import com.ecarinfo.survey.vo.GprsVO;
import com.ecarinfo.survey.vo.TagVO;

@Sharable
@Component("protobufDecoder")
public class ProtobufDecoder extends OneToOneDecoder {
	public static final Logger logger = Logger.getLogger(ProtobufDecoder.class
			.getName());

	@Override
	public Object decode(ChannelHandlerContext ctx, Channel channel, Object obj)
			throws Exception {

		if (!(obj instanceof ChannelBuffer)) {
			logger.error("obj is not ChannelBuffer");
			return null;
		}
		ChannelBuffer buffer = (ChannelBuffer) obj;
		byte[] bytes = buffer.array();

		// '$' == 36 'T'=84 'V'=86
		if (bytes[0] == 36) {
			String messageStr = new String(bytes, 0, buffer.readableBytes());
			logger.info(messageStr);
			if (bytes[1] == 86) {// 图片
				return Decoder4Image.decode(bytes);
			}

			if (checksum(bytes, 0, buffer.readableBytes() - 4)) {
				// decode
				if (bytes[1] == 36) {// 设备位置信息
					GprsVO vo = Decoder4Gprs.decode(bytes);
					System.err.println(messageStr);
//					System.err.println(vo);
					return vo;
				}
				
				if (bytes[1] == 84) {// 电子标签
					TagVO vo = Decoder4Tag.decode(bytes);
					System.err.println(messageStr);
//					System.err.println(vo);
					return vo;
				}
			} else {
				// check faulure
				logger.error("check faulure:" + new String(bytes, 0, buffer.readableBytes()));
			}

		} else {
			logger.error((char)bytes[0]+"----unkown request:" + new String(bytes, 0, buffer.readableBytes()));
		}

		return null;
	}

	/**
	 * 
	 * @param bytes
	 * @param offset
	 * @param length
	 *            checksum's length
	 * @return
	 */
	private static boolean checksum(byte[] bytes, int offset, int length) {
		Checksum checksum =  new CRC16();
        checksum.update(bytes,offset,length);
        Long checkCode = checksum.getValue();
        String realCodeStr = new String(bytes,length,4);
        System.err.println("realCodeStr="+realCodeStr);
        Long realCode = Long.valueOf(realCodeStr, 16);
        System.err.println(realCode+"======"+checkCode);
        return checkCode.equals(realCode);
	}

	public static void main(String[] args) {
		System.err.println((int)'#');
	}
	
}
