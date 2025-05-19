package br.com.postechfiap.fiappagamentoservice.usecase.cartao;

import br.com.postechfiap.fiappagamentoservice.adapter.PerfilPagamentoResponseAdapter;
import br.com.postechfiap.fiappagamentoservice.client.clienteService.ClienteServiceClient;
import br.com.postechfiap.fiappagamentoservice.controller.dto.response.PerfilPagamentoResponse;
import br.com.postechfiap.fiappagamentoservice.exception.EntityNotFoundException;
import br.com.postechfiap.fiappagamentoservice.interfaces.repository.PerfilPagamentoRepository;
import br.com.postechfiap.fiappagamentoservice.interfaces.usecases.ListarCartoesClienteUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListarCartoesClienteUseCaseImpl implements ListarCartoesClienteUseCase {

    private final PerfilPagamentoRepository perfilPagamentoRepository;
    private final ClienteServiceClient clienteServiceClient;
    private final PerfilPagamentoResponseAdapter perfilPagamentoResponseAdapter;

    @Override
    public List<PerfilPagamentoResponse> execute(Long clienteId) {
        final var clienteResponse = clienteServiceClient.getCliente(clienteId);
        if (clienteResponse == null) {
            throw new EntityNotFoundException("cliente", clienteId.toString());
        }
        final var perfisPagamento = perfilPagamentoRepository.findPerfilPagamentoByClienteId(clienteId);
        return perfisPagamento.stream()
                .map(perfilPagamentoResponseAdapter::adapt)
                .toList();
    }
}
