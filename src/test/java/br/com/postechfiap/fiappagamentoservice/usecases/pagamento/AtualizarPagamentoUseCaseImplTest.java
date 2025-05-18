package br.com.postechfiap.fiappagamentoservice.usecases.pagamento;

import br.com.postechfiap.fiappagamentoservice.adapter.PagamentoResponseCustomAdapter;
import br.com.postechfiap.fiappagamentoservice.controller.dto.response.PagamentoResponse;
import br.com.postechfiap.fiappagamentoservice.entities.MercadoPagoPayment;
import br.com.postechfiap.fiappagamentoservice.entities.Pagamento;
import br.com.postechfiap.fiappagamentoservice.entities.PerfilPagamento;
import br.com.postechfiap.fiappagamentoservice.enuns.MetodoPagamentoEnum;
import br.com.postechfiap.fiappagamentoservice.enuns.StatusPagamentoEnum;
import br.com.postechfiap.fiappagamentoservice.interfaces.repository.PagamentoRepository;
import br.com.postechfiap.fiappagamentoservice.interfaces.usecases.AtualizarPagamentoUseCase;
import br.com.postechfiap.fiappagamentoservice.usecase.mercadoPago.dto.PagamentoContext;
import br.com.postechfiap.fiappagamentoservice.usecase.pagamento.AtualizarPagamentoUseCaseImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AtualizarPagamentoUseCaseImplTest {

    @Mock
    private PagamentoRepository pagamentoRepository;
    @Mock
    private PagamentoResponseCustomAdapter pagamentoResponseCustomAdapter;

    private AtualizarPagamentoUseCase atualizarPagamentoUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        atualizarPagamentoUseCase = new AtualizarPagamentoUseCaseImpl(pagamentoRepository, pagamentoResponseCustomAdapter);
    }

    @Test
    public void atualizarPagamentoDeveRetornarSucesso1() {
        //given
        final var pagamento = Pagamento.builder()
                .id(1L)
                .clienteId(1L)
                .valor(BigDecimal.TEN)
                .status(StatusPagamentoEnum.PENDENTE)
                .parcelas(1)
                .pedidoId("123456")
                .metodoPagamento(MetodoPagamentoEnum.CARTAO_CREDITO)
                .perfilPagamento(PerfilPagamento.builder().build())
                .build();
        final var pagamentoResponse = PagamentoResponse.builder()
                .id(pagamento.getId())
                .clienteId(pagamento.getClienteId())
                .valor(pagamento.getValor())
                .pedidoId(pagamento.getPedidoId())
                .status(StatusPagamentoEnum.SUCESSO)
                .build();
        final var mercadoPagoPayment = MercadoPagoPayment.builder()
                .status("approved")
                .build();
        final var pagamentoContext = PagamentoContext.builder()
                .pagamento(pagamento)
                .mercadoPagoPayment(mercadoPagoPayment)
                .build();
        when(pagamentoRepository.save(any(Pagamento.class)))
                .thenAnswer(invocation -> {
                    final var savedToDB = (Pagamento) invocation.getArgument(0);
                    assertEquals(StatusPagamentoEnum.SUCESSO, savedToDB.getStatus());
                    return savedToDB;
                });

        when(pagamentoResponseCustomAdapter.adapt(any(PagamentoContext.class)))
                .thenReturn(pagamentoResponse);

        //when
        final var actual = atualizarPagamentoUseCase.execute(pagamentoContext);

        //then
        assertNotNull(actual);
        assertEquals(pagamentoResponse, actual);
        assertEquals(StatusPagamentoEnum.SUCESSO, actual.getStatus());
        assertEquals(pagamento.getId(), actual.getId());
        assertEquals(pagamento.getValor(), actual.getValor());
    }

    @Test
    public void atualizarPagamentoDeveRetornarSucesso2() {
        //given
        final var pagamento = Pagamento.builder()
                .id(1L)
                .clienteId(1L)
                .valor(BigDecimal.TEN)
                .status(StatusPagamentoEnum.PENDENTE)
                .parcelas(1)
                .pedidoId("123456")
                .metodoPagamento(MetodoPagamentoEnum.CARTAO_CREDITO)
                .perfilPagamento(PerfilPagamento.builder().build())
                .build();
        final var pagamentoResponse = PagamentoResponse.builder()
                .id(pagamento.getId())
                .clienteId(pagamento.getClienteId())
                .valor(pagamento.getValor())
                .pedidoId(pagamento.getPedidoId())
                .status(StatusPagamentoEnum.FALHA)
                .build();
        final var mercadoPagoPayment = MercadoPagoPayment.builder()
                .status("rejected")
                .build();
        final var pagamentoContext = PagamentoContext.builder()
                .pagamento(pagamento)
                .mercadoPagoPayment(mercadoPagoPayment)
                .build();
        when(pagamentoRepository.save(any(Pagamento.class)))
                .thenAnswer(invocation -> {
                    final var savedToDB = (Pagamento) invocation.getArgument(0);
                    assertEquals(StatusPagamentoEnum.FALHA, savedToDB.getStatus());
                    return savedToDB;
                });

        when(pagamentoResponseCustomAdapter.adapt(any(PagamentoContext.class)))
                .thenReturn(pagamentoResponse);

        //when
        final var actual = atualizarPagamentoUseCase.execute(pagamentoContext);

        //then
        assertNotNull(actual);
        assertEquals(pagamentoResponse, actual);
        assertEquals(StatusPagamentoEnum.FALHA, actual.getStatus());
        assertEquals(pagamento.getId(), actual.getId());
        assertEquals(pagamento.getValor(), actual.getValor());
    }

    @Test
    public void atualizarPagamentoDeveRetornarSucesso3() {
        //given
        final var pagamento = Pagamento.builder()
                .id(1L)
                .clienteId(1L)
                .valor(BigDecimal.TEN)
                .status(StatusPagamentoEnum.PENDENTE)
                .parcelas(1)
                .pedidoId("123456")
                .metodoPagamento(MetodoPagamentoEnum.CARTAO_CREDITO)
                .perfilPagamento(PerfilPagamento.builder().build())
                .build();
        final var pagamentoResponse = PagamentoResponse.builder()
                .id(pagamento.getId())
                .clienteId(pagamento.getClienteId())
                .valor(pagamento.getValor())
                .pedidoId(pagamento.getPedidoId())
                .status(StatusPagamentoEnum.PENDENTE)
                .build();
        final var mercadoPagoPayment = MercadoPagoPayment.builder()
                .status("pending")
                .build();
        final var pagamentoContext = PagamentoContext.builder()
                .pagamento(pagamento)
                .mercadoPagoPayment(mercadoPagoPayment)
                .build();
        when(pagamentoRepository.save(any(Pagamento.class)))
                .thenAnswer(invocation -> {
                    final var savedToDB = (Pagamento) invocation.getArgument(0);
                    assertEquals(StatusPagamentoEnum.PENDENTE, savedToDB.getStatus());
                    return savedToDB;
                });

        when(pagamentoResponseCustomAdapter.adapt(any(PagamentoContext.class)))
                .thenReturn(pagamentoResponse);

        //when
        final var actual = atualizarPagamentoUseCase.execute(pagamentoContext);

        //then
        assertNotNull(actual);
        assertEquals(pagamentoResponse, actual);
        assertEquals(StatusPagamentoEnum.PENDENTE, actual.getStatus());
        assertEquals(pagamento.getId(), actual.getId());
        assertEquals(pagamento.getValor(), actual.getValor());
    }
}
