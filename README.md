# flink-demo
flink

### Mac 下安装 flink

```
$ brew install apache-flink

$ flink --version

$ brew info apache-flink

/usr/local/Cellar/apache-flink/1.5.0 (116 files, 324MB) *

$ ./libexec/bin/start-cluster.sh

接着就可以进入web页面(http://localhost:8081/)

```


### 运行demo

flink 结合 kafka

要在 flink 上跑 jar 包前，需要启动 kafka

1. 启动kafka

```
bin/kafka-server-start.sh config/server.properties
```

启动kafka 之前要启动 zookeeper

```
./zkServer start
```

kafka 创建 topic

```
./kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic wiki-results
```

Mac 下可以使用 brew info xxx 查看 使用 brew install 安装的包的信息

还有一个注意点：

打成 jar 包，使用 flink 运行，报错 ClassNotDefFoundException

是因为 maven 打包时，没有将依赖的包打进去。

修改 pom.xml

```
<build>
        <plugins>
            <plugin>
                <artifactId>maven-assembly-plugin</artifactId>
                <configuration>
                    <appendAssemblyId>false</appendAssemblyId>
                    <descriptorRefs>
                        <descriptorRef>jar-with-dependencies</descriptorRef>
                    </descriptorRefs>
                    <archive>
                        <manifest>
                            <!-- 此处指定main方法入口的class -->
                            <mainClass>com.beng.app.WikipediaAnalysis</mainClass>
                        </manifest>
                    </archive>
                </configuration>
                <executions>
                    <execution>
                        <id>make-assembly</id>
                        <phase>package</phase>
                        <goals>
                            <goal>assembly</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
```

2. 运行 flink

```
./flink run -c com.beng.app.WikipediaAnalysis /Users/apple/flink/flink-demo-0.0.1-SNAPSHOT.jar
```

查看 kafka 消费：

```
./kafka-console-consumer.sh --zookeeper localhost:2181 --topic wiki-result

```
