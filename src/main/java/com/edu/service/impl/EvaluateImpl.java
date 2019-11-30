package com.edu.service.impl;

import com.edu.common.EvaluateGradeEnum;
import com.edu.common.ResponseCode;
import com.edu.common.ServerResponse;
import com.edu.dao.EvaluateMapper;
import com.edu.pojo.Evaluate;
import com.edu.service.IEvaluateService;
import com.edu.service.IOrderService;
import com.edu.service.IUserService;
import com.edu.untils.DateUtils;
import com.edu.vo.EvaluatesVO;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class EvaluateImpl implements IEvaluateService {
    @Autowired
    EvaluateMapper evaluateMapper;
    @Autowired
    IOrderService orderService;
    @Autowired
    IUserService userService;


    @Override
    public ServerResponse add(Evaluate evaluate) {
        if (evaluate == null) {
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "参数不能为空");
        }
        //去添加
        int result = evaluateMapper.insert(evaluate);
        if(result<=0){
            //添加失败
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "评价失败");
              }else {
            return ServerResponse.createServerResponseBySuccess("评价成功");

        }

    }

    @Override
    public ServerResponse show(Integer productId) {
        //判断参数不能为空
        if(productId==null){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR, "参数不能为空");
        }
        //根据商品的ID去查询评价
        List<Evaluate> evaluates = evaluateMapper.findByProductId(productId);

        List<EvaluatesVO> evaluatesVOS = assem(evaluates);
        //查看该商品有多少评价
        Integer count = evaluateMapper.count(productId);
        return ServerResponse.createServerResponseBySuccess(evaluatesVOS,count);

    }
    private  List<EvaluatesVO> assem(List<Evaluate> evaluates){
        //遍历这个eva
        List<EvaluatesVO> evaluatesVOS= Lists.newArrayList();
        for(Evaluate evaluate: evaluates){
            EvaluatesVO evaluatesVO=new EvaluatesVO();
            evaluatesVO.setCreateTime(DateUtils.dateToStr(evaluate.getCreateTime()));
            evaluatesVO.setDetail(evaluate.getDetail());
            EvaluateGradeEnum evaluateGradeEnum=EvaluateGradeEnum.codeOf(evaluate.getGrade());
            if(evaluateGradeEnum!=null){
                evaluatesVO.setGrade(evaluateGradeEnum.getDesc());
            }
            evaluatesVO.setImg(evaluate.getImg());
            evaluatesVO.setId(evaluate.getId());
            //拿到用户ID去查用户名
            String username = userService.findUserNameByUserId(evaluate.getUserId());
             evaluatesVO.setUserName(username);
             evaluatesVO.setProductComm(evaluate.getOrderNo());
            evaluatesVOS.add(evaluatesVO);

        }
        return evaluatesVOS;

    }

}
