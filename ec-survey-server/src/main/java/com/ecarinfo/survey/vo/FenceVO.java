package com.ecarinfo.survey.vo;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.ecarinfo.common.utils.JSONUtil;
import com.ecarinfo.survey.po.FenceInfo;

public class FenceVO extends Model {
	private static final long serialVersionUID = 7329071071256876312L;
	List<Point> points;
	private Integer id;
	private String name;
	private String description;

	public List<Point> getPoints() {
		return points;
	}

	public void init(FenceInfo fenceInfo) {
		this.id = fenceInfo.getId();
		this.name = fenceInfo.getName();
		this.description = fenceInfo.getDescription();
		List<Point> points = new ArrayList<Point>();
		List<LinkedHashMap<String, Double>> list = JSONUtil.fromJson(fenceInfo.getPoints(), List.class);
		for (LinkedHashMap<String, Double> map : list) {
			int x = (int) (map.get("lng") * 1000000);
			int y = (int) (map.get("lat") * 1000000);
			Point point = new Point(x, y);
			points.add(point);
		}
		this.points = points;
	}

	public static void main(String[] args) {
		String ps = "[{\"lng\":113.942459,\"lat\":22.557152}]";
		System.err.println(ps);
		List<LinkedHashMap<String, Double>> list = JSONUtil.fromJson(ps,List.class);
		System.err.println(list);
		for (LinkedHashMap<String, Double> map : list) {
			int x = (int) (map.get("lng") * 1000000);
			int y = (int) (map.get("lat") * 1000000);
			System.err.println(x + "," + y);
		}

	}

	public void setPoints(List<Point> points) {
		this.points = points;
	}

	public Integer getId() {
		return id;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
