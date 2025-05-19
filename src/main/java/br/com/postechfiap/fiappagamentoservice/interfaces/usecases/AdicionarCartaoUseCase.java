package br.com.postechfiap.fiappagamentoservice.interfaces.usecases;

import br.com.postechfiap.fiappagamentoservice.controller.dto.request.PerfilPagamentoRequest;
import br.com.postechfiap.fiappagamentoservice.controller.dto.response.PerfilPagamentoResponse;
import br.com.postechfiap.fiappagamentoservice.interfaces.UseCase;

public interface AdicionarCartaoUseCase extends UseCase<PerfilPagamentoRequest, PerfilPagamentoResponse> {
}
