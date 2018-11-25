package com.xfwl.beans.menus;

/**
 * 菜单项
 * 
 * @author Jason
 * 
 */
public class Menu {
	public static final String CLICK = "click";
	public static final String VIEW = "view";
	public static final String SCANCODE_WAITMSG = "scancode_waitmsg";
	public static final String SCANCODE_PUSH = "scancode_push";
	public static final String PIC_SYSPHOTO = "pic_sysphoto";
	public static final String PIC_PHOTO_OR_ALBUM = "pic_photo_or_album";
	public static final String PIC_WEIXIN = "pic_weixin";
	public static final String LOCATION_SELECT = "location_select";
	public static final String MEDIA_ID = "media_id";
	public static final String SUB_BUTTON = "sub_button";
	private BasicButton[] button;

	public BasicButton[] getButton() {
		return this.button;
	}

	public void setButton(BasicButton[] button) {
		this.button = button;
	}
}
