package br.com.postechfiap.fiappagamentoservice.client.clienteService;

import br.com.postechfiap.fiappagamentoservice.client.clienteService.dto.ClienteResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "Cliente Service Client", url = "${cliente.service.url}")
public interface ClienteServiceClient {

    @GetMapping("/clientes/{id}")
    ClienteResponse getCliente(@PathVariable("id") Long id);

}
