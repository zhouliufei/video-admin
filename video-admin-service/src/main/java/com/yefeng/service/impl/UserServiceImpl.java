package com.yefeng.service.impl;

import com.yefeng.dto.AdminUser;
import com.yefeng.mapper.UserMapper;
import com.yefeng.pojo.User;
import com.yefeng.service.UserService;
import com.yefeng.util.JsonResult;
import com.yefeng.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    static final String USER_NAME = "admin";

    @Autowired
    private UserMapper userMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    public User queryUser(String id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public JsonResult login(String username, String password) {
        if(!USER_NAME.equals(username)) {
            return JsonResult.errorMessage("账户不对，登录失败");
        }
        List<User> userList = userMapper.login(username,password);
        if(userList == null || userList.size() == 0) {
            return JsonResult.errorMessage("用户不存在,请先注册");
        }
        User user = userList.get(0);
        String token = MD5Util.encodeByUserId(user.getUsername());
        AdminUser adminUser = new AdminUser();
        adminUser.setUsername(user.getUsername());
        adminUser.setToken(token);
        return JsonResult.ok(adminUser);
    }
}
