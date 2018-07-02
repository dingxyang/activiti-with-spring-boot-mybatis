package com.dxy.service;

import com.dxy.dto.UserDto;
import com.dxy.mapper.UserMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @ResponseBody
    @PostMapping(value = "/add")
    public int addUser(@RequestBody UserDto user){
        return userMapper.insert(user);
    }

    @ResponseBody
    @GetMapping(value = "/all")
    public List<UserDto> findAllUser(){
        return userMapper.selectAllUser();
    }


}
