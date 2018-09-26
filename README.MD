# Phone Market App

Phone market app contains next services:
  - Service Registry based on Eureka Server, port 8761
  - Edge Service based on Netflix Zuul, port 8082
  - Phone Service, port 8080
  - Order Srvice, port 8081

> For open API documentation please run infrastructure and go to http://localhost:8082/swagger-ui/index.html#/

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

# Nice to have!

> Of course, this is demo application and there a lot of things which could be improved.
  - Proper Error handling with custome messages
  - Better test coverage (I write only for critical parts of logic)
  -  Add cache to handle API overflow
  - Add some rabbit MQ integration and implement automatic sync order service data from phones service
  - Play around with a circuit breaker and load balance configurations to handle issues on the microservices side
