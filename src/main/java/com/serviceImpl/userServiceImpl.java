package com.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;

import com.mybatis.entity.User;
import com.mybatis.mapper.UserMapper;
import com.service.userService;

public class userServiceImpl implements userService{
	
	
	public UserMapper userMapper;

	public User login() {
		User user=userMapper.selectByPrimaryKey(1);
		System.out.println(user.getUsername());
		return null;
	}

}
