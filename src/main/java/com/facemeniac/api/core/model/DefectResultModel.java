package com.facemeniac.api.core.model;

public class DefectResultModel {
	
	private String image_id;
	private int height;
	private int width;
	private int topLeftX;
	private int topLeftY;
	
	public String getImage_id() {
		return image_id;
	}
	public void setImage_id(String image_id) {
		this.image_id = image_id;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getTopLeftX() {
		return topLeftX;
	}
	public void setTopLeftX(int topLeftX) {
		this.topLeftX = topLeftX;
	}
	public int getTopLeftY() {
		return topLeftY;
	}
	public void setTopLeftY(int topLeftY) {
		this.topLeftY = topLeftY;
	}
	
}
