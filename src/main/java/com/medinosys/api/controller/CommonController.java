package com.medinosys.api.controller;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.annotations.ApiIgnore;

@RestController
public class CommonController {
	private final static Logger logger = LoggerFactory.getLogger(CommonController.class);
	
	@GetMapping(path = "")
	@ApiIgnore
	public void sayHello(HttpServletResponse httpServletResponse) {
	    httpServletResponse.setHeader("Location", "/swagger-ui.html");
	    httpServletResponse.setStatus(302);
	}



}


