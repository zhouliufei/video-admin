package com.yefeng.service;

import com.yefeng.pojo.User;
import com.yefeng.util.JsonResult;

public interface UserService {

    /**
     * 根据用户id查询用户信息
     * @return
     */
    User queryUser(String id);

    /**
     * 根据用户名和密码登录
     * @return
     */
    JsonResult login(String username, String password);
}
