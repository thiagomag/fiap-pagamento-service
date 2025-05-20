package br.com.postechfiap.fiappagamentoservice.usecases.mercadopago;

import br.com.postechfiap.fiappagamentoservice.client.mercadopago.MercadoPagoClient;
import br.com.postechfiap.fiappagamentoservice.client.mercadopago.dto.response.MercadoPagoCardResponse;
import br.com.postechfiap.fiappagamentoservice.entities.MercadoPagoCard;
import br.com.postechfiap.fiappagamentoservice.entities.MercadoPagoCustomer;
import br.com.postechfiap.fiappagamentoservice.exception.EntityNotFoundException;
import br.com.postechfiap.fiappagamentoservice.interfaces.repository.MercadoPagoCardRepository;
import br.com.postechfiap.fiappagamentoservice.interfaces.usecases.DeletarMercadoPagoCardUseCase;
import br.com.postechfiap.fiappagamentoservice.usecase.mercadoPago.DeletarMercadoPagoCardUseCaseImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeletarMercadoPagoCardUseCaseImplTest {

    @Mock
    private MercadoPagoCardRepository mercadoPagoCardRepository;
    @Mock
    private MercadoPagoClient mercadoPagoClient;

    private DeletarMercadoPagoCardUseCase deletarMercadoPagoCardUseCase;

    @BeforeEach
    public void setUp() {
        deletarMercadoPagoCardUseCase = new DeletarMercadoPagoCardUseCaseImpl(mercadoPagoCardRepository,
                mercadoPagoClient);
    }

    @Test
    public void deletarCartaoDeveRetornarSucesso() {
        //given
        final var perfilPagamentoId = 1L;
        final var mercadoPagoCard = new MercadoPagoCard();
        final var mercadoPagoCustomer = new MercadoPagoCustomer();
        mercadoPagoCustomer.setMercadoPagoCustomerId("123");
        mercadoPagoCard.setMercadoPagoCustomer(mercadoPagoCustomer);
        mercadoPagoCard.setMercadoPagoCardId(perfilPagamentoId);
        final var mercadoPagoCardResponse = mock(MercadoPagoCardResponse.class);

        when(mercadoPagoCardRepository.findByPerfilPagamento_Id(perfilPagamentoId))
                .thenReturn(Optional.of(mercadoPagoCard));
        when(mercadoPagoClient.deleteCard(anyString(), anyString()))
                .thenReturn(mercadoPagoCardResponse);
        when(mercadoPagoCardRepository.save(any(MercadoPagoCard.class)))
                .thenReturn(mercadoPagoCard);

        //when
        final var actual = deletarMercadoPagoCardUseCase.execute(perfilPagamentoId);

        //then
        assertNotNull(actual);
        assertNotNull(actual.getDeletedTmsp());
    }

    @Test
    public void deletarCartaoDeveRetornarFalha() {
        //given
        final var perfilPagamentoId = 1L;

        when(mercadoPagoCardRepository.findByPerfilPagamento_Id(perfilPagamentoId))
                .thenReturn(Optional.empty());

        //when
        //then
        assertThatThrownBy(() -> deletarMercadoPagoCardUseCase.execute(perfilPagamentoId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("mercadoPagoCard não encontrado1.");

        verifyNoInteractions(mercadoPagoClient);
        verify(mercadoPagoCardRepository, times(0)).save(any(MercadoPagoCard.class));
    }
}
