package br.com.postechfiap.fiappagamentoservice.usecase.pagamento;

import br.com.postechfiap.fiappagamentoservice.adapter.PagamentoResponseCustomAdapter;
import br.com.postechfiap.fiappagamentoservice.controller.dto.response.PagamentoResponse;
import br.com.postechfiap.fiappagamentoservice.entities.Pagamento;
import br.com.postechfiap.fiappagamentoservice.enuns.StatusPagamentoEnum;
import br.com.postechfiap.fiappagamentoservice.interfaces.repository.PagamentoRepository;
import br.com.postechfiap.fiappagamentoservice.interfaces.usecases.AtualizarPagamentoUseCase;
import br.com.postechfiap.fiappagamentoservice.usecase.mercadoPago.dto.PagamentoContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AtualizarPagamentoUseCaseImpl implements AtualizarPagamentoUseCase {

    final private PagamentoRepository pagamentoRepository;
    final private PagamentoResponseCustomAdapter pagamentoResponseCustomAdapter;


    @Override
    public PagamentoResponse execute(PagamentoContext pagamentoContext) {
        final var pagamento = atualizarPagamento(pagamentoContext);
        pagamentoContext.setPagamento(pagamento);
        return pagamentoResponseCustomAdapter.adapt(pagamentoContext);
    }

    private Pagamento atualizarPagamento(PagamentoContext pagamentoContext) {
        final var pagamento = pagamentoContext.getPagamento();
        final var mercadoPagoPayment = pagamentoContext.getMercadoPagoPayment();
        if ("rejected".equalsIgnoreCase(mercadoPagoPayment.getStatus())) {
            pagamento.setStatus(StatusPagamentoEnum.FALHA);
        } else if ("approved".equalsIgnoreCase(mercadoPagoPayment.getStatus())) {
            pagamento.setStatus(StatusPagamentoEnum.SUCESSO);
        }
        pagamento.setMercadoPagoPayment(mercadoPagoPayment);
        pagamento.setAuthorizedAt(mercadoPagoPayment.getAuthorizedAt());
        pagamento.setCapturedAt(mercadoPagoPayment.getCapturedAt());
        return pagamentoRepository.save(pagamento);
    }
}
