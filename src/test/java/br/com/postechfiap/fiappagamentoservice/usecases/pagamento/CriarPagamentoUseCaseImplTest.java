package br.com.postechfiap.fiappagamentoservice.usecases.pagamento;

import br.com.postechfiap.fiappagamentoservice.client.clienteService.ClienteServiceClient;
import br.com.postechfiap.fiappagamentoservice.client.clienteService.dto.ClienteResponse;
import br.com.postechfiap.fiappagamentoservice.controller.dto.request.PagamentoRequest;
import br.com.postechfiap.fiappagamentoservice.controller.dto.request.PerfilPagamentoRequest;
import br.com.postechfiap.fiappagamentoservice.controller.dto.response.PagamentoResponse;
import br.com.postechfiap.fiappagamentoservice.entities.MercadoPagoCustomer;
import br.com.postechfiap.fiappagamentoservice.entities.Pagamento;
import br.com.postechfiap.fiappagamentoservice.entities.PerfilPagamento;
import br.com.postechfiap.fiappagamentoservice.enuns.MetodoPagamentoEnum;
import br.com.postechfiap.fiappagamentoservice.enuns.StatusBasicoEnum;
import br.com.postechfiap.fiappagamentoservice.enuns.StatusPagamentoEnum;
import br.com.postechfiap.fiappagamentoservice.exception.PagamentoRejeitadoException;
import br.com.postechfiap.fiappagamentoservice.interfaces.repository.PagamentoRepository;
import br.com.postechfiap.fiappagamentoservice.interfaces.usecases.*;
import br.com.postechfiap.fiappagamentoservice.usecase.mercadoPago.dto.PagamentoContext;
import br.com.postechfiap.fiappagamentoservice.usecase.pagamento.CriarPagamentoUseCaseImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
public class CriarPagamentoUseCaseImplTest {

    @Mock
    private CriarOuAtualizarClienteUseCase criarOuAtualizarClienteUseCase;
    @Mock
    private CriarPerfilPagamentoUseCase criarPerfilPagamentoUseCase;
    @Mock
    private MercadoPagoCreatePaymentUseCase mercadoPagoCreatePaymentUseCase;
    @Mock
    private AtualizarPagamentoUseCase atualizarPagamentoUseCase;
    @Mock
    private ClienteServiceClient clienteServiceClient;
    @Mock
    private PagamentoRepository pagamentoRepository;

