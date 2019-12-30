package com.edu.service;

import com.edu.common.ServerResponse;
import com.edu.pojo.User;

public interface IUserService {
    /**
     * 注册接口
     */
     ServerResponse register(User user);
    /**
     * 登录接口
     * @type 1:普通用户 0：管理员
     */
     ServerResponse login(String username ,String password,int type);
    /**
     * 根据用户名获取密保问题接口
     * forget_get_question.do?username=admin
     */
     ServerResponse forget_get_question(String username);

    /**
     * 提交答案接口
     * /user/forget_check_answer.do
     */
     ServerResponse forget_check_answer(String username,String question,String answer);
    /**
     *修改密码
     * /user/forget_reset_password.do
     */
     ServerResponse forget_reset_password(String username,String newpassword,String forgettoken);
    /**
     * 更新用户部分信息
     */
    ServerResponse updateUserByActivate( User user);
    /**
     * 登录状态下修改密码
     */
    ServerResponse  reset_password(String passwordOld,String passwordNew,String username);
    /**
     * 根据用户ID查用户名
     */
    String findUserNameByUserId(Integer userId);
    /**
     * 更新用户角色
     */
    int updateUserStatus(String username,Integer role);

}
