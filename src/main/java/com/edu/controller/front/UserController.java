package com.edu.controller.front;

import com.edu.common.ResponseCode;
import com.edu.common.RoleEnum;
import com.edu.common.ServerResponse;
import com.edu.pojo.User;
import com.edu.service.IUserService;
import com.edu.untils.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/user/")
@CrossOrigin
public class UserController {
    @Autowired
    IUserService userService;
    /**
     * 注册接口
     */
    @RequestMapping(value = "register.do")
    public ServerResponse register(User user){
       return userService.register(user);

    }
    /**
     * 登录接口
     */
    @RequestMapping(value= "login/{username}/{password}")
    public ServerResponse login(@PathVariable("username")String username,
                                @PathVariable("password")String password,
                                HttpSession session){
        ServerResponse serverResponse = userService.login(username, password, RoleEnum.ROLE_User.getRole());
        if (serverResponse.isSuccess()){
            session.setAttribute(Const.CURRENT_USER, serverResponse.getData());
        }
        return  serverResponse;
    }
    /**
     * 根据用户名获取密保问题接口
     * forget_get_question.do?username=admin
     */
    @RequestMapping(value = "forget_get_question/{username}")
    public ServerResponse forget_get_question(@PathVariable("username")String username){
        return  userService.forget_get_question(username);
    }
    /**
     * 提交答案接口
     * /user/forget_check_answer.do
     */
    @RequestMapping(value = "forget_check_answer.do")
    public ServerResponse forget_check_answer(String username,String question,String answer){
        return  userService.forget_check_answer(username, question, answer);
    }
    /**
     *修改密码
     * /user/forget_reset_password.do
     */
    @RequestMapping(value = "forget_reset_password.do")
    public ServerResponse forget_reset_password(String username,String newpassword,String forgettoken){

        return  userService.forget_reset_password(username, newpassword, forgettoken);
    }
    /**
     *user/update_information.do
     *更新用户部分信息
     */
    @RequestMapping(value = "update_information.do")
    public  ServerResponse update_information(User user,HttpSession session){
        User loginUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (loginUser==null){
            return ServerResponse.createServerResponseByError(ResponseCode.NO_LOGIN,"未登录");

        }
        user.setId(loginUser.getId());
      ServerResponse serverResponse =  userService.updateUserByActivate(user);
        return  serverResponse;

    }
    /**
     * 获取当前登录用户的详细信息
     * /user/get_inforamtion.do
     */
    @RequestMapping(value = "get_inforamtion.do")
    public  ServerResponse get_inforamtion(HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createServerResponseByError(ResponseCode.NO_LOGIN, "用户未登录，无法获取当前用户信息,status=10强制退出");
        }
        return ServerResponse.createServerResponseBySuccess(user);

    }
    /**
     * 退出登录
     * /user/logout.do
     */
    @RequestMapping(value = "logout.do")
    public  ServerResponse logout(HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "服务端异常");
        }
        session.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.createServerResponseBySuccess("退出成功");

    }

    /**
     * .登录中状态重置密码
     * /user/reset_password.do
     */
    @RequestMapping(value = "reset_password.do")
    public ServerResponse reset_password(
            String passwordOld,String passwordNew,HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createServerResponseByError(ResponseCode.NO_LOGIN, "用户没有登录或者session过期");
        }
        String username = user.getUsername();
        return userService.reset_password(passwordOld, passwordNew, username);

    }



}
