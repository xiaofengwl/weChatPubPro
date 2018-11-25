package com.xfwl.token;

public class AccessToken {
	private String tokenName;
	private int expireSecond;

	public String getTokenName() {
		return this.tokenName;
	}

	public void setTokenName(String tokenName) {
		this.tokenName = tokenName;
	}

	public int getExpireSecond() {
		return this.expireSecond;
	}

	public void setExpireSecond(int expireSecond) {
		this.expireSecond = expireSecond;
	}
}
