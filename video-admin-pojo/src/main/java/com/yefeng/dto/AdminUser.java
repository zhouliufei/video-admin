package com.yefeng.dto;

import lombok.Data;

@Data
public class AdminUser {

    private String token;

    private String username;

    private String password;

    public AdminUser() {}

    public AdminUser(String username,String password,String token) {
        this.username = username;
        this.password = password;
        this.token = token;
    }

}
