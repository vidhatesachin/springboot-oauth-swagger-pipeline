package com.springboot.api.controller;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.springboot.api.domain.User;
import com.springboot.api.exception.InvalidInputException;
import com.springboot.api.exception.ResourceConflictException;
import com.springboot.api.exception.ResourceNotFoundException;
import com.springboot.api.repository.UserRepository;
import com.springboot.api.util.CommonUtils;
import com.springboot.api.util.SecretEncoder;
import com.springboot.api.vo.UserVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value="User operations")
public class UserController {
	private final Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private SecretEncoder cryptoLibrary;
	
	@PostMapping(value = "/api/v1/user/signup")
	@Transactional
	@ApiOperation(value = "Register to app.",response = HttpStatus.class)
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created account"),
            @ApiResponse(code = 409, message = "Conflict: User with given email id already exists"),
            @ApiResponse(code = 404, message = "Resource not found")
    })
	public ResponseEntity<HttpStatus> register(@RequestBody UserVO userVO) {
		logger.info("register called {}", userVO.toString());
		if(!this.mandatoryValuesPresent(userVO)) {
			throw new InvalidInputException("Mandatory fields are missing");
		}
		
		if(StringUtils.isEmpty(userVO.getEmail()) || !CommonUtils.validateEmail(userVO.getEmail())) {
			throw new InvalidInputException("Enter valid email address");
		}
		
		User existingUser = userRepository.findByEmail(userVO.getEmail());
		if (existingUser!= null) {
			throw new ResourceConflictException("User with given email id already exist");
		}
		
		if(StringUtils.isEmpty(userVO.getPassword()) || !this.validPassword(userVO.getPassword())) {
			throw new InvalidInputException("Password does not meet minimum complexity. Min chars should be 8 & max chars should be 16");
		}
		
		User user=new User();
		BeanUtils.copyProperties(userVO, user);
		user.setPassword(this.cryptoLibrary.encrypt(userVO.getPassword()));
		this.userRepository.save(user);
		return new ResponseEntity<HttpStatus>(HttpStatus.CREATED);
		
	}
	
	@PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
	@GetMapping(value = "/api/v1/user")
	@ApiOperation(value = "Find user profile information for logged in user",response = HttpStatus.class)
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully fetched the profile"),
            @ApiResponse(code = 404, message = "User not found for given email id")
    })
	public ResponseEntity<UserVO> getUser(Authentication authentication) {
		logger.info("getUser called {}", authentication.getName());
		if(!authentication.isAuthenticated()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		User user=this.userRepository.findByEmail(authentication.getName());
		if (user == null) {
			throw new ResourceNotFoundException("User not found with give email id");
		}
		UserVO userVO=new UserVO();
		BeanUtils.copyProperties(user, userVO);
		userVO.setPassword(null);
		return new ResponseEntity<UserVO>(userVO, HttpStatus.OK);
	}
	
	private boolean mandatoryValuesPresent(UserVO userVO) {
		if(StringUtils.isEmpty(userVO.getName()) || StringUtils.isEmpty(userVO.getEmail()) || StringUtils.isEmpty(userVO.getPassword()) ) {
			return false;
		}
		return true;
	}


	private boolean validPassword(String pass) {
		if(pass.length()>=8 && pass.length()<=16) {
			return true;
		}
		return false;
	}
 
	
}
