package com.boot.interceptor;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.boot.bean.R;
import com.boot.util.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token = request.getHeader("Authorization");
        if (token != null && !"".equals(token)) {
            token = token.substring("Bearer ".length());
            try {
                DecodedJWT decodedJWT = JwtUtils.verifyToken(token);
                Integer id = Integer.valueOf(decodedJWT.getClaim("userId").asString());
                String account = decodedJWT.getClaim("account").asString();
                String role = decodedJWT.getClaim("role").asString();
                request.setAttribute("userId", id);
                request.setAttribute("account", account);
                request.setAttribute("role", role);
                return true;
            } catch (Exception e) {
                R r = R.error(401, "认证失败");
                //将map转为json
                String json = new ObjectMapper().writeValueAsString(r);
                // 相应json数据
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().println(json);
                return false;
            }

        } else {
            R r = R.error(401, "认证失败");
            //将map转为json
            String json = new ObjectMapper().writeValueAsString(r);
            // 相应json数据
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().println(json);
            return false;
        }

    }
}
