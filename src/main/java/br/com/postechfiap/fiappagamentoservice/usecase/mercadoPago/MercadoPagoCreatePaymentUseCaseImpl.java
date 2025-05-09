package br.com.postechfiap.fiappagamentoservice.usecase.mercadoPago;

import br.com.postechfiap.fiappagamentoservice.adapters.MercadoPagoCreatePaymentRequestCustomAdapter;
import br.com.postechfiap.fiappagamentoservice.adapters.MercadoPagoPaymentCustomAdapter;
import br.com.postechfiap.fiappagamentoservice.client.mercadopago.MercadoPagoClient;
import br.com.postechfiap.fiappagamentoservice.interfaces.repository.MercadoPagoPaymentRepository;
import br.com.postechfiap.fiappagamentoservice.interfaces.usecases.MercadoPagoCreatePaymentUseCase;
import br.com.postechfiap.fiappagamentoservice.usecase.mercadoPago.dto.PagamentoContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MercadoPagoCreatePaymentUseCaseImpl implements MercadoPagoCreatePaymentUseCase {

    private final MercadoPagoCreatePaymentRequestCustomAdapter mercadoPagoCreatePaymentRequestCustomAdapter;
    private final MercadoPagoPaymentCustomAdapter mercadoPagoPaymentAdapter;
    private final MercadoPagoPaymentRepository mercadoPagoPaymentRepository;
    private final MercadoPagoClient mercadoPagoClient;

    @Override
    public PagamentoContext execute(PagamentoContext pagamentoContext) {
        final var mercadoPagoPaymentRequest = mercadoPagoCreatePaymentRequestCustomAdapter.adapt(pagamentoContext);
        final var mercadoPagoPaymentResponse = mercadoPagoClient.createPayment(mercadoPagoPaymentRequest);
        final var mercadoPagoPayment = mercadoPagoPaymentRepository.save(mercadoPagoPaymentAdapter.adapt(mercadoPagoPaymentResponse, pagamentoContext));
        pagamentoContext.setMercadoPagoPayment(mercadoPagoPayment);
        return pagamentoContext;
    }
}
