package com.edu.schedule;

import com.edu.service.IOrderService;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 关闭订单定时任务
 */
@Component
public class CloseOrderSchedule {
    @Value("${order.close.tiomeout}")
    private  int orderTimeout;
    @Autowired
    IOrderService orderService;
    @Scheduled(cron = "0 0 0/1 * * ?")
    public void closeOrder(){
        //  时间减法
        Date closeOrderTime = DateUtils.addHours(new Date(), -orderTimeout);
        orderService.closeOrder(com.edu.untils.DateUtils.dateToStr(closeOrderTime));

    }
}
