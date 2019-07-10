package com.syy.hardware.config;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.syy.hardware.dao.*;
import com.syy.hardware.entity.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ShiroRealm
 * @Description TODO
 * @Author Administrator
 * @Data 2019/4/3 14:07
 * @Version 1.0
 **/
public class ShiroRealm extends AuthorizingRealm {

    private Logger logger = LoggerFactory.getLogger(ShiroRealm.class);

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
    private RolePermissionDao rolePermissionDao;


    /**
     * 必须重写此方法，不然Shiro会报错
     */
    /*@Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }*/


    /**
     * 权限认证
     *
     * @param
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.info("##################执行Shiro权限认证##################");
        //获取当前登录输入的用户名，等价于(String) principalCollection.fromRealm(getName()).iterator().next();
//        String loginName = (String) super.getAvailablePrincipal(principalCollection);
        User user = (User) principalCollection.getPrimaryPrincipal();
//        //到数据库查是否有此对象
//        User user = null;// 实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
//        user = userMapper.findByName(loginName);
        if (user != null) {
            //权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            //用户的角色集合
            info.addRoles(user.getRoleStrlist());
            //用户的权限集合
            info.addStringPermissions(user.getPerminsStrlist());

            return info;
        }
        // 返回null的话，就会导致任何用户访问被拦截的请求时，都会自动跳转到unauthorizedUrl指定的地址
        return null;
    }

    /**
     * 登录认证
     *
     * @param authenticationToken
     * @return
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        logger.info("##################执行Shiro登录认证##################");
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        logger.info("验证当前Subject时获取到token为：" + token.toString());

        //查出是否有此用户
        List<User> nickname = this.uUserDao.selectList(
                new EntityWrapper<User>().eq("nickname", token.getUsername())
        );
        //用户的角色集合
        List<String> roleStrlist = new ArrayList<String>();
        //用户的权限集合
        List<String> perminsStrlist = new ArrayList<String>();

        //String md5Pwd = new Md5Hash("123", "lucare",2).toString();
        // 若存在，将此用户存放到登录认证info中，无需自己做密码对比，Shiro会为我们进行密码对比校验
        if (nickname.size() > 0) {
            User hasUser = nickname.get(0);
            String pswd = hasUser.getPswd();
            String tPassWord = token.getPassword().toString();
            logger.info("tPassWord:" + tPassWord);
            if(!tPassWord.equals("")&&!tPassWord.equals(null)){
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
                Session session = SecurityUtils.getSubject().getSession();
                session.setAttribute("user", hasUser);//成功则放入session
                // 若存在，将此用户存放到登录认证info中，无需自己做密码对比，Shiro会为我们进行密码对比校验
                return new SimpleAuthenticationInfo(hasUser, hasUser.getPswd(), getName());
            }else{
                return null;
            }
        }
        return null;
    }
}
