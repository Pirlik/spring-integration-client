package com.griddynamics.integration.client.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.*;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.ip.tcp.TcpOutboundGateway;
import org.springframework.integration.ip.tcp.connection.AbstractClientConnectionFactory;
import org.springframework.integration.ip.tcp.connection.TcpNetClientConnectionFactory;
import org.springframework.messaging.MessageHandler;

@EnableIntegration
@IntegrationComponentScan
@Configuration
public class Config {

    @Value("${integration.client.host}")
    private String host;

    @Value("${integration.client.port}")
    private int clientPort;

    @MessagingGateway(defaultRequestChannel = "toTcp", defaultRequestTimeout = "60000")
    public interface NewGateway {

        String sendBytesArrayMessage(byte[] in);

    }

    @Bean
    @ServiceActivator(inputChannel = "toTcp")
    public MessageHandler tcpOutGate(AbstractClientConnectionFactory connectionFactory) {
        TcpOutboundGateway gate = new TcpOutboundGateway();
        gate.setConnectionFactory(connectionFactory);
        gate.setOutputChannelName("resultToString");
        return gate;
    }

    @Transformer(inputChannel = "resultToString")
    public String convertResult(byte[] bytes) {
        return new String(bytes);
    }

    @Bean
    public AbstractClientConnectionFactory clientCF() {
        return new TcpNetClientConnectionFactory(host, clientPort);
    }
}
