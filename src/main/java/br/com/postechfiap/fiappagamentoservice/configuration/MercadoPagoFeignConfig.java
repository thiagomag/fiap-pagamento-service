package br.com.postechfiap.fiappagamentoservice.configuration;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
public class MercadoPagoFeignConfig {

    @Value("${client.mercadopago.token}")
    private String mercadoPagoToken;

    @Bean
    public RequestInterceptor mercadoPagoRequestInterceptor() {
        return requestTemplate -> requestTemplate
                .header("Authorization", "Bearer " + mercadoPagoToken)
                .header("X-Idempotency-Key", UUID.randomUUID().toString())
                .header("Content-Type", "application/json");
    }
}
