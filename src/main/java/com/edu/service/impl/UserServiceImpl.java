package com.edu.service.impl;

import com.edu.common.ResponseCode;
import com.edu.common.RoleEnum;
import com.edu.common.ServerResponse;
import com.edu.dao.UserMapper;
import com.edu.pojo.User;
import com.edu.service.IUserService;
import com.edu.untils.MD5Utils;
import com.edu.untils.RedisApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    RedisApi redisApi;

    @Override
    public ServerResponse register(User user) {
        //1.参数的校验
        if (user == null) {
            return ServerResponse.createServerResponseByError(ResponseCode.PARAM_NOT_NULL, "参数不能为空");
        }
        //2.判断用户名是否存在
        int result = userMapper.isexistsusername(user.getUsername());
        if (result > 0) {
            return ServerResponse.createServerResponseByError(ResponseCode.EXIST_USER, "用户名已经存在");
        }
        //3.邮箱是否存在
        int resultEmail = userMapper.isexistsemail(user.getEmail());
        if (resultEmail > 0) {
            return ServerResponse.createServerResponseByError(ResponseCode.EXIST_EMAIL, "邮箱已经存在");
        }
        //4.密码加密，设置用户角色
        user.setPassword(MD5Utils.getMD5Code(user.getPassword()));
        //设置角色为普通用户
        user.setRole(RoleEnum.ROLE_User.getRole());
        //5.注册
        int insertResult = userMapper.insert(user);
        //6.返回
        if (insertResult <= 0) {
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "注册失败");
        }
        return ServerResponse.createServerResponseBySuccess();

    }

    @Override
    public ServerResponse login(String username, String password, int type) {
        //1.参数的校验
        if (username == null || username.equals("")) {
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "用户名不能为空");
        }
        if (password == null || password.equals("")) {
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "密码不能为空");
        }
        //2.判断用户名是否存在
        int result = userMapper.isexistsusername(username);
        if (result <= 0) {
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "用户名不存在");
        }
        //3.存在用户名密码加密
        password = MD5Utils.getMD5Code(password);
        //4.登录
        User user = userMapper.findUserAndPwd(username, password);
        if (user == null) {
            return ServerResponse.createServerResponseByError(ResponseCode.EMPTY_CART, "密码错误");
        }
        if (type == 0) {//管理员
            if (user.getRole() == RoleEnum.ROLE_User.getRole()) {//没有权限
                return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "登录权限不足");


            }
        }

        return ServerResponse.createServerResponseBySuccess(user);
    }

    @Override
    public ServerResponse forget_get_question(String username) {
        //1.参数非空校验
        if (username == null || username.equals("")) {
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "用户名不能为空");
        }
        //2.根据用户名查询问题
        String question = userMapper.forget_get_question(username);
        //3.返回结果
        if (question == null) {
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "没有查询到密保问题");
        }

        return ServerResponse.createServerResponseBySuccess(question);
    }

    @Override
    public ServerResponse forget_check_answer(String username, String question, String answer) {
        //1.参数非空校验
        if (username == null || username.equals("")) {
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "密保问题不能为空");
        }
        //2.根据用户名查询问题
        int result = userMapper.forget_check_answer(username, question, answer);
        //3.返回结果
        if (result <= 0) {
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "答案错误");
        }
        //生成token生成唯一表示
        String token = UUID.randomUUID().toString();
//        TokenCache.set("username:" + username, token);
        //redis存储token
        redisApi.setex("username:"+username,token,12*3600);

        return ServerResponse.createServerResponseBySuccess(token);
    }

    @Override
    public ServerResponse forget_reset_password(String username, String newpassword, String forgettoken) {

        //1.参数的校验
        if (username == null || username.equals("")) {
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "用户名不能为空");
        }
        if (newpassword == null || newpassword.equals("")) {
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "新密码不能为空");
        }
        if (forgettoken == null || forgettoken.equals("")) {
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "token不能为空");
        }

        //是否修改的自己账号
      //  String token = TokenCache.get("username:" + username);
        String token = redisApi.get("username:"+username,String.class);
        if (token == null) {
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "不能修改他人密码或者token已经过期");
        }
        if (!token.equals(forgettoken)) {
            return ServerResponse.createServerResponseByError(ResponseCode.UNLAWFULNESS_TOKEN, "无效的token");
        }
        //2.根据用户名密码修改
        int result = userMapper.forget_reset_password(username, MD5Utils.getMD5Code(newpassword));
        if (result <= 0) {
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "密码修改失败");

        }

        return ServerResponse.createServerResponseBySuccess();
    }

    @Override
    public ServerResponse updateUserByActivate(User user) {
        //1.非空判断
        if (user == null) {
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "参数不能为空");
        }
        //修改密码的话 要给密码MD5加密
        if (user.getPassword() != null) {
            user.setPassword(MD5Utils.getMD5Code(user.getPassword()));
        }
        //修改Email要看是否重复
        if (user.getEmail() != null) {
            int resultEmail = userMapper.isexistsemail(user.getEmail());
            if (resultEmail > 0) {
                return ServerResponse.createServerResponseByError(ResponseCode.EXIST_EMAIL, "邮箱已经存在");
            }
        }

        int result = userMapper.updateUserByActivate(user);
        if (result <= 0) {
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "修改失败");

        }

        return ServerResponse.createServerResponseBySuccess();
    }

    @Override
    public ServerResponse reset_password(String passwordOld, String passwordNew, String username) {
        //1.参数非空判断
        if (passwordNew == null || passwordNew.equals("")) {
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "参数不能为空");
        }
        if (passwordOld == null || passwordOld.equals("")) {
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "参数不能为空");
        }
        //2.密码加密处理
        passwordOld = MD5Utils.getMD5Code(passwordOld);
        passwordNew = MD5Utils.getMD5Code(passwordNew);
        //3.根据用户名密码去查询是否是正确的用户名和密码
        User user = userMapper.findUserAndPwd(username, passwordOld);
        if (user == null) {
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "旧密码输入错误");
        }
        Integer id = user.getId();
        //3.去修改
        int result = userMapper.updateLoginPassword(id, passwordNew);
        if (result <= 0) {
            return ServerResponse.createServerResponseByError(ResponseCode.DEFEACTED_PASSWORDNEW, "修改密码操作失败");
        }

        return ServerResponse.createServerResponseBySuccess("修改密码成功");
    }
}
