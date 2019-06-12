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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ShiroController
 * @Description TODO
 * @Author Administrator
 * @Data 2019/5/7 14:49
 * @Version 1.0
 **/
@RestController
@Api(value = "/shiro", description = "用户登录")
@RequestMapping("/shiro")
public class ShiroController {

    private Logger logger = LoggerFactory.getLogger(ShiroRealm.class);

    @PostMapping(value = "/login")
    @ApiOperation(value="shiro登录", notes="shiro系统登录")
    @CrossOrigin(allowCredentials="true",maxAge = 3600,origins = "*")
    public Object loginUser(String userName, String passWord ) throws AuthenticationException {
        boolean rememberMe = false;
        Map<String, Object> result = new HashMap<>();
        result.put("code", "20000");
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
            result.put("code", 20000);
            Map<String, Object> data = new HashMap<>();
            data.put("token","admin-token");

            result.put("data", data);
            logger.info("userName:{},passWord:{} login success", userName, passWord);
        } catch (UnknownAccountException e) {
            //用户名不存在
            result.put("code", 50008);
            result.put("msg", "用户名不存在");
            logger.error("userName:{},passWord:{} login fail,error info is:{}", userName, passWord, e.getMessage());
        } catch (IncorrectCredentialsException e) {
            //密码错误
            result.put("code", 50008);
            result.put("msg", "用户名或密码错误");
            logger.error("userName:{},passWord:{} login fail,error info is:{}", userName, passWord, e.getMessage());
        } catch (LockedAccountException e) {
            //账户被锁定
            result.put("code", 1);
            result.put("msg", "账户被锁定");
            logger.error("userName:{},passWord:{} login fail,error info is:{}", userName, passWord, e.getMessage());

        } catch (ExcessiveAttemptsException e) {
            //登录失败次数超过系统最大次数,请稍后重试
            result.put("code", 1);
            result.put("msg", "登录失败次数超过系统最大次数,请稍后重试!");
            logger.error("userName:{},passWord:{} login fail,error info is:{}", userName, passWord, e.getMessage());

        } catch (DisabledAccountException e) {
            //验证未通过,帐号已经禁止登录
            result.put("code",  1 );
            result.put("msg", "验证未通过,帐号已经禁止登录!");
            logger.error("userName:{},passWord:{} login fail,error info is:{}", userName, passWord, e.getMessage());

        } catch (AuthenticationException e) {
            //出现其他异常
            result.put("code", 1 );
            result.put("msg", e.getMessage());
            logger.error("userName:{},passWord:{} login fail,error info is:{}", userName, passWord, e.getMessage());
        }

        return result;
    }

    @PostMapping(value = "/info")
    @ApiOperation(value="tonken验证", notes="tonken验证获取角色")
    @CrossOrigin(allowCredentials="true",maxAge = 3600,origins = "*")
    public Object infoUser(String token) throws AuthenticationException {

        Map<String, Object> result = new HashMap<>();
        Map<String, Object> result2 = new HashMap<>();
        List<String> routList = new ArrayList<>();

        List routsList=new ArrayList();
        Map<String, Object> routsObj = new HashMap<>();
        List yearList=new ArrayList();
        Map<String, Object> yearsObj = new HashMap<>();
        Map<String, Object> yearsMeta = new HashMap<>();
        List itemList=new ArrayList();
        Map<String, Object> itemObj = new HashMap<>();
        Map<String, Object> itemObj1 = new HashMap<>();
        Map<String, Object> itemMate = new HashMap<>();
        Map<String, Object> itemMate1 = new HashMap<>();
        itemMate1.put("title","XX纪委项目");
        itemObj1.put("meta",itemMate1);
        itemObj1.put("name","xxjiweiID");

        itemObj1.put("component","views/section/itemsList");
        itemObj1.put("path","itemsList1/:item");
        itemList.add(itemObj1);

        itemObj.put("component","views/section/itemsList");
        itemObj.put("path","itemsList2/:item");
        yearsObj.put("children",itemList);
        itemMate.put("title","XX青少年项目");
        itemObj.put("meta",itemMate);
        itemObj.put("name","xxqingshaonianID");
        itemList.add(itemObj);
        yearsObj.put("children",itemList);
        yearsMeta.put("title" ,"2019");
        yearsObj.put("meta",yearsMeta);
        yearsObj.put("path","year2019");
        yearsObj.put("name","Year2019");
        yearsObj.put("component","views/section/index");
        yearsObj.put("path","year2019");
        yearList.add(yearsObj);
        yearsMeta.put("title" ,"测试后台返回");
        routsObj.put("meta",yearsMeta);
        routsObj.put("path","/section");
        routsObj.put("component","Layout");
        routsObj.put("name","Section");
        routsObj.put("redirect","/section");
        routsObj.put("children",yearList);
        routsList.add(routsObj);
        result2.put("routs",routsList);
        routList.add("admin");
        result2.put("introduction","我是一个超级管理员");
        result2.put("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        result2.put("name","Super Admin");
        result2.put("roles","admin");
        result2.put("role","admin");
        result.put("data",result2);
        result.put("code",20000);
        logger.info("tonken验证返回："+result.toString());
        return result;
    }

    @PostMapping(value = "/generateRoutes")
    @ApiOperation(value="返回权限具体数据", notes="返回权限具体数据")
    @CrossOrigin(allowCredentials="true",maxAge = 3600,origins = "*")
    public Object generateRoutes(String roles) throws AuthenticationException {

        Map<String, Object> result = new HashMap<>();
        Map<String, Object> result2 = new HashMap<>();
       /* List routsList=new ArrayList();
        Map<String, Object> routsObj = new HashMap<>();
        List yearList=new ArrayList();
        Map<String, Object> yearsObj = new HashMap<>();
        Map<String, Object> yearsMeta = new HashMap<>();
        List itemList=new ArrayList();
        Map<String, Object> itemObj = new HashMap<>();
        Map<String, Object> itemMate = new HashMap<>();


        itemMate.put("title","XX纪委项目");
        itemObj.put("meta",itemMate);
        itemObj.put("name","xxjiweiID");
        itemObj.put("component","views/section/itemsList");
        itemObj.put("path","itemsList/:item");
        itemList.add(itemObj);
        yearsObj.put("children",itemList);
        itemMate.put("title","XX青少年项目");
        itemObj.put("meta",itemMate);
        itemObj.put("name","xxqingshaonianID");
        itemList.add(itemObj);
        yearsObj.put("children",itemList);
        yearsMeta.put("title" ,"2019");
        yearsObj.put("meta",yearsMeta);
        yearsObj.put("path","year2019");
        yearsObj.put("name","Year2019");
        yearsObj.put("component","views/section/index");
        yearsObj.put("path","year2019");
        yearList.add(yearsObj);
        yearsMeta.put("title" ,"测试后台返回");
        routsObj.put("meta",yearsMeta);
        routsObj.put("path","/section");
        routsObj.put("component","Layout");
        routsObj.put("name","Section");
        routsObj.put("redirect","/section");
        routsObj.put("children",yearList);
        routsList.add(routsObj);
        result2.put("routs",routsList);*/
        result.put("data",result2);
        result.put("code",20000);
        logger.info("权限路径数据："+result.toString());
        return result;
    }


    @PostMapping(value = "/logout")
    @ApiOperation(value="登出", notes="用户登出")
    @CrossOrigin(allowCredentials="true",maxAge = 3600,origins = "*")
    public Object userLogout(String token) throws AuthenticationException {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        Map<String, Object> result = new HashMap<>();
        result.put("code", 20000);
        logger.info("用户登出："+result.toString());
        return result;
    }
}
