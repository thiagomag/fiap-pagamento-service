package br.com.postechfiap.fiappagamentoservice.usecase.pagamento;

import br.com.postechfiap.fiappagamentoservice.adapters.PerfilPagamentoAdapter;
import br.com.postechfiap.fiappagamentoservice.controller.dto.request.PagamentoRequest;
import br.com.postechfiap.fiappagamentoservice.entities.MercadoPagoCard;
import br.com.postechfiap.fiappagamentoservice.interfaces.repository.MercadoPagoCardRepository;
import br.com.postechfiap.fiappagamentoservice.interfaces.repository.PerfilPagamentoRepository;
import br.com.postechfiap.fiappagamentoservice.interfaces.usecases.CriarMercadoPagoCardUseCase;
import br.com.postechfiap.fiappagamentoservice.interfaces.usecases.CriarPerfilPagamentoUseCase;
import br.com.postechfiap.fiappagamentoservice.usecase.mercadoPago.dto.CriarMercadoPagoCard;
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
    public MercadoPagoCard execute(PagamentoRequest pagamentoRequest) {
        final var perfilPagamentpRequest = pagamentoRequest.getPerfilPagamento();
        final var perfilCPagamento = perfilCPagamentoRepository.save(perfilCPagamentoAdapter.adapt(perfilPagamentpRequest));
        final var mercadoPagoCard = criarMercadoPagoCardUseCase.execute(buildCriarMercadoPagoCard(pagamentoRequest));
        mercadoPagoCard.setPerfilPagamento(perfilCPagamento);
        return mercadoPagoCardRepository.save(mercadoPagoCard);
    }

    private CriarMercadoPagoCard buildCriarMercadoPagoCard(PagamentoRequest pagamentoRequest) {
        return CriarMercadoPagoCard.builder()
                .perfilPagamentoRequest(pagamentoRequest.getPerfilPagamento())
                .clienteResponse(pagamentoRequest.getCliente())
                .build();
    }
}
