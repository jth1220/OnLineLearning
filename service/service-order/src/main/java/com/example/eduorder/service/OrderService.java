package com.example.eduorder.service;

import com.example.commonutils.R;
import com.example.commonutils.ordervo.UcenterMemberOrder;
import com.example.eduorder.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author Jiangth
 * @since 2021-11-28
 */
public interface OrderService extends IService<Order> {

    //1 生成订单的方法
    String createOrders(String courseId, String memberIdByJwtToken);
}
