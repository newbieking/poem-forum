package com.example.interceptor;

import com.example.annotation.MustLogin;
import com.example.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    LoginService loginService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getParameter("token");
        String[] parsedToken = LoginService.parseToken(token);
        String userId = parsedToken[0];
        if (handler instanceof HandlerMethod) {
            MustLogin mustLogin = ((HandlerMethod) handler).getMethodAnnotation(MustLogin.class);
            // need login but not login
            // check invalidate token
            if (!loginService.isLogin(userId) && mustLogin != null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return false;
            }
        }


        return true;
    }
}
