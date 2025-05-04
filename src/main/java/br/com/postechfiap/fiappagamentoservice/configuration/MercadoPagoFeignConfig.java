package br.com.postechfiap.fiappagamentoservice.configuration;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MercadoPagoFeignConfig {

    @Value("${client.mercadopago.token}")
    private String mercadoPagoToken;

    @Bean
    public RequestInterceptor mercadoPagoRequestInterceptor() {
        return requestTemplate -> requestTemplate.header("Authorization", "Bearer " + mercadoPagoToken);
    }
}
