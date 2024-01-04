package com.onefin.ewallet.common.base.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;

public interface IBaseController {

	@ResponseBody
	ResponseEntity<?> about();
}