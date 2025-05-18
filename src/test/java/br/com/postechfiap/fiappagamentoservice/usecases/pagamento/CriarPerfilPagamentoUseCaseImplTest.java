package br.com.postechfiap.fiappagamentoservice.usecases.pagamento;

import br.com.postechfiap.fiappagamentoservice.adapter.PerfilPagamentoAdapter;
import br.com.postechfiap.fiappagamentoservice.controller.dto.request.PerfilPagamentoRequest;
import br.com.postechfiap.fiappagamentoservice.entities.MercadoPagoCard;
import br.com.postechfiap.fiappagamentoservice.entities.PerfilPagamento;
import br.com.postechfiap.fiappagamentoservice.enuns.StatusBasicoEnum;
import br.com.postechfiap.fiappagamentoservice.interfaces.repository.PerfilPagamentoRepository;
import br.com.postechfiap.fiappagamentoservice.interfaces.usecases.CriarMercadoPagoCardUseCase;
import br.com.postechfiap.fiappagamentoservice.interfaces.usecases.CriarPerfilPagamentoUseCase;
import br.com.postechfiap.fiappagamentoservice.usecase.mercadoPago.dto.PagamentoContext;
import br.com.postechfiap.fiappagamentoservice.usecase.pagamento.CriarPerfilPagamentoUseCaseImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CriarPerfilPagamentoUseCaseImplTest {

    @Mock
    private PerfilPagamentoRepository perfilCPagamentoRepository;
    @Mock
    private PerfilPagamentoAdapter perfilPagamentoAdapter;
    @Mock
    private CriarMercadoPagoCardUseCase criarMercadoPagoCardUseCase;

    private CriarPerfilPagamentoUseCase criarPerfilPagamentoUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        criarPerfilPagamentoUseCase = new CriarPerfilPagamentoUseCaseImpl(perfilCPagamentoRepository, perfilPagamentoAdapter,
                criarMercadoPagoCardUseCase);
    }

    @Test
    public void criarPerfilPagamentoDeveRetornarSucesso() {
        //given
        final var perfilPagamentoRequest = PerfilPagamentoRequest.builder()
                .numeroCartao("1234567890123456")
                .dataValidade(LocalDate.of(2030, 12, 31))
                .nomeTitularCartao("Nome Teste")
                .codigoSegurancaCartao("123")
                .build();
        final var pagamentoContext = PagamentoContext.builder()
                .perfilPagamentoRequest(perfilPagamentoRequest)
                .build();
        final var perfilPagamento = PerfilPagamento.builder()
                .clienteId(1L)
                .dataValidade(LocalDate.of(2030, 12, 31))
                .status(StatusBasicoEnum.ATIVO)
                .primeirosNumerosCartao("1234")
                .ultimosNumerosCartao("5678")
                .nomeTitularCartao("Nome Teste")
                .build();

        when(perfilPagamentoAdapter.adapt(perfilPagamentoRequest))
                .thenReturn(perfilPagamento);
        when(perfilCPagamentoRepository.save(perfilPagamento))
                .thenReturn(perfilPagamento);
        when(criarMercadoPagoCardUseCase.execute(any(PagamentoContext.class)))
                .thenReturn(new MercadoPagoCard());

        //when
        final var actual = criarPerfilPagamentoUseCase.execute(pagamentoContext);

        //then
        assertNotNull(actual);
    }

    @Test
    public void criarPerfilPagamentoDeveRetornarErro() {
        //given
        final var perfilPagamentoRequest = PerfilPagamentoRequest.builder()
                .numeroCartao("1234567890123456")
                .dataValidade(LocalDate.of(2030, 12, 31))
                .nomeTitularCartao("Nome Teste")
                .codigoSegurancaCartao("123")
                .build();
        final var pagamentoContext = PagamentoContext.builder()
                .perfilPagamentoRequest(perfilPagamentoRequest)
                .build();
        final var perfilPagamento = PerfilPagamento.builder()
                .clienteId(1L)
                .dataValidade(LocalDate.of(2030, 12, 31))
                .status(StatusBasicoEnum.ATIVO)
                .primeirosNumerosCartao("1234")
                .ultimosNumerosCartao("5678")
                .nomeTitularCartao("Nome Teste")
                .build();

        when(perfilPagamentoAdapter.adapt(perfilPagamentoRequest))
                .thenReturn(perfilPagamento);
        when(perfilCPagamentoRepository.save(perfilPagamento))
                .thenReturn(perfilPagamento);
        when(criarMercadoPagoCardUseCase.execute(any(PagamentoContext.class)))
                .thenThrow(new RuntimeException("Erro ao gerar cartão"));

        //when
        //then
        assertThatThrownBy(() -> criarPerfilPagamentoUseCase.execute(pagamentoContext))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Erro ao gerar cartão");
    }
}
