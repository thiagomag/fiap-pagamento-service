package br.com.postechfiap.fiappagamentoservice.adapter;

import br.com.postechfiap.fiappagamentoservice.client.mercadopago.dto.response.MercadoPagoCustomerResponse;
import br.com.postechfiap.fiappagamentoservice.client.mercadopago.dto.response.MercadoPagoIdentificationResponse;
import br.com.postechfiap.fiappagamentoservice.entities.MercadoPagoCustomer;
import br.com.postechfiap.fiappagamentoservice.utils.JsonUtils;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MercadoPagoCustomerAdapter extends AbstractAdapter<MercadoPagoCustomerResponse, MercadoPagoCustomer> {

    public MercadoPagoCustomerAdapter(JsonUtils jsonUtils) {
        super(MercadoPagoCustomer.class, jsonUtils);
    }

    @Override
    protected ModelMapper getModelMapper() {
        if (this.modelMapper == null) {
            this.modelMapper = super.getModelMapper();

            this.modelMapper.typeMap(MercadoPagoCustomerResponse.class, MercadoPagoCustomer.class)
                    .addMappings(mapping -> {
                        mapping.skip(MercadoPagoCustomerResponse::getId, MercadoPagoCustomer::setId);
                        mapping.map(MercadoPagoCustomerResponse::getId, MercadoPagoCustomer::setMercadoPagoCustomerId);
                        mapping.map(MercadoPagoCustomerResponse::getFirstName, MercadoPagoCustomer::setFirstName);
                        mapping.map(MercadoPagoCustomerResponse::getLastName, MercadoPagoCustomer::setLastName);
                        mapping.map(MercadoPagoCustomerResponse::getEmail, MercadoPagoCustomer::setEmail);
                        mapping.using(toIdentificationNumber()).map(MercadoPagoCustomerResponse::getIdentification, MercadoPagoCustomer::setIdentificationNumber);
                    });
        }

        return this.modelMapper;
    }

    private Converter<MercadoPagoIdentificationResponse,String> toIdentificationNumber() {
        return context -> {
            final var source = context.getSource();
            return Optional.ofNullable(source)
                    .map(MercadoPagoIdentificationResponse::getNumber)
                    .orElse(null);
        };
    }
}
