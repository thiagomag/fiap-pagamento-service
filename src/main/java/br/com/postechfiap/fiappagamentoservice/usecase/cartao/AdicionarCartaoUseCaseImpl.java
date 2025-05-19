package br.com.postechfiap.fiappagamentoservice.usecase.cartao;

import br.com.postechfiap.fiappagamentoservice.adapter.PerfilPagamentoResponseAdapter;
import br.com.postechfiap.fiappagamentoservice.client.clienteService.ClienteServiceClient;
import br.com.postechfiap.fiappagamentoservice.controller.dto.request.PerfilPagamentoRequest;
import br.com.postechfiap.fiappagamentoservice.controller.dto.response.PerfilPagamentoResponse;
import br.com.postechfiap.fiappagamentoservice.exception.EntityNotFoundException;
import br.com.postechfiap.fiappagamentoservice.interfaces.repository.MercadoPagoCustomerRepository;
import br.com.postechfiap.fiappagamentoservice.interfaces.usecases.AdicionarCartaoUseCase;
import br.com.postechfiap.fiappagamentoservice.usecase.mercadoPago.dto.PagamentoContext;
import br.com.postechfiap.fiappagamentoservice.usecase.pagamento.CriarPerfilPagamentoUseCaseImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdicionarCartaoUseCaseImpl implements AdicionarCartaoUseCase {

    private final CriarPerfilPagamentoUseCaseImpl criarMercadoPagoCardUseCase;
    private final MercadoPagoCustomerRepository mercadoPagoCustomerRepository;
    private final ClienteServiceClient clienteServiceClient;
    private final PerfilPagamentoResponseAdapter perfilPagamentoResponseAdapter;

    @Override
    public PerfilPagamentoResponse execute(PerfilPagamentoRequest request) {
        final var clienteResponse = clienteServiceClient.getCliente(request.getIdCliente());
        final var mercadoPagoCustomer = mercadoPagoCustomerRepository.findByClienteId(clienteResponse.getId())
                .orElseThrow(() -> new EntityNotFoundException("mercado_pago_customer", clienteResponse.getId().toString()));
        var pagamentoContext = PagamentoContext.builder()
                .perfilPagamentoRequest(request)
                .mercadoPagoCustomer(mercadoPagoCustomer)
                .clienteResponse(clienteResponse)
                .build();
        pagamentoContext = criarMercadoPagoCardUseCase.execute(pagamentoContext);
        return perfilPagamentoResponseAdapter.adapt(pagamentoContext.getPerfilPagamento());
    }
}
