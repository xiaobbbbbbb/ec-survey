package com.ecarinfo.survey.vo;

import java.util.List;

public class TagVO extends BaseVO {
	private static final long serialVersionUID = -8728325951940747109L;
//	private int len;//长度
	private String imei;
	private String rtc;// 数据打包发送时间
	private String lat;// 纬度(0,9)
	private String lng;// 经度(9)
	private String tagCount;// 标签总数
	private List<String> tagIds;

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getRtc() {
		return rtc;
	}

	public void setRtc(String rtc) {
		this.rtc = rtc;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

//	public int getLen() {
//		return len;
//	}
//
//	public void setLen(int len) {
//		this.len = len;
//	}

	public String getTagCount() {
		return tagCount;
	}

	public void setTagCount(String tagCount) {
		this.tagCount = tagCount;
	}

	public List<String> getTagIds() {
		return tagIds;
	}

	public void setTagIds(List<String> tagIds) {
		this.tagIds = tagIds;
	}
	
	@Override
	public String getDescriptorForType() {
		return "TagInfoCommand";
	}

}
