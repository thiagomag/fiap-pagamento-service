package br.com.postechfiap.fiappagamentoservice.usecase.mercadoPago;

import br.com.postechfiap.fiappagamentoservice.client.mercadopago.MercadoPagoClient;
import br.com.postechfiap.fiappagamentoservice.entities.MercadoPagoCard;
import br.com.postechfiap.fiappagamentoservice.exception.EntityNotFoundException;
import br.com.postechfiap.fiappagamentoservice.interfaces.repository.MercadoPagoCardRepository;
import br.com.postechfiap.fiappagamentoservice.interfaces.usecases.DeletarMercadoPagoCardUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeletarMercadoPagoCardUseCaseImpl implements DeletarMercadoPagoCardUseCase {

    private final MercadoPagoCardRepository mercadoPagoCardRepository;
    private final MercadoPagoClient mercadoPagoClient;

    @Override
    public MercadoPagoCard execute(Long perfilPagamentoId) {
        final var mercardoPagoCard = mercadoPagoCardRepository.findByPerfilPagamento_Id(perfilPagamentoId)
                .orElseThrow(() -> new EntityNotFoundException("mercadoPagoCard", perfilPagamentoId.toString()));
        mercadoPagoClient.deleteCard(mercardoPagoCard.getMercadoPagoCustomer().getMercadoPagoCustomerId(), mercardoPagoCard.getMercadoPagoCardId().toString());
        mercardoPagoCard.delete();
        return mercadoPagoCardRepository.save(mercardoPagoCard);
    }
}
