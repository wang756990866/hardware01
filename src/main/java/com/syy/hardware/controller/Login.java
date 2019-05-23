package com.syy.hardware.controller;

import com.syy.hardware.config.ShiroRealm;
import com.syy.hardware.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName Login
 * @Description TODO
 * @Author Administrator
 * @Data 2019/4/3 16:18
 * @Version 1.0
 **/
@RestController
@Api(value = "/user", description = "用户登录")
@RequestMapping("/user")
public class Login {

    private Logger logger = LoggerFactory.getLogger(ShiroRealm.class);
    /**
     * 登录方法
     * 在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的      认证检查
     * 每个Realm都能在必要时对提交的AuthenticationTokens作出反应
     * @param userName
     * @param passWord
     * @return
     * @throws AuthenticationException
     */
    /*@RequestMapping(method={RequestMethod.GET, RequestMethod.POST },value = "/loginUser")
    @ResponseBody
    @ApiOperation(value="登录", notes="系统登录")
    @CrossOrigin(allowCredentials="true",maxAge = 3600,origins = "*")
    public Object loginUser(String userName, String passWord ) throws AuthenticationException {
        boolean rememberMe = false;
        Map<String, Object> result = new HashMap<>();
        result.put("code", "200");
        result.put("msg", "登录成功");
        Subject subject = SecurityUtils.getSubject();
        //已经登录过
        if (subject.isAuthenticated()) {
            User user = (User) subject.getPrincipal();
            result.put("user", user);
            logger.info("subject.isAuthenticated,userName:{}, login success", user.getNickname());
            boolean test=false;
            return result;
        }
        //勾选了记住我
        if (subject.isRemembered()) {
            User user = (User) subject.getPrincipal();
            result.put("user", user);
            logger.info("subject.isRemembered,userName:{},login success", user.getNickname());
            return result;
        }
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(userName, passWord, true);
        usernamePasswordToken.setRememberMe(rememberMe);
        try {
            subject.login(usernamePasswordToken);
            User user = (User) subject.getPrincipal();
            result.put("user", user);
            result.put("code", "20000");
            logger.info("userName:{},passWord:{} login success", userName, passWord);
        } catch (UnknownAccountException e) {
            //用户名不存在
            result.put("code", "50008");
            result.put("msg", "用户名不存在");
            logger.error("userName:{},passWord:{} login fail,error info is:{}", userName, passWord, e.getMessage());
        } catch (IncorrectCredentialsException e) {
            //密码错误
            result.put("code", "50008");
            result.put("msg", "用户名或密码错误");
            logger.error("userName:{},passWord:{} login fail,error info is:{}", userName, passWord, e.getMessage());
        } catch (LockedAccountException e) {
            //账户被锁定
            result.put("code", "-1");
            result.put("msg", "账户被锁定");
            logger.error("userName:{},passWord:{} login fail,error info is:{}", userName, passWord, e.getMessage());

        } catch (ExcessiveAttemptsException e) {
            //登录失败次数超过系统最大次数,请稍后重试
            result.put("code", "-1");
            result.put("msg", "登录失败次数超过系统最大次数,请稍后重试!");
            logger.error("userName:{},passWord:{} login fail,error info is:{}", userName, passWord, e.getMessage());

        } catch (DisabledAccountException e) {
            //验证未通过,帐号已经禁止登录
            result.put("code", "-1");
            result.put("msg", "验证未通过,帐号已经禁止登录!");
            logger.error("userName:{},passWord:{} login fail,error info is:{}", userName, passWord, e.getMessage());

        } catch (AuthenticationException e) {
            //出现其他异常
            result.put("code", "-1");
            result.put("msg", e.getMessage());
            logger.error("userName:{},passWord:{} login fail,error info is:{}", userName, passWord, e.getMessage());
        }

        return result;
    }*/


    @PostMapping("/getUsers")
    @ResponseBody
    @ApiOperation(value="测试返回", notes="测试返回")
    @CrossOrigin(allowCredentials="true",maxAge = 3600,origins = "*")
    public String getUser(String userName ,String passWord){
        return  "测试返回 userName : " +userName + "; passWord:"+ passWord;
    }
}
