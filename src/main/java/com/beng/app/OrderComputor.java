package com.beng.app;

import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;

import com.beng.order.Order;

/**
 * 订单实时统计
 * 
 * @author apple
 */
public class OrderComputor {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // 设置时间窗口类型
        // 1) ProcessingTime 程序处理时间
        // 2) IngestionTime 数据源进入 flink 的时间
        // 3) EventTime 数据自带时间戳
        // env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        DataStream<Order> orderStream = env.addSource(new OrderSource());

        KeyedStream<Order, String> keyedEdits = orderStream.keyBy(new KeySelector<Order, String>() {
            @Override
            public String getKey(Order order) {
                return order.getOrderName();
            }
        });

        DataStream<Tuple2<String, Double>> result = keyedEdits.keyBy("").
        // 统计窗口 5 秒
                timeWindowAll(Time.seconds(10))
                // 开始聚合
                .aggregate(new AggregateFunction<Order, Tuple2<String, Double>, Tuple2<String, Double>>() {
                    @Override
                    public Tuple2<String, Double> createAccumulator() {
                        return new Tuple2<>("", 0.0);
                    }

                    @Override
                    public Tuple2<String, Double> add(Order value, Tuple2<String, Double> accumulator) {
                        return new Tuple2<>(value.getOrderName(), value.getPrice() + accumulator.f1);
                    }

                    @Override
                    public Tuple2<String, Double> getResult(Tuple2<String, Double> accumulator) {
                        return accumulator;
                    }

                    @Override
                    public Tuple2<String, Double> merge(Tuple2<String, Double> a, Tuple2<String, Double> b) {
                        return null;
                    }
                });
        result.print();
        env.execute();
    }

}
