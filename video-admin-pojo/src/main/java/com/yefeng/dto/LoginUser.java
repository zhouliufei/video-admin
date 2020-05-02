package com.yefeng.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LoginUser {
    @NotNull
    private String username;
    @NotNull
    private String password;

}
