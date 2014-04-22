package com.ecarinfo.survey.dto;

import com.ecarinfo.common.utils.DateUtils;
import com.ecarinfo.common.utils.DateUtils.TimeFormatter;
import com.ecarinfo.survey.po.AreaInfo;
import com.ecarinfo.survey.po.CarInfo;
import com.ecarinfo.survey.po.EventInfo;
import com.ecarinfo.survey.po.MarkInfo;

public class AreaTreeDto {
	private Integer id;
	private Integer pId;
	private String name;
	private String open;
	private String url;
	private String target = "self";
	private FontDto font = FontDto.getDefaultFont();
	private Boolean isParent;
	private String carNo;
	
	public void init(AreaInfo area) {
		this.id = area.getId();
		this.pId = area.getPid();
		this.isParent = true;
		this.name = area.getName();
		this.open = "true";
//		this.url = "carLocate?areaId="+area.getId();
	}
	
	public void init(CarInfo carInfo) {
		this.id = carInfo.getId()+1000;
		this.pId = carInfo.getAreaId();
		this.name = carInfo.getCarNo();
		this.open = "true";
		this.carNo = carInfo.getCarNo();
		if(carInfo.getStatus() == 0) {//待命
			this.font = FontDto.getFont("green");
		}
		if(carInfo.getStatus() == 1) {//工作中
			this.font = FontDto.getFont("red");
		}
		if(carInfo.getStatus() == 2) {
			this.font = FontDto.getFont("gray");
		}
	}
	
	public void init(MarkInfo mark,int pid) {
		this.id = mark.getId().intValue()+4;
		this.pId = pid;
		this.name = DateUtils.dateToString(mark.getCreateTime(), TimeFormatter.FORMATTER5)+ mark.getName();
		this.open = "true";
	}
	
	public void init(EventInfo event,int pid) {
		this.id = event.getId().intValue()+4;
		this.pId = pid;
		this.name = DateUtils.dateToString(event.getCreateTime(), TimeFormatter.FORMATTER5)+ event.getName();
		this.open = "true";
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getpId() {
		return pId;
	}

	public void setpId(Integer pId) {
		this.pId = pId;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOpen() {
		return open;
	}

	public void setOpen(String open) {
		this.open = open;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public Boolean getIsParent() {
		return isParent;
	}

	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
	}

	public String getCarNo() {
		return carNo;
	}

	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}

	public FontDto getFont() {
		return font;
	}

	public void setFont(FontDto font) {
		this.font = font;
	}

}
