package br.com.postechfiap.fiappagamentoservice.usecases.cartao;

import br.com.postechfiap.fiappagamentoservice.adapter.PerfilPagamentoResponseAdapter;
import br.com.postechfiap.fiappagamentoservice.client.clienteService.ClienteServiceClient;
import br.com.postechfiap.fiappagamentoservice.client.clienteService.dto.ClienteResponse;
import br.com.postechfiap.fiappagamentoservice.controller.dto.request.PerfilPagamentoRequest;
import br.com.postechfiap.fiappagamentoservice.controller.dto.response.PerfilPagamentoResponse;
import br.com.postechfiap.fiappagamentoservice.entities.MercadoPagoCustomer;
import br.com.postechfiap.fiappagamentoservice.exception.EntityNotFoundException;
import br.com.postechfiap.fiappagamentoservice.interfaces.repository.MercadoPagoCustomerRepository;
import br.com.postechfiap.fiappagamentoservice.interfaces.usecases.AdicionarCartaoUseCase;
import br.com.postechfiap.fiappagamentoservice.usecase.cartao.AdicionarCartaoUseCaseImpl;
import br.com.postechfiap.fiappagamentoservice.usecase.mercadoPago.dto.PagamentoContext;
import br.com.postechfiap.fiappagamentoservice.usecase.pagamento.CriarPerfilPagamentoUseCaseImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdicionarCartaoUseCaseImplTest {

    @Mock
    private CriarPerfilPagamentoUseCaseImpl criarMercadoPagoCardUseCase;
    @Mock
    private MercadoPagoCustomerRepository mercadoPagoCustomerRepository;
    @Mock
    private ClienteServiceClient clienteServiceClient;
    @Mock
    private PerfilPagamentoResponseAdapter perfilPagamentoResponseAdapter;

    private AdicionarCartaoUseCase adicionarCartaoUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        adicionarCartaoUseCase = new AdicionarCartaoUseCaseImpl(criarMercadoPagoCardUseCase, mercadoPagoCustomerRepository,
                clienteServiceClient, perfilPagamentoResponseAdapter);
    }

    @Test
    public void adicionarCartaoDeveRetornarSucesso() {
        //given
        final var perfilPagamentoRequest = mock(PerfilPagamentoRequest.class);
        final var clienteResponse = mock(ClienteResponse.class);
        final var mercadoPagoCustomer = mock(MercadoPagoCustomer.class);
        final var pagamentoContext = mock(PagamentoContext.class);
        final var perfilPagamentoResponse = mock(PerfilPagamentoResponse.class);
        when(clienteServiceClient.getCliente(anyLong()))
                .thenReturn(clienteResponse);
        when(mercadoPagoCustomerRepository.findByClienteId(anyLong()))
                .thenReturn(Optional.of(mercadoPagoCustomer));
        when(criarMercadoPagoCardUseCase.execute(any(PagamentoContext.class)))
                .thenReturn(pagamentoContext);
        when(perfilPagamentoResponseAdapter.adapt(any()))
                .thenReturn(perfilPagamentoResponse);

        //when
        final var actual = adicionarCartaoUseCase.execute(perfilPagamentoRequest);

        //then
        assertNotNull(actual);
    }

    @Test
    public void adicionarCartaoDeveRetornarFalha() {
        //given
        final var perfilPagamentoRequest = mock(PerfilPagamentoRequest.class);
        final var clienteResponse = mock(ClienteResponse.class);

        when(clienteServiceClient.getCliente(anyLong()))
                .thenReturn(clienteResponse);
        when(mercadoPagoCustomerRepository.findByClienteId(anyLong()))
                .thenReturn(Optional.empty());
        //when
        //then
        assertThatThrownBy(() -> adicionarCartaoUseCase.execute(perfilPagamentoRequest))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("mercado_pago_customer não encontrado0.");

        verifyNoInteractions(criarMercadoPagoCardUseCase, criarMercadoPagoCardUseCase);
    }


}
