package first.app.microservice;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"first.app.microservice.controller","first.app.microservice.service","first.app.microservice.mapstruct"})
public class MicroserviceApplication  {

    public static void main(String[] args)  {
        SpringApplication.run(MicroserviceApplication.class, args);
    }

}
