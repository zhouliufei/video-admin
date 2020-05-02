package com.yefeng.interceptor;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.yefeng.annotation.LoginRequired;
import com.yefeng.util.JsonResult;
import com.yefeng.util.MD5Util;
import org.springframework.http.HttpHeaders;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //如果不是映射到方法，直接通过
        if(!(handler instanceof HandlerMethod)) {
            return true;
        }
        //方法注解级拦截器
        HandlerMethod handlerMethod = (HandlerMethod)handler;
        Method method = handlerMethod.getMethod();
        LoginRequired loginAnnotation = method.getAnnotation(LoginRequired.class);
        //需要验证
        if(loginAnnotation != null) {
            try{
                String token = request.getHeader("token").toString();
                String userKey = request.getHeader("userKey").toString();
                if(MD5Util.encodeByUserId(userKey).equals(token)) {
                    return true;
                }
                else {
                   throw new Exception("用户无访问权限");
                }
            }catch (Exception e) {
                response.setHeader("Access-Control-Allow-Origin","*");
                response.setHeader("Access-Control-Allow-Headers","content-type");
                response.setHeader("Access-Control-Allow-Credentials","true");
                response.setContentType("application/json; charset=utf-8");
                response.setCharacterEncoding("utf-8");
                PrintWriter write = response.getWriter();
                write.write("403");
                return false;
            }
        }
        return true;
    }


}
