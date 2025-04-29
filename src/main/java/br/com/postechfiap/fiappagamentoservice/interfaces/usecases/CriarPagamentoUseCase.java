package br.com.postechfiap.fiappagamentoservice.interfaces.usecases;

import br.com.postechfiap.fiappagamentoservice.controller.dto.request.PagamentoRequest;
import br.com.postechfiap.fiappagamentoservice.controller.dto.response.PagamentoResponse;
import br.com.postechfiap.fiappagamentoservice.interfaces.UseCase;

public interface CriarPagamentoUseCase extends UseCase<PagamentoRequest, PagamentoResponse> {
}
