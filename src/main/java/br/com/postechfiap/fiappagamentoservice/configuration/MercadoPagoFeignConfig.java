package br.com.postechfiap.fiappagamentoservice.configuration;

import br.com.postechfiap.fiappagamentoservice.client.mercadopago.MercadoPagoClient;
import br.com.postechfiap.fiappagamentoservice.client.mercadopago.MercadoPagoFeignClient;
import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "mock.mercado-pago.enabled", havingValue = "false", matchIfMissing = true)
public class MercadoPagoFeignConfig {

    @Bean
    public MercadoPagoClient mercadoPagoClient(MercadoPagoFeignClient feignClient) {
        return feignClient;
    }

    @Bean
    public RequestInterceptor mercadoPagoRequestInterceptor(
            @Value("${client.mercadopago.token}") String token
    ) {
        return requestTemplate -> requestTemplate
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json");
    }
}
