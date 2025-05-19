package br.com.postechfiap.fiappagamentoservice.interfaces.usecases;

import br.com.postechfiap.fiappagamentoservice.controller.dto.response.PerfilPagamentoResponse;
import br.com.postechfiap.fiappagamentoservice.interfaces.UseCase;

import java.util.List;

public interface ListarCartoesClienteUseCase extends UseCase<Long, List<PerfilPagamentoResponse>> {
}
