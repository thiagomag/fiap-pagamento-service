package br.com.postechfiap.fiappagamentoservice.usecase.pagamento;

import br.com.postechfiap.fiappagamentoservice.adapters.PerfilPagamentoAdapter;
import br.com.postechfiap.fiappagamentoservice.controller.dto.request.PagamentoRequest;
import br.com.postechfiap.fiappagamentoservice.entities.PerfilPagamento;
import br.com.postechfiap.fiappagamentoservice.interfaces.repository.MercadoPagoCardRepository;
import br.com.postechfiap.fiappagamentoservice.interfaces.repository.PerfilPagamentoRepository;
import br.com.postechfiap.fiappagamentoservice.interfaces.usecases.CriarMercadoPagoCardUseCase;
import br.com.postechfiap.fiappagamentoservice.interfaces.usecases.CriarPerfilPagamentoUseCase;
import br.com.postechfiap.fiappagamentoservice.usecase.mercadoPago.dto.PagamentoContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CriarPerfilPagamentoUseCaseImpl implements CriarPerfilPagamentoUseCase {

    private final PerfilPagamentoRepository perfilCPagamentoRepository;
    private final PerfilPagamentoAdapter perfilCPagamentoAdapter;
    private final CriarMercadoPagoCardUseCase criarMercadoPagoCardUseCase;
    private final MercadoPagoCardRepository mercadoPagoCardRepository;

    @Override
    public PagamentoContext execute(PagamentoRequest pagamentoRequest) {
        final var perfilPagamentpRequest = pagamentoRequest.getPerfilPagamento();
        final var perfilCPagamento = perfilCPagamentoRepository.save(perfilCPagamentoAdapter.adapt(perfilPagamentpRequest));
        final var pagamentoContext = buildPagamentoContext(pagamentoRequest, perfilCPagamento);
        final var mercadoPagoCard = criarMercadoPagoCardUseCase.execute(pagamentoContext);
        mercadoPagoCard.setPerfilPagamento(perfilCPagamento);
        pagamentoContext.setMercadoPagoCard(mercadoPagoCard);
        mercadoPagoCardRepository.save(mercadoPagoCard);
        return pagamentoContext;
    }

    private PagamentoContext buildPagamentoContext(PagamentoRequest pagamentoRequest, PerfilPagamento perfilCPagamento) {
        return PagamentoContext.builder()
                .pagamentoRequest(pagamentoRequest)
                .perfilPagamento(perfilCPagamento)
                .perfilPagamentoRequest(pagamentoRequest.getPerfilPagamento())
                .clienteResponse(pagamentoRequest.getCliente())
                .build();
    }
}
