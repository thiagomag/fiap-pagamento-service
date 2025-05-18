package br.com.postechfiap.fiappagamentoservice.usecases.mercadopago;

import br.com.postechfiap.fiappagamentoservice.adapter.MercadoPagoCustomerAdapter;
import br.com.postechfiap.fiappagamentoservice.client.clienteService.dto.ClienteResponse;
import br.com.postechfiap.fiappagamentoservice.client.clienteService.dto.EnderecoResponse;
import br.com.postechfiap.fiappagamentoservice.client.mercadopago.MercadoPagoClient;
import br.com.postechfiap.fiappagamentoservice.client.mercadopago.dto.request.MercadoPagoCreateCustomerRequest;
import br.com.postechfiap.fiappagamentoservice.client.mercadopago.dto.request.MercadoPagoUpdateCustomerRequest;
import br.com.postechfiap.fiappagamentoservice.client.mercadopago.dto.response.MercadoPagoCustomerResponse;
import br.com.postechfiap.fiappagamentoservice.entities.MercadoPagoCustomer;
import br.com.postechfiap.fiappagamentoservice.interfaces.repository.MercadoPagoCustomerRepository;
import br.com.postechfiap.fiappagamentoservice.interfaces.usecases.CriarOuAtualizarClienteUseCase;
import br.com.postechfiap.fiappagamentoservice.usecase.mercadoPago.CriarOuAtualizarClienteUseCaseImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CriarOuAtualizarClienteUseCaseImplTest {

    @Mock
    private MercadoPagoClient mercadoPagoClient;
    @Mock
    private MercadoPagoCustomerRepository mercadoPagoCustomerRepository;
    @Mock
    private MercadoPagoCustomerAdapter mercadoPagoCustomerAdapter;

    private CriarOuAtualizarClienteUseCase criarOuAtualizarClienteUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        criarOuAtualizarClienteUseCase = new CriarOuAtualizarClienteUseCaseImpl(mercadoPagoClient, mercadoPagoCustomerRepository,
                mercadoPagoCustomerAdapter);
    }

    @Test
    public void criarClienteDeveRetornarSucesso() {
        //given
        final var clienteResponse = ClienteResponse.builder()
                .id(1L)
                .nome("Nome Teste")
                .cpf("12345678901")
                .email("teste@teste.com")
                .enderecos(List.of(EnderecoResponse.builder()
                        .cep("12345678")
                        .bairro("Bairro Teste")
                        .cidade("Cidade Teste")
                        .estado("Estado Teste")
                        .logradouro("Logradouro Teste")
                        .numero(123)
                        .complemento("Complemento Teste")
                        .build()))
                .build();

        final var mercadoPagoCustomerResponse = MercadoPagoCustomerResponse.builder()
                .id("customerId")
                .firstName("Nome Teste")
                .lastName("Sobrenome Teste")
                .email("teste@teste.com")
                .build();

        final var mercadoPagoCustomer = new MercadoPagoCustomer();
        mercadoPagoCustomer.setClienteId(1L);
        mercadoPagoCustomer.setId(1L);
        mercadoPagoCustomer.setEmail(mercadoPagoCustomerResponse.getEmail());
        mercadoPagoCustomer.setFirstName(mercadoPagoCustomerResponse.getFirstName());
        mercadoPagoCustomer.setLastName(mercadoPagoCustomerResponse.getLastName());
        mercadoPagoCustomer.setMercadoPagoCustomerId("customerId");

        when(mercadoPagoCustomerRepository.findByClienteId(1L))
                .thenReturn(Optional.empty());

        when(mercadoPagoClient.createCustomer(any(MercadoPagoCreateCustomerRequest.class)))
                .thenReturn(mercadoPagoCustomerResponse);

        when(mercadoPagoCustomerAdapter.adapt(mercadoPagoCustomerResponse))
                .thenReturn(mercadoPagoCustomer);

        when(mercadoPagoCustomerRepository.save(mercadoPagoCustomer))
                .thenAnswer(invocation -> {
                    final var savedCustomer = (MercadoPagoCustomer) invocation.getArgument(0);
                    assertEquals(mercadoPagoCustomer.getClienteId(), savedCustomer.getClienteId());
                    assertEquals(mercadoPagoCustomer.getMercadoPagoCustomerId(), savedCustomer.getMercadoPagoCustomerId());
                    assertEquals(mercadoPagoCustomer.getEmail(), savedCustomer.getEmail());
                    assertEquals(mercadoPagoCustomer.getFirstName(), savedCustomer.getFirstName());
                    assertEquals(mercadoPagoCustomer.getLastName(), savedCustomer.getLastName());
                    assertEquals(mercadoPagoCustomer.getId(), savedCustomer.getId());

                    return savedCustomer;
                });

        //when
        final var actual = criarOuAtualizarClienteUseCase.execute(clienteResponse);

        //then
        assertNotNull(actual);

        verify(mercadoPagoCustomerRepository, times(1)).findByClienteId(anyLong());
        verify(mercadoPagoClient, times(1)).createCustomer(any(MercadoPagoCreateCustomerRequest.class));
        verify(mercadoPagoClient, times(0)).updateCustomer(anyString(), any(MercadoPagoUpdateCustomerRequest.class));
        verify(mercadoPagoCustomerAdapter, times(1)).adapt(any(MercadoPagoCustomerResponse.class));
        verify(mercadoPagoCustomerRepository, times(1)).save(any(MercadoPagoCustomer.class));
    }

    @Test
    public void atualizarClienteDeveRetornarSucesso() {
        //given
        final var clienteResponse = ClienteResponse.builder()
                .id(1L)
                .nome("Nome Teste")
                .cpf("12345678901")
                .email("teste@teste.com")
                .enderecos(List.of(EnderecoResponse.builder()
                        .cep("12345678")
                        .bairro("Bairro Teste")
                        .cidade("Cidade Teste")
                        .estado("Estado Teste")
                        .logradouro("Logradouro Teste")
                        .numero(123)
                        .complemento("Complemento Teste")
                        .build()))
                .build();

        final var mercadoPagoCustomerResponse = MercadoPagoCustomerResponse.builder()
                .id("customerId")
                .firstName("Novo Nome Teste")
                .lastName("Novo Sobrenome Teste")
                .email("teste@teste.com")
                .build();

        final var mercadoPagoCustomer = new MercadoPagoCustomer();
        mercadoPagoCustomer.setClienteId(1L);
        mercadoPagoCustomer.setId(1L);
        mercadoPagoCustomer.setEmail(mercadoPagoCustomerResponse.getEmail());
        mercadoPagoCustomer.setFirstName("Nome Teste");
        mercadoPagoCustomer.setLastName("Sobrenome Teste");
        mercadoPagoCustomer.setMercadoPagoCustomerId("customerId");

        final var mercadoPagoCustomerAtualizado = new MercadoPagoCustomer();
        mercadoPagoCustomerAtualizado.setClienteId(1L);
        mercadoPagoCustomerAtualizado.setId(1L);
        mercadoPagoCustomerAtualizado.setEmail(mercadoPagoCustomerResponse.getEmail());
        mercadoPagoCustomerAtualizado.setFirstName("Novo Nome Teste");
        mercadoPagoCustomerAtualizado.setLastName("Novo Sobrenome Teste");
        mercadoPagoCustomerAtualizado.setMercadoPagoCustomerId("customerId");

        when(mercadoPagoCustomerRepository.findByClienteId(1L))
                .thenReturn(Optional.of(mercadoPagoCustomer));

        when(mercadoPagoClient.updateCustomer(anyString(), any(MercadoPagoUpdateCustomerRequest.class)))
                .thenReturn(mercadoPagoCustomerResponse);

        when(mercadoPagoCustomerAdapter.adapt(mercadoPagoCustomerResponse, mercadoPagoCustomer))
                .thenReturn(mercadoPagoCustomerAtualizado);

        when(mercadoPagoCustomerRepository.save(mercadoPagoCustomerAtualizado))
                .thenAnswer(invocation -> {
                    final var savedCustomer = (MercadoPagoCustomer) invocation.getArgument(0);
                    assertEquals("Novo Nome Teste", savedCustomer.getFirstName());
                    assertEquals("Novo Sobrenome Teste", savedCustomer.getLastName());

                    return savedCustomer;
                });

        //when
        final var actual = criarOuAtualizarClienteUseCase.execute(clienteResponse);

        //then
        assertNotNull(actual);

        verify(mercadoPagoCustomerRepository, times(1)).findByClienteId(anyLong());
        verify(mercadoPagoClient, times(0)).createCustomer(any(MercadoPagoCreateCustomerRequest.class));
        verify(mercadoPagoClient, times(1)).updateCustomer(anyString(), any(MercadoPagoUpdateCustomerRequest.class));
        verify(mercadoPagoCustomerAdapter, times(1)).adapt(any(MercadoPagoCustomerResponse.class), any(MercadoPagoCustomer.class));
        verify(mercadoPagoCustomerRepository, times(1)).save(any(MercadoPagoCustomer.class));
    }


}
