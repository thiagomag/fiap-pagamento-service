package br.com.postechfiap.fiappagamentoservice.interfaces.usecases;

import br.com.postechfiap.fiappagamentoservice.controller.dto.request.PagamentoRequest;
import br.com.postechfiap.fiappagamentoservice.entities.MercadoPagoCard;
import br.com.postechfiap.fiappagamentoservice.interfaces.UseCase;

public interface CriarPerfilPagamentoUseCase extends UseCase<PagamentoRequest, MercadoPagoCard> {
}
