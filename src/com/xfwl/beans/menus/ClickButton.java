package com.xfwl.beans.menus;

/**
 * Click按钮
 * 
 * @author Jason
 * 
 */
public class ClickButton extends BasicButton {
	private String key;

	public ClickButton() {
		super.setType("click");
	}

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}
