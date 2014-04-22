package com.ecarinfo.survey.common;


public class Const {
	public static final String DEFAULT_CHARSET = "UTF-8";//收发字节的字我符串格式 	
	public static final int SEND_CONNECT_TIMEOUT = 120000;//120 000 = 120s = 2m 下行信息连接超时时间,毫秒
	public static final long TIME_OVER = 30000;// 在线设备的超时时间,30秒
	public static final int GET_DB_DATA_QTY = 1000;// 一次从DB取数据的条数

	public static class CarNoticeConst{
		/**
		 * 故障1001
		 */
		public static final int TYPE_GUZHANG = 1001;
		/**
		 * 怠速1002
		 */
		public static final int TYPE_DAISHU = 1002;
		/**
		 * 疲劳1003
		 */
		public static final int TYPE_PILAO = 1003;
		/**
		 * 车辆年检2001
		 */
		public static final int TYPE_NIANJIAN = 2001;
		/**
		 * 违章2002
		 */
		public static final int TYPE_WEIZHANG = 2002;
		/**
		 * 驾照年审换证2003
		 */
		public static final int TYPE_HUANZHENG = 2003;
		/**
		 * 续保2004
		 */
		public static final int TYPE_XUBAO = 2004;
		/**
		 * 保养2005
		 */
		public static final int TYPE_BAOYANG = 2005;
	}
}