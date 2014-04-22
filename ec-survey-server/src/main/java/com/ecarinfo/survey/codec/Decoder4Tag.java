package com.ecarinfo.survey.codec;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import com.ecarinfo.common.utils.BytesUtil;
import com.ecarinfo.survey.vo.TagVO;

public class Decoder4Tag {
	
	private static final int TYPE_IMEI = 1;
	private static final int TYPE_RTC = 2;
	private static final int TYPE_LOCATE = 3;
	private static final int TYPE_TAG_COUNT = 4;
	private static final int TYPE_TAG_ID = 5;
	
	public static final TagVO decode(byte [] bytes) {
		TagVO vo = new TagVO();
		 /*
         * Format：
         * 
         * $T(2Bytes)+Len(2Bytes)+IMEI(15Bytes+|+RTC(14Bytes)+|+LOCATION(19Bytes)+|+TagCount(1Byte)+|TAGID（4Bytes)....|+Checksum(4Bytes)+ \r\n(2Bytes) 
        */
//		vo.setLen(length);
		int type = 0;
		int startOffset = 4;
		
		for(int i=4,len=bytes.length;i<len;i++) {
			if(bytes[i]==124) {//'|'==124
				type ++;
				init(vo,bytes,startOffset,i,type);
				startOffset = i+1;
			}
		}
		return vo;
	}
	
	
	private static final void init(TagVO vo,byte[] bytes,int startOffset,int endOffset,int type) {
		switch (type) {
		case TYPE_IMEI:
			vo.setImei(new String(bytes,startOffset,endOffset-startOffset));
			break;
		case TYPE_RTC:
			vo.setRtc(new String(bytes,startOffset,endOffset-startOffset));
			break;
		case TYPE_LOCATE:
			vo.setLat(new String(bytes,startOffset,9));
			vo.setLng(new String(bytes,startOffset+9,endOffset-startOffset-9));
			break;
		case TYPE_TAG_COUNT:
			vo.setTagCount(bytes[startOffset]+"");
			break;
		case TYPE_TAG_ID:
			
			List<String> idList = new ArrayList<String>();
			for(int i=0;i<Integer.parseInt(vo.getTagCount());i++) {
				idList.add(BytesUtil.bytes2HexString(bytes, startOffset+(i*4),4).replace(" ", ""));
//				idList.add(ByteBuffer.wrap(bytes, startOffset+(i*4),4).getInt()+"");
			}
			vo.setTagIds(idList);
			break;
		default:
			break;
		}
	}
	
	public static void main(String[] args) {
		System.err.println((int)'$');
		System.err.println((int)'T');
	}
}
