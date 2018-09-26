http://localhost:8082/swagger-ui/index.html

Infrastructure contains :
      1) Service Registry based on Eureka Server, port 8761
      2) Phone Service, port 8080
      3) Order Srvice, port 8081
      4) Edge Service based on Netflix Zuul, port 8082

For open API documentation please go to http://localhost:8082/swagger-ui/index.html#/

Phone Service and Order Service works absolutely independently and all business logic connected on Edge Service level

Of course, this is demo application and there a lot of things which could be improved like:
	Error handling
  
  More tests (I write only for critical parts of logic)
  
  I have only two profiles for services and default for util services but in the real version it should be much more
	
  Also it will be nice to add  cache to handle API overflow
  
  Also will be nice to add some rabbit MQ integration and implement automatic sync order service data and phones information
  
  Also will be nice to play around with  a circuit breaker and load balance configurations  to handle issues on the microservices side


For run infrastructure

run service registry from project folder ./mvnw spring-boot:run
run phone service from project folder ./mvnw spring-boot:run -Dspring-boot.run.profiles=h2
run order service from project folder ./mvnw spring-boot:run -Dspring-boot.run.profiles=h2
run edge service from project folder ./mvnw spring-boot:run

go to http://localhost:8082/swagger-ui/index.html#/


