package com.beng.app;

import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer011;
import org.apache.flink.streaming.connectors.wikiedits.WikipediaEditEvent;
import org.apache.flink.streaming.connectors.wikiedits.WikipediaEditsSource;

/**
 * @author apple
 */
public class WikipediaAnalysis {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment see = StreamExecutionEnvironment.getExecutionEnvironment();

        // source 输入源 创建了一个数据流
        DataStream<WikipediaEditEvent> edits = see.addSource(new WikipediaEditsSource());

        KeyedStream<WikipediaEditEvent, String> keyedEdits = edits.keyBy(new KeySelector<WikipediaEditEvent, String>() {
            @Override
            public String getKey(WikipediaEditEvent event) {
                return event.getUser();
            }
        });

        DataStream<Tuple2<String, Integer>> result = keyedEdits
                // 4.设置窗口时间为5s
                .timeWindow(Time.seconds(5))
                // 5.聚合当前窗口中相同用户名的事件，最终返回一个tuple2<user，累加的ByteDiff>
                .aggregate(
                        new AggregateFunction<WikipediaEditEvent, Tuple2<String, Integer>, Tuple2<String, Integer>>() {
                            @Override
                            public Tuple2<String, Integer> createAccumulator() {
                                return new Tuple2<>("", 0);
                            }

                            @Override
                            public Tuple2<String, Integer> add(WikipediaEditEvent value,
                                    Tuple2<String, Integer> accumulator) {
                                return new Tuple2<>(value.getUser(), value.getByteDiff() + accumulator.f1);
                            }

                            @Override
                            public Tuple2<String, Integer> getResult(Tuple2<String, Integer> accumulator) {
                                return accumulator;
                            }

                            @Override
                            public Tuple2<String, Integer> merge(Tuple2<String, Integer> a, Tuple2<String, Integer> b) {
                                return new Tuple2<>(a.f0 + b.f0, a.f1 + b.f1);
                            }
                        });

        // result.print();
        result.map(new MapFunction<Tuple2<String, Integer>, String>() {
            @Override
            public String map(Tuple2<String, Integer> tuple) {
                return tuple.toString();
            }
        }).addSink(new FlinkKafkaProducer011<>("localhost:9092", "wiki-result", new SimpleStringSchema()));

        see.execute();
    }
}
