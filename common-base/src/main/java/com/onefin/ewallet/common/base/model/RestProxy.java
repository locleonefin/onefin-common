package com.onefin.ewallet.common.base.model;

import lombok.Data;

@Data
public class RestProxy {

	private boolean active; // true via proxy, false not via proxy

	private String host;

	private int port;
	
	private boolean auth;

	private String userName;

	private String password;

}
