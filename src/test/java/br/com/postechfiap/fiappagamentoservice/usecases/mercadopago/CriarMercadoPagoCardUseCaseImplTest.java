package br.com.postechfiap.fiappagamentoservice.usecases.mercadopago;

import br.com.postechfiap.fiappagamentoservice.adapter.MercadoPagoCardAdapter;
import br.com.postechfiap.fiappagamentoservice.client.clienteService.dto.ClienteResponse;
import br.com.postechfiap.fiappagamentoservice.client.mercadopago.MercadoPagoClient;
import br.com.postechfiap.fiappagamentoservice.client.mercadopago.dto.response.MercadoPagoCardResponse;
import br.com.postechfiap.fiappagamentoservice.client.mercadopago.dto.response.MercadoPagoCardTokenResponse;
import br.com.postechfiap.fiappagamentoservice.controller.dto.request.PerfilPagamentoRequest;
import br.com.postechfiap.fiappagamentoservice.entities.MercadoPagoCard;
import br.com.postechfiap.fiappagamentoservice.entities.MercadoPagoCustomer;
import br.com.postechfiap.fiappagamentoservice.interfaces.repository.MercadoPagoCardRepository;
import br.com.postechfiap.fiappagamentoservice.interfaces.usecases.CriarMercadoPagoCardUseCase;
import br.com.postechfiap.fiappagamentoservice.usecase.mercadoPago.CriarMercadoPagoCardUseCaseImpl;
import br.com.postechfiap.fiappagamentoservice.usecase.mercadoPago.dto.PagamentoContext;
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
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CriarMercadoPagoCardUseCaseImplTest {

    @Mock
    private MercadoPagoClient mercadoPagoClient;
    @Mock
    private MercadoPagoCardAdapter mercadoPagoCardAdapter;
    @Mock
    private MercadoPagoCardRepository mercadoPagoCardRepository;

    private CriarMercadoPagoCardUseCase criarMercadoPagoCardUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        criarMercadoPagoCardUseCase = new CriarMercadoPagoCardUseCaseImpl(mercadoPagoClient, mercadoPagoCardAdapter,
                mercadoPagoCardRepository);
    }

    @Test
    public void criarMercadoPagoCardDeveRetornarSucesso() {
        //given
        final var perfilPagamentoRequest = PerfilPagamentoRequest.builder()
                .numeroCartao("1234567890123456")
                .dataValidade(LocalDate.of(2030, 12, 31))
                .nomeTitularCartao("Nome Teste")
                .codigoSegurancaCartao("123")
                .build();
        final var mercadoPagoCustomer = new MercadoPagoCustomer();
        final var clienteResponse = ClienteResponse.builder()
                .id(1L)
                .nome("Nome Teste")
                .cpf("12345678901")
                .enderecos(null)
                .build();
        final var tokenResponse = MercadoPagoCardTokenResponse.builder()
                .id("token123")
                .build();
        final var mercadoPagoCardResponse = MercadoPagoCardResponse.builder()
                .id(1L)
                .firstSixDigits("123456")
                .lastFourDigits("3456")
                .expirationMonth(12)
                .expirationYear(30)
                .build();
        final var mercadoPagoCard = new MercadoPagoCard();
        final var pagamentoContext = PagamentoContext.builder()
                .perfilPagamentoRequest(perfilPagamentoRequest)
                .mercadoPagoCustomer(mercadoPagoCustomer)
                .clienteResponse(clienteResponse)
                .build();

        when(mercadoPagoClient.generateCardToken(any(), any()))
                .thenReturn(tokenResponse);
        when(mercadoPagoClient.createCard(any(), any()))
                .thenReturn(mercadoPagoCardResponse);
        when(mercadoPagoCardAdapter.adapt(any(MercadoPagoCardResponse.class)))
                .thenReturn(mercadoPagoCard);
        when(mercadoPagoCardRepository.save(any(MercadoPagoCard.class)))
                .thenReturn(mercadoPagoCard);
        //when
        final var actual = criarMercadoPagoCardUseCase.execute(pagamentoContext);

        //then
        assertNotNull(actual);
    }

    @Test
    public void criarMercadoPagoCardDeveRetornarErro() {
        //given
        final var perfilPagamentoRequest = PerfilPagamentoRequest.builder()
                .numeroCartao("1234567890123456")
                .dataValidade(LocalDate.of(2030, 12, 31))
                .nomeTitularCartao("Nome Teste")
                .build();
        final var mercadoPagoCustomer = new MercadoPagoCustomer();
        final var clienteResponse = ClienteResponse.builder()
                .id(1L)
                .nome("Nome Teste")
                .cpf("12345678901")
                .enderecos(null)
                .build();
        final var pagamentoContext = PagamentoContext.builder()
                .perfilPagamentoRequest(perfilPagamentoRequest)
                .mercadoPagoCustomer(mercadoPagoCustomer)
                .clienteResponse(clienteResponse)
                .build();

        //when
        //then
        assertThatThrownBy(() -> criarMercadoPagoCardUseCase.execute(pagamentoContext))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Código de segurança não pode ser nulo ou vazio");

        verifyNoInteractions(mercadoPagoClient, mercadoPagoCardAdapter, mercadoPagoCardRepository);
    }

    @Test
    public void criarMercadoPagoCardDeveRetornarErro2() {
        //given
        final var perfilPagamentoRequest = PerfilPagamentoRequest.builder()
                .numeroCartao(null)
                .dataValidade(LocalDate.of(2030, 12, 31))
                .nomeTitularCartao("Nome Teste")
                .codigoSegurancaCartao("123")
                .build();
        final var mercadoPagoCustomer = new MercadoPagoCustomer();
        final var clienteResponse = ClienteResponse.builder()
                .id(1L)
                .nome("Nome Teste")
                .cpf("12345678901")
                .enderecos(null)
                .build();
        final var pagamentoContext = PagamentoContext.builder()
                .perfilPagamentoRequest(perfilPagamentoRequest)
                .mercadoPagoCustomer(mercadoPagoCustomer)
                .clienteResponse(clienteResponse)
                .build();

        //when
        //then
        assertThatThrownBy(() -> criarMercadoPagoCardUseCase.execute(pagamentoContext))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Número do cartão não pode ser nulo ou vazio");

        verifyNoInteractions(mercadoPagoClient, mercadoPagoCardAdapter, mercadoPagoCardRepository);
    }

    @Test
    public void criarMercadoPagoCardDeveRetornarErro3() {
        //given
        final var perfilPagamentoRequest = PerfilPagamentoRequest.builder()
                .numeroCartao("1234567890123456")
                .nomeTitularCartao("Nome Teste")
                .codigoSegurancaCartao("123")
                .build();
        final var mercadoPagoCustomer = new MercadoPagoCustomer();
        final var clienteResponse = ClienteResponse.builder()
                .id(1L)
                .nome("Nome Teste")
                .cpf("12345678901")
                .enderecos(null)
                .build();
        final var pagamentoContext = PagamentoContext.builder()
                .perfilPagamentoRequest(perfilPagamentoRequest)
                .mercadoPagoCustomer(mercadoPagoCustomer)
                .clienteResponse(clienteResponse)
                .build();

        //when
        //then
        assertThatThrownBy(() -> criarMercadoPagoCardUseCase.execute(pagamentoContext))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Data de validade não pode ser nula");

        verifyNoInteractions(mercadoPagoClient, mercadoPagoCardAdapter, mercadoPagoCardRepository);
    }

    @Test
    public void criarMercadoPagoCardDeveRetornarErro4() {
        //given
        final var perfilPagamentoRequest = PerfilPagamentoRequest.builder()
                .numeroCartao("")
                .dataValidade(LocalDate.of(2030, 12, 31))
                .nomeTitularCartao("Nome Teste")
                .codigoSegurancaCartao("123")
                .build();
        final var mercadoPagoCustomer = new MercadoPagoCustomer();
        final var clienteResponse = ClienteResponse.builder()
                .id(1L)
                .nome("Nome Teste")
                .cpf("12345678901")
                .enderecos(null)
                .build();
        final var pagamentoContext = PagamentoContext.builder()
                .perfilPagamentoRequest(perfilPagamentoRequest)
                .mercadoPagoCustomer(mercadoPagoCustomer)
                .clienteResponse(clienteResponse)
                .build();

        //when
        //then
        assertThatThrownBy(() -> criarMercadoPagoCardUseCase.execute(pagamentoContext))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Número do cartão não pode ser nulo ou vazio");

        verifyNoInteractions(mercadoPagoClient, mercadoPagoCardAdapter, mercadoPagoCardRepository);
    }

    @Test
    public void criarMercadoPagoCardDeveRetornarErro5() {
        //given
        final var perfilPagamentoRequest = PerfilPagamentoRequest.builder()
                .numeroCartao("1234567890123456")
                .dataValidade(LocalDate.of(2030, 12, 31))
                .nomeTitularCartao("Nome Teste")
                .codigoSegurancaCartao("")
                .build();
        final var mercadoPagoCustomer = new MercadoPagoCustomer();
        final var clienteResponse = ClienteResponse.builder()
                .id(1L)
                .nome("Nome Teste")
                .cpf("12345678901")
                .enderecos(null)
                .build();
        final var pagamentoContext = PagamentoContext.builder()
                .perfilPagamentoRequest(perfilPagamentoRequest)
                .mercadoPagoCustomer(mercadoPagoCustomer)
                .clienteResponse(clienteResponse)
                .build();

        //when
        //then
        assertThatThrownBy(() -> criarMercadoPagoCardUseCase.execute(pagamentoContext))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Código de segurança não pode ser nulo ou vazio");

        verifyNoInteractions(mercadoPagoClient, mercadoPagoCardAdapter, mercadoPagoCardRepository);
    }
}
