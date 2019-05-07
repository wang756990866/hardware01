package com.syy.hardware.controller;

import com.syy.hardware.config.ShiroRealm;
import com.syy.hardware.dao.*;
import com.syy.hardware.entity.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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
    @Autowired
    @Lazy
    private UUserDao uUserDao;
    @Autowired
    @Lazy
    private URoleDao uRoleDao;
    @Autowired
    @Lazy
    private UPermissionDao uPermissionDao;
    @Autowired
    @Lazy
    private com.syy.hardware.dao.UserRoleDao userRoleDao;

    @Autowired
    @Lazy
    private  RolePermissionDao rolePermissionDao;
    // 初始化 成功标识
    boolean result = false;
    // 初始化
    User uUser = new User();
    URole uRole = new URole();
    UPermission uPermission = new UPermission();
    UserRole userRole = new UserRole();
    RolePermission rolePermission = new RolePermission();

    private Logger logger = LoggerFactory.getLogger(ShiroRealm.class);

    @GetMapping("/getUsers")
    @ResponseBody
    @ApiOperation(value="测试返回", notes="测试返回")
    public String getUser(){


        return  "测试返回";
    }
    @GetMapping("/getTestString")
    @ResponseBody
    @ApiOperation(value="测试返回", notes="测试返回")
    public String getTestString(String name,String val){

        logger.info("测试返回,name:"+name+", val :" +val);
        return  "测试返回,name:"+name+", val :" +val;
    }

    //http://localhost:8888/saveUser?userName=xiaoli&userPassword=111
    //保存用户
    @ApiOperation(value="测试添加", notes="测试添加")
    @GetMapping("saveUser")
    public String saveUser(String id,String userName,String userPassword,String val)
    {
        User user=new User();
        user.setId(id);
        user.setNickname(userName);
        user.setPswd(userPassword);
        user.setEmail(val);
        Integer index = UserDao.insert(user);
        if(index>0){
            return "新增用户成功。";
        }else{
            return "新增用户失败。";
        }
    }


    @GetMapping("/testLogin")
    @ResponseBody
    @ApiOperation(value="测试登陆", notes="测试返回")
    public String testLogin(String name){
        /*//查出是否有此用户
        List<User> nickname = this.uUserDao.selectList(
                new EntityWrapper<User>().eq("nickname", name)
        );
        //用户的角色集合
        List<String> roleStrlist = new ArrayList<String>();
        //用户的权限集合
        List<String> perminsStrlist = new ArrayList<String>();

        //String md5Pwd = new Md5Hash("123", "lucare",2).toString();
        // 若存在，将此用户存放到登录认证info中，无需自己做密码对比，Shiro会为我们进行密码对比校验
        if (nickname.size() > 0) {
            User hasUser = nickname.get(0);


            List<UserRole> userRoles = this.userRoleDao.selectList(
                    new EntityWrapper<UserRole>().eq("uid",hasUser.getId())
            );

            if (userRoles != null) {

                for (int i = 0;i < userRoles.size(); i++ ){
                    //通过角色id获取用户权限id
                    List<RolePermission> plist = this.rolePermissionDao.selectList(
                            new EntityWrapper<RolePermission>().eq("rid", userRoles.get(0).getRid())
                    );

                    URole uRole = this.uRoleDao.selectById(userRoles.get(0).getRid());
                    roleStrlist.add(uRole.getName());

                    //通过权限id循环获取具体权限
                    for (int y = 0; y < plist.size(); y++) {

                        UPermission uPermission1 = this.uPermissionDao.selectById(plist.get(y).getPid());

                        List<UPermission> permissionList = this.uPermissionDao.selectList(
                                new EntityWrapper<UPermission>().eq("id", plist.get(y).getPid())
                        );
                        UPermission uPermission = permissionList.get(0);
                        perminsStrlist.add(uPermission.getName());

                    }
                }

            }


            hasUser.setRoleStrlist(roleStrlist);
            hasUser.setPerminsStrlist(perminsStrlist);
            return  "测试成功";
        }*/
        return  "测试失败";
    }

}
