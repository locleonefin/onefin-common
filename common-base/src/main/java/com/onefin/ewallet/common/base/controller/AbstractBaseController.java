package com.onefin.ewallet.common.base.controller;

import com.onefin.ewallet.common.base.constants.BaseServicePath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public abstract class AbstractBaseController implements IBaseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractBaseController.class);

	@RequestMapping(value = BaseServicePath.GET_ABOUT_PATH_1, method = RequestMethod.GET)
	@Override
	public @ResponseBody
	ResponseEntity<?> about() { // Check config with only String return ????
		return new ResponseEntity<>("Successful", HttpStatus.OK);
	}

	@GetMapping("/timezone")
	public ResponseEntity<?> getTimezone(TimeZone timezone) {
		LOGGER.info("Time zone {}", timezone);
		Map<String, Object> response = new HashMap<>();
		response.put("data", timezone);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}