    private CriarPagamentoUseCase criarPagamentoUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        criarPagamentoUseCase = new CriarPagamentoUseCaseImpl(criarOuAtualizarClienteUseCase, criarPerfilPagamentoUseCase
                ,mercadoPagoCreatePaymentUseCase, atualizarPagamentoUseCase, clienteServiceClient, pagamentoRepository);
    }

    @Test
    public void criarPagamentoDeveRetornarSucesso() {
        //given
        final var request = PagamentoRequest.builder()
                .idCliente(1L)
                .idPedido(UUID.randomUUID())
                .skuProduto("PRODTEST")
                .valor(BigDecimal.TEN)
                .perfilPagamento(PerfilPagamentoRequest.builder().build())
                .build();
        final var clienteResponse = ClienteResponse.builder()
                .id(1L)
                .nome("Nome Teste")
                .cpf("12345678901")
                .enderecos(null)
                .build();
        final var mercadoPagoCustomer = new MercadoPagoCustomer();
        final var pagamentoContext = PagamentoContext.builder()
                .clienteResponse(clienteResponse)
                .mercadoPagoCustomer(mercadoPagoCustomer)
                .perfilPagamentoRequest(request.getPerfilPagamento())
                .pagamentoRequest(request)
                .build();
        final var perfilPagamento = PerfilPagamento.builder()
                .clienteId(1L)
                .dataValidade(LocalDate.of(2030, 12, 31))
                .status(StatusBasicoEnum.ATIVO)
                .primeirosNumerosCartao("1234")
                .ultimosNumerosCartao("5678")
                .nomeTitularCartao("Nome Teste")
                .build();
        final var pagamento = Pagamento.builder()
                .clienteId(request.getIdCliente())
                .valor(request.getValor())
                .status(StatusPagamentoEnum.PROCESSANDO)
                .parcelas(1)
                .pedidoId(request.getIdPedido().toString())
                .metodoPagamento(MetodoPagamentoEnum.CARTAO_CREDITO)
                .perfilPagamento(perfilPagamento)
                .build();
        final var pagamentoResponse = PagamentoResponse.builder()
                .id(pagamento.getId())
                .clienteId(pagamento.getClienteId())
                .valor(pagamento.getValor())
                .pedidoId(pagamento.getPedidoId())
                .status(StatusPagamentoEnum.SUCESSO)
                .build();
        when(clienteServiceClient.getCliente(anyLong()))
                .thenReturn(clienteResponse);
        when(criarOuAtualizarClienteUseCase.execute(clienteResponse))
                .thenReturn(mercadoPagoCustomer);
        pagamentoContext.setPerfilPagamento(perfilPagamento);
        when(criarPerfilPagamentoUseCase.execute(any(PagamentoContext.class)))
                .thenReturn(pagamentoContext);
        when(pagamentoRepository.save(pagamento))
                .thenReturn(pagamento);
        pagamentoContext.setPagamento(pagamento);
        when(mercadoPagoCreatePaymentUseCase.execute(pagamentoContext))
                .thenReturn(pagamentoContext);
        when(atualizarPagamentoUseCase.execute(pagamentoContext))
                .thenReturn(pagamentoResponse);
        //when
        final var actual = criarPagamentoUseCase.execute(request);
        //then
        assertNotNull(actual);
        assertEquals(pagamentoResponse, actual);
        assertEquals(StatusPagamentoEnum.SUCESSO, actual.getStatus());
    }

    @Test
    public void criarPagamentoDeveRetornarErro() {
        //given
        final var request = PagamentoRequest.builder()
                .idCliente(1L)
                .idPedido(UUID.randomUUID())
                .skuProduto("PRODTEST")
                .valor(BigDecimal.TEN)
                .perfilPagamento(PerfilPagamentoRequest.builder().build())
                .build();
        final var clienteResponse = ClienteResponse.builder()
                .id(1L)
                .nome("Nome Teste")
                .cpf("12345678901")
                .enderecos(null)
                .build();
        final var mercadoPagoCustomer = new MercadoPagoCustomer();
        final var pagamentoContext = PagamentoContext.builder()
                .clienteResponse(clienteResponse)
                .mercadoPagoCustomer(mercadoPagoCustomer)
                .perfilPagamentoRequest(request.getPerfilPagamento())
                .pagamentoRequest(request)
                .build();
        final var perfilPagamento = PerfilPagamento.builder()
                .clienteId(1L)
                .dataValidade(LocalDate.of(2030, 12, 31))
                .status(StatusBasicoEnum.ATIVO)
                .primeirosNumerosCartao("1234")
                .ultimosNumerosCartao("5678")
                .nomeTitularCartao("Nome Teste")
                .build();
        final var pagamento = Pagamento.builder()
                .id(1L)
                .clienteId(request.getIdCliente())
                .valor(request.getValor())
                .status(StatusPagamentoEnum.PROCESSANDO)
                .parcelas(1)
                .pedidoId(request.getIdPedido().toString())
                .metodoPagamento(MetodoPagamentoEnum.CARTAO_CREDITO)
                .perfilPagamento(perfilPagamento)
                .build();
        final var pagamentoResponse = PagamentoResponse.builder()
                .id(pagamento.getId())
                .clienteId(pagamento.getClienteId())
                .valor(pagamento.getValor())
                .pedidoId(pagamento.getPedidoId())
                .status(StatusPagamentoEnum.FALHA)
                .build();
        when(clienteServiceClient.getCliente(anyLong()))
                .thenReturn(clienteResponse);
        when(criarOuAtualizarClienteUseCase.execute(clienteResponse))
                .thenReturn(mercadoPagoCustomer);
        pagamentoContext.setPerfilPagamento(perfilPagamento);
        when(criarPerfilPagamentoUseCase.execute(any(PagamentoContext.class)))
                .thenReturn(pagamentoContext);
        when(pagamentoRepository.save(any(Pagamento.class)))
                .thenReturn(pagamento);
        pagamentoContext.setPagamento(pagamento);
        when(mercadoPagoCreatePaymentUseCase.execute(pagamentoContext))
                .thenReturn(pagamentoContext);
        when(atualizarPagamentoUseCase.execute(pagamentoContext))
                .thenReturn(pagamentoResponse);
        //when
        //then
        assertThatThrownBy(() -> criarPagamentoUseCase.execute(request))
                .isInstanceOf(PagamentoRejeitadoException.class)
                .hasMessage("Pagamento com o id 1 rejeitado: FALHA");
    }
}
