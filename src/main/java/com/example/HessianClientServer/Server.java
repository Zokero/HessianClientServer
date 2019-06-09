package com.example.HessianClientServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianServiceExporter;
import org.springframework.remoting.support.RemoteExporter;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Server {

    @Bean(name = "/booking123")
    RemoteExporter bookingService() {
        HessianServiceExporter exporter = new HessianServiceExporter();
        exporter.setService(new GuessTheNumberImpl());
        exporter.setServiceInterface( IGuessTheNumberService.class );
        return exporter;
    }

    public static void main(String[] args) {
        SpringApplication.run(Server.class, args);
    }
}