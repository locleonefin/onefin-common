package com.onefin.ewallet.common.domain.base;

public class PeekNumber {

	private long number;
	private long ctime;

	public PeekNumber(long number) {
		this.number = number;
		this.ctime = System.currentTimeMillis();
	}

	public long getNumber() {
		return number;
	}

	public boolean tooOld() {
		return System.currentTimeMillis() > (ctime + 86400000L);
	}
}
