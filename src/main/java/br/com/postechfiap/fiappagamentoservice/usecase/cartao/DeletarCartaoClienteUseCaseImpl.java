package br.com.postechfiap.fiappagamentoservice.usecase.cartao;

import br.com.postechfiap.fiappagamentoservice.exception.EntityNotFoundException;
import br.com.postechfiap.fiappagamentoservice.interfaces.repository.PerfilPagamentoRepository;
import br.com.postechfiap.fiappagamentoservice.interfaces.usecases.DeletarCartaoClienteUseCase;
import br.com.postechfiap.fiappagamentoservice.interfaces.usecases.DeletarMercadoPagoCardUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeletarCartaoClienteUseCaseImpl implements DeletarCartaoClienteUseCase {

    private final DeletarMercadoPagoCardUseCase deletarMercadoPagoCardUseCase;
    private final PerfilPagamentoRepository perfilPagamentoRepository;

    @Override
    public Void execute(Long perfilPagamentoId) {
        final var perfilPagamento = perfilPagamentoRepository.findById(perfilPagamentoId)
                .orElseThrow(() -> new EntityNotFoundException("perfil_pagamento", perfilPagamentoId.toString()));
        deletarMercadoPagoCardUseCase.execute(perfilPagamentoId);
        perfilPagamento.delete();
        perfilPagamentoRepository.save(perfilPagamento);
        return null;
    }
}
