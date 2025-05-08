package br.com.postechfiap.fiappagamentoservice.client.clienteService;

import br.com.postechfiap.fiappagamentoservice.client.clienteService.dto.ClienteResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ClienteServiceClient", url = "${client.cliente-service.url}")
public interface ClienteServiceClient {

    @GetMapping("/clientes/buscar/{id}")
    ClienteResponse getCliente(@PathVariable("id") Long id);

}
