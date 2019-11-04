package com.edu.interceptor;

import com.edu.common.ResponseCode;
import com.edu.common.RoleEnum;
import com.edu.common.ServerResponse;
import com.edu.pojo.User;
import com.edu.untils.Const;
import com.google.gson.Gson;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 自定义管理员登录拦截器
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session= request.getSession();
        User user=(User) session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            response.reset();
            try {
                response.setHeader("Content-Type","application/json;charset=UTF-8");
                PrintWriter printWriter=response.getWriter();
                ServerResponse serverResponse=ServerResponse.createServerResponseByError(ResponseCode.NO_LOGIN,"未登录");
                Gson gson=new Gson();
                String json=gson.toJson(serverResponse);
                printWriter.write(json);
                printWriter.flush();
                printWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }

        int role=user.getRole();
        if(role== RoleEnum.ROLE_User.getRole()){
            response.reset();
            try {
                response.setHeader("Content-Type","application/json;charset=UTF-8");
                PrintWriter printWriter=response.getWriter();

                ServerResponse serverResponse= ServerResponse.createServerResponseByError(ResponseCode.ERROR,"权限不足");
                Gson gson=new Gson();
                String json = gson.toJson(serverResponse);
                printWriter.write(json);
                printWriter.flush();
                printWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return false;
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
