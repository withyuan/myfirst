package com.edu.service.impl;

import com.edu.common.ResponseCode;
import com.edu.common.RoleEnum;
import com.edu.common.ServerResponse;
import com.edu.dao.SellerMapper;
import com.edu.pojo.Seller;
import com.edu.pojo.User;
import com.edu.service.ISellerService;
import com.edu.service.IUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service

public class SellerServiceImpl implements ISellerService {
    @Autowired
    IUserService userService;
    @Autowired
    SellerMapper sellerMapper;
    @Override
    public ServerResponse add(Seller seller) {
        //第一步判断参数是否为空
        if(seller==null){
            //参数为空
            return  ServerResponse.createServerResponseByError(ResponseCode.ERROR, "参数不能为空");
        }
        //参数不为空把商家的用户和密码加入到用户表中
        User user=new User();
        user.setPassword(seller.getPassword());
        user.setPhone(seller.getTelephone());
        user.setQuestion(seller.getQuestion());
        user.setAnswer(seller.getAnswer());
        user.setEmail(seller.getEmail());
        user.setUsername(seller.getSellerId());
        ServerResponse serverResponse = userService.register(user);
        //判断是否加入成功 若成功则继续 不成功则返回
        if(!serverResponse.isSuccess()){
            return serverResponse;
        }

        //去加入到数据库中
        seller.setStatus("0");
        int result = sellerMapper.insert(seller);
        if(result<=0){
            //添加失败
            return  ServerResponse.createServerResponseByError(ResponseCode.ERROR, "申请失败");

        }
        return ServerResponse.createServerResponseBySuccess("申请成功，请您等待审核！！");

    }

    @Override
    public ServerResponse showAllSellers(Integer pageNum,Integer pageSize) {
        //执行之前先分页
        PageHelper.startPage(pageNum, pageSize);
        //去数据库查询所有商家
        List<Seller> sellers = sellerMapper.selectAll();
        //查看总数
        int count = sellerMapper.showCount();
        PageInfo pageInfo = new PageInfo(sellers);
        return ServerResponse.createServerResponseBySuccess(pageInfo,count);
    }

    @Override
    public ServerResponse searchByName(String name, String nickName, Integer pageNum, Integer pageSize) {
        if (name != null && !name.equals("")) {
            name = "%" + name + "%";
        }
        if (nickName != null && !nickName.equals("")) {
            nickName = "%" + nickName + "%";
        }
        //执行之前
        PageHelper.startPage(pageNum, pageSize);
        List<Seller> sellers= sellerMapper.findSellerByName(name, nickName);
        PageInfo pageInfo = new PageInfo(sellers);

        return ServerResponse.createServerResponseBySuccess(pageInfo);
    }
    @Transactional
    @Override
    public ServerResponse updateStatus(String sellerId, String status) {
        //判断参数不能为空
        if(sellerId==null||sellerId.equals(""))
        {
            return ServerResponse.createServerResponseBySuccess(ResponseCode.ERROR,"参数不能为空");

        }
        if(status==null||status.equals(""))
        {
            return ServerResponse.createServerResponseBySuccess(ResponseCode.ERROR,"状态不能为空");

        }
        //去查询商家信息
        Seller seller= sellerMapper.selectByPrimaryKey(sellerId);
        //去修改状态
        seller.setStatus(status);
        int result = sellerMapper.updateByPrimaryKey(seller);
    if(result>=0){
        //修改成功
        //status状态为1 说明审核通过 应该去修改用户的角色为商家
        if(status.equals("1")){
            //把他的角色变为商家
            userService.updateUserStatus(sellerId, RoleEnum.ROLE_SELLER.getRole());
        }
        if(status.equals("3")){
            //关闭了改商家吧他的角色变成普通用户
            userService.updateUserStatus(sellerId, RoleEnum.ROLE_User.getRole());
        }

    }
    return ServerResponse.createServerResponseBySuccess("修改状态成功！");




    }
}
