package br.com.postechfiap.fiappagamentoservice.usecases.cartao;

import br.com.postechfiap.fiappagamentoservice.adapter.PerfilPagamentoResponseAdapter;
import br.com.postechfiap.fiappagamentoservice.client.clienteService.ClienteServiceClient;
import br.com.postechfiap.fiappagamentoservice.client.clienteService.dto.ClienteResponse;
import br.com.postechfiap.fiappagamentoservice.entities.PerfilPagamento;
import br.com.postechfiap.fiappagamentoservice.exception.EntityNotFoundException;
import br.com.postechfiap.fiappagamentoservice.interfaces.repository.PerfilPagamentoRepository;
import br.com.postechfiap.fiappagamentoservice.interfaces.usecases.ListarCartoesClienteUseCase;
import br.com.postechfiap.fiappagamentoservice.usecase.cartao.ListarCartoesClienteUseCaseImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ListarCartoesClienteUseCaseImplTest {

    @Mock
    private PerfilPagamentoRepository perfilPagamentoRepository;
    @Mock
    private ClienteServiceClient clienteServiceClient;
    @Mock
    private PerfilPagamentoResponseAdapter perfilPagamentoResponseAdapter;

    private ListarCartoesClienteUseCase listarCartoesClienteUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        listarCartoesClienteUseCase = new ListarCartoesClienteUseCaseImpl(perfilPagamentoRepository,
                clienteServiceClient, perfilPagamentoResponseAdapter);
    }

    @Test
    public void listarCartoesClienteDeveRetornarSucesso() {
        //given
        final var idCliente = 1L;
        final var perfilPagamentos = List.of(PerfilPagamento.builder().build());
        final var clienteResponse = new ClienteResponse();
        when(clienteServiceClient.getCliente(idCliente))
                .thenReturn(clienteResponse);
        when(perfilPagamentoRepository.findPerfilPagamentoByClienteId(idCliente))
                .thenReturn(perfilPagamentos);

        //when
        final var response = listarCartoesClienteUseCase.execute(idCliente);

        //then
        assertNotNull(response);
    }

    @Test
    public void listarCartoesClienteDeveRetornarErro() {
        //given
        final var idCliente = 1L;
        when(clienteServiceClient.getCliente(idCliente))
                .thenReturn(null);


        //when
        //then
        assertThatThrownBy(() -> listarCartoesClienteUseCase.execute(idCliente))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("cliente não encontrado1.");

        verifyNoInteractions(perfilPagamentoRepository, perfilPagamentoResponseAdapter);

    }

}
