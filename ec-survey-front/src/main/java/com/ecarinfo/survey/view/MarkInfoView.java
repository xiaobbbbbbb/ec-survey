package com.ecarinfo.survey.view;

import java.io.Serializable;
import java.util.Date;

import com.ecarinfo.common.utils.DateUtils;
import com.ecarinfo.survey.po.MarkInfo;
import com.ecarinfo.utils.poi.ExcelResources;

public class MarkInfoView extends MarkInfo implements Serializable {

	private static final long serialVersionUID = 152354670188495252L;
	private String img;
	private String typeName; // 标注分类
	@ExcelResources(title = "标注类型", order = 2)
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	@Override
	public Long getId() {
		return super.getId();
	}
	
	@ExcelResources(title = "标注名称", order = 1)
	public String getName() {
		return super.getName();
	}

	@Override
	public Integer getType() {
		return super.getType();
	}
	@ExcelResources(title = "标注点详细地址", order = 3)
	public String getAddress() {
		return super.getAddress();
	}

	@Override
	public Double getBaiduLongitude() {
		return super.getBaiduLongitude();
	}

	@Override
	public Double getBaiduLatitude() {
		return super.getBaiduLatitude();
	}
	
	public Integer getStatus() {
		return super.getStatus();
	}
	
	public Date getCreateTime() {
		return super.getCreateTime();
	}

	@Override
	public Date getUpdateTime() {
		return super.getUpdateTime();
	}
	
	@ExcelResources(title = "标注状态", order = 4)
	public String getStatusType(){
		if(getStatus()==0){
			return "未标注";
		}else if(getStatus()==1){
			return "已标注";
		}else{
			return "无效点";
		}
	}
	
	@ExcelResources(title = "创建时间", order = 5)
	public String getCreateTimes(){
		if(getCreateTime()!= null){
			return DateUtils.dateToString(getCreateTime(), "yyyy-MM-dd HH:mm");
		}else{
			return "";
		}
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
}
