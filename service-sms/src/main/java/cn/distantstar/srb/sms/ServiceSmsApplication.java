package cn.distantstar.srb.sms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author: distantstar
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "cn.distantstar.srb.sms")
@ComponentScan(basePackages = {"cn.distantstar.srb", "cn.distantstar.common"})
public class ServiceSmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceSmsApplication.class, args);
    }
}
