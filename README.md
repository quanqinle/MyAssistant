[简体中文](./README.cn.md) | English

My personal assistant.

The purpose of the project is to integrate all the back-end services I need in daily work and life. Furthermore, in this project, I will practice the skills I learned and accumulate some useful code snippets.

> Note!  
> A completely private project, forking is not recommended.

# Technology overview

* Spring Boot
* Maven
* MySQL
* JpaRepository
* Dozer
* SLF4J
* Swagger
* Batch processing
* Redis
* Scheduled Task
* Freemarker
* Selenium
* Lombok
* AdminLTE

# Technology details

## 1. Batch processing
+ Under the package `com.quanqinle.myassistant.biz.batchjob`, there is an implementation reading data from CVS and writing to DB
+ In config file, set `spring.batch.job.enabled` to open or shutdown the batch job

## 2. Redis
### Publish/Subscribe
`RedisChannelListenerConf.java`: achieve Channel Subscribe, set Listener

## 3. Scheduled Task
+ In the config file, set `@EnableScheduling` to open or shutdown Scheduled Task
+ `ScheduledTaskService.java`: achieve Scheduled Task, use cron to set the timing period

## 4. Swagger

Change from `Swagger UI` method to `springfox-swagger` method.  
`Swagger UI` needs to maintain sample.json, I don't like this! To use `springfox-swagger`, just to add annotations in codes. That sounds great!

+ visit webui: `http://localhost:8080/swagger-ui.html`
+ access json: `http://localhost:8080/v2/api-docs`

### API
Repository by default exposes REST APIs which we can visit through url/tablename+s/ , such as, http://localhost:8080/chineseRegions

## 5. Front-end
I'm not a big fun of front-end development, so I would prefer to choose the tech which can render HTML page by a server. (At least till 2021.07.22) 

### freemarker (.ftl)

### AdminLTE 3.1
Try out this front-end UI framework [AdminLTE (on GitHub)](https://github.com/ColorlibHQ/AdminLTE)

* [live preview](https://adminlte.io/themes/v3)
* [docs](https://adminlte.io/docs/3.1/)

I use [DataTables](https://datatables.net/) to create table, `DataTables` has two usages as below:
1. Client-side processing - where filtering, paging and sorting calculations are all performed in the web-browser.
    + serverSide=false (By default, more detail to see the [Manual](https://datatables.net/manual/server-side))
    + queryAll() in EnglishWordController.java
    + /pages/english/list.html

2. Server-side processing - where filtering, paging and sorting calculations are all performed by a server. It is useful when working with large data sets (typically > 50000 records)
    + serverSide=true
    + getList2() in EnglishWordController.java
    + /pages/english/list2.html


# Business overview

## 1. Get the best tax plan by arranging monthly salary and annual salary

## 2. Track the listing price of second-hand housing in Hangzhou

## 3. Video porter

## 4. History study for my kid

## 5. English study for my kid