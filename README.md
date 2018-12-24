# MyWorld
我的世界！项目的目的是整合我需要的所有后端服务，练手习得的技术，积累代码块。

> To 路过的看官：  
> 纯私人项目，没有可借鉴的地方，请勿fork！

# 一、项目技术细节

## 1. 批处理 Batch
+ `demo.batchjob`包下面，实现从cvs读数据写入db
+ 配置文件中，`spring.batch.job.enabled`取消了应用启动时执行批处理

## 2. Redis
### Publish/Subscribe
`RedisChannelListenerConf.java`：实现频道订阅，设置监听

## 3. 定时任务
+ 配置类开启定时任务的支持`@EnableScheduling`
+ `ScheduledTaskService.java` ：实现定时任务，采用cron设置定时周期

## 4. Swagger
从`Swagger UI`方式（需维护sample.json）改为`springfox-swagger`方式(代码中加注解)。

+ 访问webui：`http://localhost:8080/swagger-ui.html`
+ 访问json：`http://localhost:8080/v2/api-docs`

## 5. 前端
### freemarker (.ftl)

# 二、应用具备的业务功能
## 1. 个税计算、年薪统筹

## 2. 房产
