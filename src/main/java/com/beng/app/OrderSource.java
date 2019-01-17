package com.beng.app;

import org.apache.flink.streaming.api.functions.source.RichSourceFunction;

import com.beng.order.Order;

public class OrderSource extends RichSourceFunction<Order> {

    @Override
    public void run(SourceContext<Order> ctx) throws Exception {
        for (int i = 0; i < 100; ++i) {
            Order order = new Order();
            if (i % 5 == 0) {
                Thread.sleep(1000);
                order.setOrderName("文具");
                order.setPrice(10.0);
            } else {
                order.setOrderName("运动");
                order.setPrice(10.0);
            }
            ctx.collect(order);
        }
    }

    @Override
    public void cancel() {

    }

}
