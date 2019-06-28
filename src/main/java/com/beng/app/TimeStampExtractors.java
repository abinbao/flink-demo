package com.beng.app;

import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks;
import org.apache.flink.streaming.api.watermark.Watermark;

import com.beng.order.Order;

public class TimeStampExtractors implements AssignerWithPeriodicWatermarks<Order> {

    @Override
    public Watermark getCurrentWatermark() {
        return new Watermark(System.currentTimeMillis());
    }

    @Override
    public long extractTimestamp(Order element, long previousElementTimestamp) {
        return element.getInsertTime().getTime();
    }

}
