package com.syy.hardware.controller;

import com.syy.hardware.dao.UserMapper;
import com.syy.hardware.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName TestController
 * @Description TODO
 * @Author Administrator
 * @Data 2019/4/1 15:59
 * @Version 1.0
 **/
@RestController
@Api(value = "/test", description = "用户测试")
@RequestMapping("/test")
public class TestController {

    @Autowired
    private UserMapper UserDao;



    @GetMapping("/getUsers")
    @ResponseBody
    @ApiOperation(value="测试返回", notes="测试返回")
    public String getUser(){


        return  "测试返回";
    }

    //http://localhost:8888/saveUser?userName=xiaoli&userPassword=111
    //保存用户
    @ApiOperation(value="测试添加", notes="测试添加")
    @GetMapping("saveUser")
    public String saveUser(String id,String userName,String userPassword,String val)
    {
        User user=new User();
        user.setUserId(id);
        user.setUserName(userName);
        user.setPassword(userPassword);
        user.setLoginVal(val);
        Integer index = UserDao.insert(user);
        if(index>0){
            return "新增用户成功。";
        }else{
            return "新增用户失败。";
        }
    }

}
