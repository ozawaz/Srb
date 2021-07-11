package cn.distantstar.srb.oss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author distantstar
 */
@SpringBootApplication
@ComponentScan(basePackages = {"cn.distantstar.srb", "cn.distantstar.common"})
public class ServiceOssApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceOssApplication.class, args);
    }
}
