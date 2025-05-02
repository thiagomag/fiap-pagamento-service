package br.com.postechfiap.fiappagamentoservice.interfaces.usecases;

import br.com.postechfiap.fiappagamentoservice.controller.dto.response.PagamentoResponse;
import br.com.postechfiap.fiappagamentoservice.interfaces.UseCase;
import br.com.postechfiap.fiappagamentoservice.usecase.mercadoPago.dto.PagamentoContext;

public interface AtualizarPagamentoUseCase extends UseCase<PagamentoContext, PagamentoResponse> {
}
