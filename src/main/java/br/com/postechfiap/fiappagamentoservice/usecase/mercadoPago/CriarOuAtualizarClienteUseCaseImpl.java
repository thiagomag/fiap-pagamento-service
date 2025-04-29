package br.com.postechfiap.fiappagamentoservice.usecase.mercadoPago;

import br.com.postechfiap.fiappagamentoservice.adapters.MercadoPagoCustomerAdapter;
import br.com.postechfiap.fiappagamentoservice.client.clienteService.dto.ClienteResponse;
import br.com.postechfiap.fiappagamentoservice.client.mercadopago.MercadoPagoClient;
import br.com.postechfiap.fiappagamentoservice.client.mercadopago.dto.request.MercadoPagoCreateCustomerRequest;
import br.com.postechfiap.fiappagamentoservice.client.mercadopago.dto.request.MercadoPagoCustomerAddressRequest;
import br.com.postechfiap.fiappagamentoservice.client.mercadopago.dto.request.MercadoPagoIdentificationRequest;
import br.com.postechfiap.fiappagamentoservice.client.mercadopago.dto.request.MercadoPagoUpdateCustomerRequest;
import br.com.postechfiap.fiappagamentoservice.entities.MercadoPagoCustomer;
import br.com.postechfiap.fiappagamentoservice.interfaces.repository.MercadoPagoCustomerRepository;
import br.com.postechfiap.fiappagamentoservice.interfaces.usecases.CriarOuAtualizarClienteUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CriarOuAtualizarClienteUseCaseImpl implements CriarOuAtualizarClienteUseCase {

    private final MercadoPagoClient mercadoPagoClient;
    private final MercadoPagoCustomerRepository mercadoPagoCustomerRepository;
    private final MercadoPagoCustomerAdapter mercadoPagoCustomerAdapter;

    @Override
    public MercadoPagoCustomer execute(ClienteResponse clienteResponse) {
        final var mercadoPagoCustomer = mercadoPagoCustomerRepository.findByClienteId(clienteResponse.getId());
        return mercadoPagoCustomer.map(pagoCustomer -> atualizarMercadoPagoCustomer(pagoCustomer, clienteResponse))
                .orElseGet(() -> criarMercadoPagoCustomer(clienteResponse));
    }

    private MercadoPagoCustomer atualizarMercadoPagoCustomer(MercadoPagoCustomer mercadoPagoCustomer, ClienteResponse clienteResponse) {
        final var mercadoPagoUpdateCustomerRequest = buildMercadoPagoUpdateCustomerRequest(clienteResponse);
        final var mercadoPagoResponse = mercadoPagoClient.updateCustomer(mercadoPagoCustomer.getId(), mercadoPagoUpdateCustomerRequest);
        final var mercadoPagoCustomerUpdated = mercadoPagoCustomerAdapter.adapt(mercadoPagoResponse);
        return mercadoPagoCustomerRepository.save(mercadoPagoCustomerUpdated);
    }

    private static MercadoPagoUpdateCustomerRequest buildMercadoPagoUpdateCustomerRequest(ClienteResponse clienteResponse) {
        return MercadoPagoUpdateCustomerRequest.builder()
                .firstName(clienteResponse.getNome())
                .lastName(clienteResponse.getSobrenome())
                .identification(MercadoPagoIdentificationRequest.builder()
                        .type("CPF")
                        .number(clienteResponse.getCpf())
                        .build())
                .address(MercadoPagoCustomerAddressRequest.builder()
                        .zipCode(clienteResponse.getEndereco().getCep())
                        .streetName(clienteResponse.getEndereco().getLogradouro())
                        .streetNumber(clienteResponse.getEndereco().getNumero())
                        .build())
                .build();
    }

    private MercadoPagoCustomer criarMercadoPagoCustomer(ClienteResponse clienteResponse) {
        final var mercadoPagoCreateCustomerRequest = buildMercadoPagoCreateCustomerRequest(clienteResponse);
        final var mercadoPagoCustomerResponse = mercadoPagoClient.createCustomer(mercadoPagoCreateCustomerRequest);
        final var mercadoPagoCustomer = mercadoPagoCustomerAdapter.adapt(mercadoPagoCustomerResponse);
        mercadoPagoCustomer.setClienteId(clienteResponse.getId());
        return mercadoPagoCustomerRepository.save(mercadoPagoCustomer);
    }

    private static MercadoPagoCreateCustomerRequest buildMercadoPagoCreateCustomerRequest(ClienteResponse clienteResponse) {
        return MercadoPagoCreateCustomerRequest.builder()
                .email(clienteResponse.getEmail())
                .firstName(clienteResponse.getNome())
                .lastName(clienteResponse.getSobrenome())
                .identification(MercadoPagoIdentificationRequest.builder()
                        .type("CPF")
                        .number(clienteResponse.getCpf())
                        .build())
                .address(MercadoPagoCustomerAddressRequest.builder()
                        .zipCode(clienteResponse.getEndereco().getCep())
                        .streetName(clienteResponse.getEndereco().getLogradouro())
                        .streetNumber(clienteResponse.getEndereco().getNumero())
                        .build())
                .build();
    }
}
