package first.app.microservice;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"first.app.microservice.controller","first.app.microservice.service","first.app.microservice.mapstruct" , "first.app.microservice.repository"})
public class MicroserviceApplication  {

    public static void main(String[] args)  {
        SpringApplication.run(MicroserviceApplication.class, args);
    }

}
