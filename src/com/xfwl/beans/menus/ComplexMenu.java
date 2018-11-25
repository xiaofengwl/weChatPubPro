package com.xfwl.beans.menus;

/**
 * 二級菜單按鈕
 * 
 * @author Jason
 * 
 */
public class ComplexMenu extends BasicButton {
	private BasicButton[] sub_button;

	public BasicButton[] getSub_button() {
		return this.sub_button;
	}

	public void setSub_button(BasicButton[] sub_button) {
		this.sub_button = sub_button;
	}
}
