package com.controller;

import java.io.InputStream;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mybatis.entity.User;
import com.mybatis.mapper.UserMapper;

@Controller
public class testController {
	
	@Autowired
	UserMapper userMapper;
	
	@RequestMapping("/getindex")
	public String getIndex(){
		System.out.println("enter getindex");
		User user=userMapper.selectByPrimaryKey(4);
		System.out.println(user.getUsername());
		return "index";
	}
	
	@RequestMapping("/index")
	public String Index(){
		
		return "index";
	}
}
