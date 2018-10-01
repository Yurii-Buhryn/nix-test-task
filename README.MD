# Phone Market App

Phone market app contains next services:
  - Service Registry based on Eureka Server, port 8761
  - Edge Service based on Netflix Zuul, port 8082
  - Phone Service, port 8080
  - Order Srvice, port 8081

> For open API documentation please run infrastructure and go to http://localhost:8082/swagger-ui/index.html#/

## Important points (PLEASE READ BEFORE RUN/REVIEW)

> Of course, this is a demo application so not all things are done in production/best way but I tried to add comments to some parts of code which should be fixed in case of production version :)

Phone Service and Order Service works absolutely independent and from my idea, they should be fully closed from public access. So in this way there no validation on order service side about the validity of phones. All validations happened on edge service side(phone-market-gateway-service). From my idea, in that case, we keep maximum decoupling of services. Also, this way of using edge service will allow us, for example, change phone catalogue providers based on some user inputs but order service will be the same. In any expediency of this idea, we could discuss.

Automation test, this is very important for the production application but in case of demo app I skipped some of them for save time(Don't Do This in Production!!!) so some app have only services level tests some have controller tests and etc but I tried to cover critical points of solution from a business view

Docker, this solution has simple integration with Docker. I predicted two profile (h2 and Postgres) for quick run and test I propose to use h2 profile, in the case of Postgres it will require additional setup :) each app have some predefined data so it is possible to run and start using on h2 without pre-creation of orders or phones


### Run infrastructure

```sh
$ cd eureka-service-registry
$ nohup ./mvnw spring-boot:run &
$ cd ..
$ cd order-catalog
$ nohup ./mvnw spring-boot:run -Dspring-boot.run.profiles=h2 &
$ cd ..
$ cd phone-catalog
$ nohup ./mvnw spring-boot:run -Dspring-boot.run.profiles=h2 &
$ cd phone-market-gateway-service
$ nohup ./mvnw spring-boot:run &
```

# Nice to have/improve/fix!

  - Proper Error handling with custome messages
  - Better test coverage (I write only for critical parts of logic)
  -  Add cache to handle API overflow
  - Add some Rabbit MQ integration and implement automatic sync order service data from phones service
  - Play around with a circuit breaker and load balance configurations to handle issues on the microservices side
