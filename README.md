[简体中文](./README.cn.md) | English

My personal secretary.

The purpose of the project is to integrate all the back-end services I need in daily work and life. Furthermore, in this project, I will practice the skills I learned and accumulate some useful code blocks.

> Note!  
> A purely private project, fork is not recommended.

# Technology overview

* Spring Boot
* Maven
* MySQL
* slf4j
* Swagger
* Batch processing
* Redis
* Scheduled Task
* Freemarker
* JpaRepository
* Selenium
* Lombok

# Technology details

## 1. Batch processing
+ Under the package `com.quanqinle.mysecretary.biz.batchjob`, there is an implementation reading data from CVS and writing to DB
+ In config file, set `spring.batch.job.enabled` to open or shutdown the batch job

## 2. Redis
### Publish/Subscribe
`RedisChannelListenerConf.java`: achive Channel Subscribe, set Listener

## 3. Scheduled Task
+ In the config file, set `@EnableScheduling` to open or shutdown Scheduled Task
+ `ScheduledTaskService.java`: achive Scheduled Task, use cron to set the timing period

## 4. Swagger

Change from `Swagger UI` method to `springfox-swagger` method.  
`Swagger UI` needs to maintain sample.json, I don't like this! To use `springfox-swagger`, just to add annotations in codes. That sounds great!

+ visit webui: `http://localhost:8080/swagger-ui.html`
+ access json: `http://localhost:8080/v2/api-docs`

## 5. Front-end

### freemarker (.ftl)


# Business overview

## 1. Get the best tax plan by arranging monthly salary and annual salary

## 2. Second-hand housing listing price tracking

## 3. Video porter
