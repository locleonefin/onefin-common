package com.onefin.ewallet.common.base.search;

public class Sort {

	public static org.springframework.data.domain.Sort.Direction getSortDirection(String direction) {
		if (direction.equals("asc")) {
			return org.springframework.data.domain.Sort.Direction.ASC;
		} else if (direction.equals("desc")) {
			return org.springframework.data.domain.Sort.Direction.DESC;
		}

		return org.springframework.data.domain.Sort.Direction.ASC;
	}

}
