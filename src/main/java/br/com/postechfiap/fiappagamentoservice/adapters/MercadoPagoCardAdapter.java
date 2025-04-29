package br.com.postechfiap.fiappagamentoservice.adapters;

import br.com.postechfiap.fiappagamentoservice.client.mercadopago.dto.response.MercadoPagoCardHolderResponse;
import br.com.postechfiap.fiappagamentoservice.client.mercadopago.dto.response.MercadoPagoCardResponse;
import br.com.postechfiap.fiappagamentoservice.client.mercadopago.dto.response.MercadoPagoPaymentMethodResponse;
import br.com.postechfiap.fiappagamentoservice.entities.MercadoPagoCard;
import br.com.postechfiap.fiappagamentoservice.utils.JsonUtils;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MercadoPagoCardAdapter extends AbstractAdapter<MercadoPagoCardResponse, MercadoPagoCard> {

    public MercadoPagoCardAdapter(JsonUtils jsonUtils) {
        super(MercadoPagoCard.class, jsonUtils);
    }

    @Override
    protected ModelMapper getModelMapper() {
        if (this.modelMapper == null) {
            this.modelMapper = super.getModelMapper();

            this.modelMapper.typeMap(MercadoPagoCardResponse.class, MercadoPagoCard.class)
                    .addMappings(mapping -> {
                        mapping.map(MercadoPagoCardResponse::getId, MercadoPagoCard::setId);
                        mapping.map(MercadoPagoCardResponse::getLastFourDigits, MercadoPagoCard::setLastFourDigits);
                        mapping.map(MercadoPagoCardResponse::getExpirationMonth, MercadoPagoCard::setExpirationMonth);
                        mapping.map(MercadoPagoCardResponse::getExpirationYear, MercadoPagoCard::setExpirationYear);
                        mapping.using(toCardHolderName()).map(MercadoPagoCardResponse::getCardholder, MercadoPagoCard::setCardholderName);
                        mapping.using(toMercadoPagoPaymentMethod()).map(MercadoPagoCardResponse::getPaymentMethod, MercadoPagoCard::setMercadoPagoPaymentMethod);
                    });
        }

        return this.modelMapper;
    }

    private Converter<MercadoPagoCardHolderResponse,String> toCardHolderName() {
        return context -> {
            final var source = context.getSource();
            return Optional.ofNullable(source)
                    .map(MercadoPagoCardHolderResponse::getName)
                    .orElse(null);
        };
    }


    private Converter<MercadoPagoPaymentMethodResponse,String> toMercadoPagoPaymentMethod() {
        return context -> {
            final var source = context.getSource();
            return Optional.ofNullable(source)
                    .map(MercadoPagoPaymentMethodResponse::getName)
                    .orElse(null);
        };
    }
}
