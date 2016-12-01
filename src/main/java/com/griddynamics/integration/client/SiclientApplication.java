package com.griddynamics.integration.client;

import com.griddynamics.integration.client.config.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SiclientApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(SiclientApplication.class, args);
        Config.NewGateway g = ctx.getBean(Config.NewGateway.class);
        String stringWithSpace = g.sendBytesArrayMessage(new byte[]{12, 101, 100, 110, 123, 32, 56, 67, 75, 32, 45, 56, 78, 89});
        System.out.println("Response - " + stringWithSpace);
        String stringWithoutSpace = g.sendBytesArrayMessage(new byte[]{12, 101, 100, 110, 123, 56, 67, 75, 45, 56, 78, 89});
        System.out.println("Response - " + stringWithoutSpace);
        ctx.close();
    }
}
