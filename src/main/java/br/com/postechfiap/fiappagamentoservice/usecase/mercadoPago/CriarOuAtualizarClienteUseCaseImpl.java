package br.com.postechfiap.fiappagamentoservice.usecase.mercadoPago;

import br.com.postechfiap.fiappagamentoservice.adapter.MercadoPagoCustomerAdapter;
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
import org.springframework.stereotype.Service;

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
        final var mercadoPagoResponse = mercadoPagoClient.updateCustomer(mercadoPagoCustomer.getMercadoPagoCustomerId(), mercadoPagoUpdateCustomerRequest);
        final var mercadoPagoCustomerUpdated = mercadoPagoCustomerAdapter.adapt(mercadoPagoResponse, mercadoPagoCustomer);
        return mercadoPagoCustomerRepository.save(mercadoPagoCustomerUpdated);
    }

    private static MercadoPagoUpdateCustomerRequest buildMercadoPagoUpdateCustomerRequest(ClienteResponse clienteResponse) {
        return MercadoPagoUpdateCustomerRequest.builder()
                .firstName(clienteResponse.getNome())
                .lastName(getSobreNome(clienteResponse))
                .identification(MercadoPagoIdentificationRequest.builder()
                        .type("CPF")
                        .number(clienteResponse.getCpf())
                        .build())
                .address(MercadoPagoCustomerAddressRequest.builder()
                        .zipCode(clienteResponse.getEnderecos().getFirst().getCep())
                        .streetName(clienteResponse.getEnderecos().getFirst().getLogradouro())
                        .streetNumber(clienteResponse.getEnderecos().getFirst().getNumero())
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
                .firstName(getNome(clienteResponse))
                .lastName(getSobreNome(clienteResponse))
                .identification(MercadoPagoIdentificationRequest.builder()
                        .type("CPF")
                        .number(clienteResponse.getCpf())
                        .build())
                .address(MercadoPagoCustomerAddressRequest.builder()
                        .zipCode(clienteResponse.getEnderecos().getFirst().getCep())
                        .streetName(clienteResponse.getEnderecos().getFirst().getLogradouro())
                        .streetNumber(clienteResponse.getEnderecos().getFirst().getNumero())
                        .build())
                .build();
    }

    private static String getNome(ClienteResponse clienteResponse) {
        final var nomeCompleto = clienteResponse.getNome();

        String[] partes = nomeCompleto.split(" ", 2);
        return partes[0];
    }

    private static String getSobreNome(ClienteResponse clienteResponse) {
        final var nome = clienteResponse.getNome();
        if (nome == null || nome.trim().isEmpty()) {
            return "";
        }

        final var partes = nome.trim().split("\\s+");

        if (partes.length == 1) {
            return "";
        }

        return partes[partes.length - 1];
    }
}
