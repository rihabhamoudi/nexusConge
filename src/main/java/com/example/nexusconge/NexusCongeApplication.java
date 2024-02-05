package com.example.nexusconge;

import com.example.nexusconge.config.MailConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
public class NexusCongeApplication {

    public static void main(String[] args) {
        SpringApplication.run(NexusCongeApplication.class, args);
    }

}
