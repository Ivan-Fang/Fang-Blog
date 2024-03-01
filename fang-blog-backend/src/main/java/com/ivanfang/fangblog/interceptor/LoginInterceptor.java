package com.ivanfang.fangblog.interceptor;

import com.ivanfang.fangblog.utils.JwtUtil;
import com.ivanfang.fangblog.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            // use jwt token to implement login verification
            String jwtToken = request.getHeader("Authorization");
            Map<String, Object> claims = JwtUtil.parseToken(jwtToken);

            // save user info into ThreadLocal
            ThreadLocalUtil.set(claims);

            // check if the token is saved in redis
            if (stringRedisTemplate.opsForValue().get(jwtToken) == null) {
                throw new RuntimeException();
            }

            return true;
        } catch (Exception e) {
            response.setStatus(401);    // error 401 : Unauthorized
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // remove the data in ThreadLocal to prevent memory leakage
        ThreadLocalUtil.remove();
    }

}
