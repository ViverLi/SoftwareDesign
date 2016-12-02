package com.service;

import org.springframework.stereotype.Service;

import com.mybatis.entity.User;

@Service
public interface userService {
	
	User login();
}
