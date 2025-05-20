package br.com.postechfiap.fiappagamentoservice.usecases.cartao;

import br.com.postechfiap.fiappagamentoservice.entities.PerfilPagamento;
import br.com.postechfiap.fiappagamentoservice.exception.EntityNotFoundException;
import br.com.postechfiap.fiappagamentoservice.interfaces.repository.PerfilPagamentoRepository;
import br.com.postechfiap.fiappagamentoservice.interfaces.usecases.DeletarCartaoClienteUseCase;
import br.com.postechfiap.fiappagamentoservice.interfaces.usecases.DeletarMercadoPagoCardUseCase;
import br.com.postechfiap.fiappagamentoservice.usecase.cartao.DeletarCartaoClienteUseCaseImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeletarCartaoClienteUseCaseImplTest {

    @Mock
    private DeletarMercadoPagoCardUseCase deletarMercadoPagoCardUseCase;
    @Mock
    private PerfilPagamentoRepository perfilPagamentoRepository;

    private DeletarCartaoClienteUseCase deletarCartaoClienteUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        deletarCartaoClienteUseCase = new DeletarCartaoClienteUseCaseImpl(deletarMercadoPagoCardUseCase, perfilPagamentoRepository);
    }

    @Test
    public void deletarCartaoDeveRetornarSucesso() {
        //given
        final var perfilPagamento = new PerfilPagamento();

        when(perfilPagamentoRepository.findById(anyLong()))
                .thenReturn(Optional.of(perfilPagamento));

        when(perfilPagamentoRepository.save(any(PerfilPagamento.class)))
                .thenAnswer(invocation -> {
                    final var ppSaved = (PerfilPagamento) invocation.getArgument(0);
                    assertNotNull(ppSaved.getDeletedTmsp());
                    return ppSaved;
                });

        //when
        deletarCartaoClienteUseCase.execute(1L);

        //then
        verify(deletarMercadoPagoCardUseCase).execute(anyLong());
        verify(perfilPagamentoRepository, times(1)).findById(anyLong());
        verify(perfilPagamentoRepository, times(1)).save(any(PerfilPagamento.class));
    }

    @Test
    public void deletarCartaoDeveRetornarFalha() {
        //given
        when(perfilPagamentoRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        //when
        //then
        assertThatThrownBy(() -> deletarCartaoClienteUseCase.execute(1L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("perfil_pagamento não encontrado1.");
        verify(perfilPagamentoRepository, times(1)).findById(anyLong());
        verify(perfilPagamentoRepository, never()).save(any(PerfilPagamento.class));
    }
}
