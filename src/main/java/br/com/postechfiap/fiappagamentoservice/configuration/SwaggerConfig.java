package br.com.postechfiap.fiappagamentoservice.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI().info(new Info()
                .title("API gerenciamento de Pagamentos - FIAP Tech_Challenge")
                .version("v1")
                .description("Documentação da API de Pagamentos do sistema de gerencimento de pedidos.")
        );
    }
}
