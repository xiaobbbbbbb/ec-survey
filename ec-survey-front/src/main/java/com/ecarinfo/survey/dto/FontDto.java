package com.ecarinfo.survey.dto;

public class FontDto {
	private String color;
	private static final FontDto DEFAULT_FONT = new FontDto("black");
	public FontDto(String color) {
		this.color = color;
	}
	
	public FontDto() {
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	public static final FontDto getFont(String color) {
		return new FontDto(color);
	}
	
	public static final FontDto getDefaultFont() {
		return DEFAULT_FONT;
	}
}
