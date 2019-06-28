package com.beng.app;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.flink.streaming.api.functions.source.RichSourceFunction;

import com.beng.order.Order;

public class OrderSource extends RichSourceFunction<Order> {

    @Override
    public void run(SourceContext<Order> ctx) throws Exception {
        boolean flag = true;
        int index = 0;
        while (flag) {
            while (index < 100) {
                Order order = new Order();
                if (index / 5 == 0) {
                    order.setOrderName("文具");
                } else {
                    order.setOrderName("运动");
                }
                order.setPrice(10.0);
                String date = "2019-01-30 10:01:56";
                if (index == 10) {
                    Thread.sleep(3L);
                    date = "2019-01-30 10:02:01";
                }
                order.setInsertTime(getDate(date));
                // } else {
                // order.setOrderName("运动");
                // order.setPrice(10.0);
                // }
                ++index;
                ctx.collect(order);
            }
        }
    }

    private static Date getDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date nowtime = new Date();
        try {
            nowtime = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return nowtime;
    }

    @Override
    public void cancel() {

    }

}
