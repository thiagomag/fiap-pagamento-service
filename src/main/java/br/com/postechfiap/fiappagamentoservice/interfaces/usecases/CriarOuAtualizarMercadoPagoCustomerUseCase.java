package br.com.postechfiap.fiappagamentoservice.interfaces.usecases;

import br.com.postechfiap.fiappagamentoservice.client.clienteService.dto.ClienteResponse;
import br.com.postechfiap.fiappagamentoservice.entities.MercadoPagoCustomer;
import br.com.postechfiap.fiappagamentoservice.interfaces.UseCase;

public interface CriarOuAtualizarMercadoPagoCustomerUseCase extends UseCase<ClienteResponse, MercadoPagoCustomer> {
}
