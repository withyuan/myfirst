package com.edu.controller.backend;

import com.edu.common.RoleEnum;
import com.edu.common.ServerResponse;
import com.edu.service.IUserService;
import com.edu.untils.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
@RestController
@RequestMapping(value = "/manage/")
public class UserManagerController {
    @Autowired
    IUserService userService;
    /**
     * 登录接口
     */
    @RequestMapping(value= "login")
    public ServerResponse login(String username,
                                String password,
                                HttpSession session){
        ServerResponse serverResponse = userService.login(username, password, RoleEnum.ROLE_ADMIN.getRole());
        if (serverResponse.isSuccess()){
            session.setAttribute(Const.CURRENT_USER, serverResponse.getData());
        }
        return  serverResponse;
    }







}
