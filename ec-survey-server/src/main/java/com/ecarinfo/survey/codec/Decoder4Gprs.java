package com.ecarinfo.survey.codec;

import com.ecarinfo.survey.vo.GprmcVO;
import com.ecarinfo.survey.vo.GprsVO;

public class Decoder4Gprs {
	private static final int  TYPE_IMEI = 3;
	private static final int  TYPE_ALARMTYPE = 4;
	private static final int  TYPE_PDOP = 5;
	private static final int  TYPE_HDOP = 6;
	private static final int  TYPE_VDOP = 7;
	private static final int  TYPE_STATUS = 8;
	private static final int  TYPE_RTC = 9;
	private static final int  TYPE_VOLTAGE = 10;
	private static final int  TYPE_ADC = 11;
	private static final int  TYPE_LACCI = 12;
	private static final int  TYPE_TEMPERATURE = 13;
	private static final int  TYPE_ODOMETER = 14;
	private static final int  TYPE_SERIALID = 15;
	private static final int  TYPE_RFID = 16;
	public static final GprsVO decode(byte[] bytes) {
		GprsVO vo = new GprsVO();
		vo.setLen(new String(bytes,2,2));
		int type = 2;
		int startOffset = 4;
		for(int i=4,len=bytes.length;i<len;i++) {
			if(bytes[i]=='|') {//'|' = 124
				type ++;
				init(vo,bytes,startOffset,i,type);
				startOffset = i+1;
			}
		}
		return vo;
	}
	
	private static final void init(GprsVO vo,byte[] bytes,int startOffset,int endOffset,int type) {
//		String value = new String(chars,startOffset,endOffset-startOffset);
		switch (type) {
		case TYPE_IMEI:
			vo.setImei(new String(bytes,startOffset,endOffset-startOffset));
			break;
		case TYPE_ALARMTYPE:
			vo.setAlarmType(new String(bytes,startOffset,2));
			GprmcVO gpgmc = new GprmcVO();
			boolean valid = gpgmc.init(new String(bytes,startOffset+3,endOffset-startOffset-2));
			if(valid) {
				vo.setGprmc(gpgmc);
			} else {
				vo.setGprmc(null);
			}
			break;
		case TYPE_PDOP:
			vo.setPdop(new String(bytes,startOffset,endOffset-startOffset));
			break;
		case TYPE_HDOP:
			vo.setHdop(new String(bytes,startOffset,endOffset-startOffset));
			break;
		case TYPE_VDOP:
			vo.setVdop(new String(bytes,startOffset,endOffset-startOffset));
			break;
		case TYPE_STATUS:
			vo.setStatus(new String(bytes,startOffset,endOffset-startOffset));
			break;
		case TYPE_RTC:
			vo.setRtc(new String(bytes,startOffset,endOffset-startOffset));
			break;
		case TYPE_VOLTAGE:
			vo.setVoltage(new String(bytes,startOffset,endOffset-startOffset));
			break;
		case TYPE_ADC:
			vo.setAdc(new String(bytes,startOffset,endOffset-startOffset));
			break;
		case TYPE_LACCI:
			vo.setLacci(new String(bytes,startOffset,endOffset-startOffset));
			break;
		case TYPE_TEMPERATURE:
			vo.setTemperature(new String(bytes,startOffset,endOffset-startOffset));
			break;
		case TYPE_ODOMETER:
			vo.setOdoMeter(new String(bytes,startOffset,endOffset-startOffset));
			break;
		case TYPE_SERIALID:
			vo.setSerialId(new String(bytes,startOffset,endOffset-startOffset));
			break;
		case TYPE_RFID:
			vo.setRfId(new String(bytes,startOffset,endOffset-startOffset));
			break;
		default:
			break;
		}
	}
	
	public static void main(String[] args) {
		String data = "$$BA860041024244066|AA$GPRMC,065017.000,A,2231.9122,N,11401.3765,E,36.85,100.04,040913,,,A*51|01.9|01.2|01.5|000010000000|20130904065017|13821485|00000000|249611D3|0000|0.2704|0096||B4B4";
		byte []bytes = data.getBytes();
		GprsVO vo = decode(bytes);
		System.err.println(vo);
	}
	
}
