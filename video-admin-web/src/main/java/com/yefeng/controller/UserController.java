package com.yefeng.controller;

import com.yefeng.annotation.LoginRequired;
import com.yefeng.dto.AdminUser;
import com.yefeng.dto.LoginUser;
import com.yefeng.pojo.User;
import com.yefeng.service.UserService;
import com.yefeng.util.JsonResult;
import com.yefeng.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    @ResponseBody
    public JsonResult userLogin(@RequestBody LoginUser user) {
        if(StringUtil.isEmpty(user.getUsername()) || StringUtil.isEmpty(user.getPassword())) {
            return JsonResult.errorMessage("用户名和密码不能为空");
        }
        return userService.login(user.getUsername(),user.getPassword());
    }
}
