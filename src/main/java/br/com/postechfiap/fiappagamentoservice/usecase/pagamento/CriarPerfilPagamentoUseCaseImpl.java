package br.com.postechfiap.fiappagamentoservice.usecase.pagamento;

import br.com.postechfiap.fiappagamentoservice.adapter.PerfilPagamentoAdapter;
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
    private final PerfilPagamentoAdapter perfilPagamentoAdapter;
    private final CriarMercadoPagoCardUseCase criarMercadoPagoCardUseCase;

    @Override
    public PagamentoContext execute(PagamentoContext pagamentoContext) {
        final var perfilPagamentoRequest = pagamentoContext.getPerfilPagamentoRequest();
        final var perfilCPagamento = perfilCPagamentoRepository.save(perfilPagamentoAdapter.adapt(perfilPagamentoRequest));
        pagamentoContext.setPerfilPagamento(perfilCPagamento);
        final var mercadoPagoCard = criarMercadoPagoCardUseCase.execute(pagamentoContext);
        pagamentoContext.setMercadoPagoCard(mercadoPagoCard);
        return pagamentoContext;
    }
}
