package com.onefin.ewallet.common.domain.base;

import com.onefin.ewallet.common.domain.constants.DomainConstants;

public class NumberBlock {

	private int mode;
	private long first;
	private long last;
	private long next;

	public NumberBlock(int mode, long first, long last) {
		this.mode = mode;
		this.next = this.first = ((mode == DomainConstants.MODE_ALL
				|| (mode == DomainConstants.MODE_ODD && (first & 1) == 1)
				|| (mode == DomainConstants.MODE_EVEN && (first & 1) == 0)) ? first : (first + 1));
		this.last = last;
	}

	public final boolean isExhausted() {
		return (next >= last);
	}

	public long getNumber() {
		long result = next;

		next += (mode == DomainConstants.MODE_ALL ? 1 : 2);

		return result;
	}

	public long getFirst() {
		return this.first;
	}

	public long getLast() {
		return this.last;
	}

	public long getNext() {
		return this.next;
	}
}
