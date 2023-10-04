# LocalDateTime转换

在 ArticleHomeDTO 类中，使用了 LocalDateTime，在调用文章首页展示接口时报错。

> POST /api/v1/article/load

报错信息。

```java
JSON parse error: raw timestamp (0) not allowed for `java.time.LocalDateTime`: need additional information such as an offset or time-zone (see class Javadocs); nested exception is com.fasterxml.jackson.databind.exc.MismatchedInputException: raw timestamp (0) not allowed for `java.time.LocalDateTime`: need additional information such as an offset or time-zone (see class Javadocs)
 at [Source: (PushbackInputStream); line: 1, column: 65] (through reference chain: com.orange.model.article.dto.ArticleHomeDTO["maxBehotTime"])
```

检查前端发送的数据，发现字段 minBehotTime 传入的是时间戳，而我是以 LocalDateTime 去接收该参数，因此报错。

![image-20231003162344716](https://cdn.jsdelivr.net/gh/cnowse/imgbed/img/202310031623517.png)

需要在自定义 ObjectMapper 配置中，添加时间戳的转换，在反序列化时，将时间戳正确的转换为 LocalDateTime。还有一种方式，在 DTO 中，直接使用 Date 类型，则无需多余配置（不好）。

```java
@Bean
ObjectMapper configObjectMapper() {
    ObjectMapper om = new ObjectMapper();
    om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.DEFAULT);
    om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    om.deactivateDefaultTyping();
    om.setTimeZone(TimeZone.getTimeZone("GMT+8"));
    JavaTimeModule javaTimeModule = new JavaTimeModule();
    javaTimeModule.addSerializer(LocalDateTime.class,
                                 new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)));
    javaTimeModule.addSerializer(LocalDate.class,
                                 new LocalDateSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)));
    javaTimeModule.addSerializer(LocalTime.class,
                                 new LocalTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT)));
    javaTimeModule.addDeserializer(LocalDateTime.class,
                                   new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)));
    javaTimeModule.addDeserializer(LocalDate.class,
                                   new LocalDateDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)));
    javaTimeModule.addDeserializer(LocalTime.class,
                                   new LocalTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT)));
    // 重点是这一块配置~~
    javaTimeModule.addDeserializer(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {

        @Override
        public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
            long timestamp = jsonParser.getValueAsLong();
            Instant instant = Instant.ofEpochMilli(timestamp);
            return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        }

    });
    om.registerModule(javaTimeModule);

    SimpleModule numberModule = new SimpleModule();
    numberModule.addSerializer(BigDecimal.class, new BigDecimalJsonSerializer());
    numberModule.addSerializer(Double.class, new DoubleJsonSerializer());

    om.registerModule(numberModule);

    om.setDefaultPropertyInclusion(
        JsonInclude.Value.construct(JsonInclude.Include.NON_NULL, JsonInclude.Include.NON_NULL));
    return om;
}
```



# CentOS日期问题

使用 VMWare 创建的 CentOS 7 虚拟机，将中间件都以 Docker 容器部署，在操作 MinIO 以及其他一些中间件时，总是提示虚拟时间有问题，例如使用 MinIO 上传文件时报错。

```
The difference between the request time and the server's time is too large.
```

在虚拟机中安装 NTP（Network Time Protocol）服务来自动同步时间。

```shell
# 查看当前系统时间
date
# 安装 NTP 并启动，设置开启自启
yum -y install ntp
systemctl start ntpd
systemctl enable ntpd
```

启动 NTP 后，过一段时间便会自动同步时间，如果未同步，则可手动同步。

```shell
ntpdate pool.ntp.org
```

还可以手动设置时间。

```shell
date -s "2023-09-30 12:00:00"
```

