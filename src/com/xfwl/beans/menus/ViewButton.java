package com.xfwl.beans.menus;

/**
 * 视图按钮
 * 
 * @author Jason
 * 
 */
public class ViewButton extends BasicButton {
	private String url;

	public ViewButton() {
		super.setType("view");
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
