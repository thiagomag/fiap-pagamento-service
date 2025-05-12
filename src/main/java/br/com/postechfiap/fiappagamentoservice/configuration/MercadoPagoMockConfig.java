package br.com.postechfiap.fiappagamentoservice.configuration;

import br.com.postechfiap.fiappagamentoservice.client.mercadopago.MercadoPagoClient;
import br.com.postechfiap.fiappagamentoservice.client.mercadopago.MercadoPagoClientMock;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "mock.mercado-pago.enabled", havingValue = "true", matchIfMissing = false)
public class MercadoPagoMockConfig {

    @Bean
    public MercadoPagoClient mercadoPagoClientMock() {
        System.out.println("✅ Usando MOCK de MercadoPagoClient");
        return new MercadoPagoClientMock();
    }
}
