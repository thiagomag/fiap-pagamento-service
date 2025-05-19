package br.com.postechfiap.fiappagamentoservice.adapter;

import br.com.postechfiap.fiappagamentoservice.controller.dto.response.PerfilPagamentoResponse;
import br.com.postechfiap.fiappagamentoservice.entities.PerfilPagamento;
import br.com.postechfiap.fiappagamentoservice.utils.JsonUtils;
import org.springframework.stereotype.Component;

@Component
public class PerfilPagamentoResponseAdapter extends AbstractAdapter<PerfilPagamento, PerfilPagamentoResponse> {

    public PerfilPagamentoResponseAdapter(JsonUtils jsonUtils) {
        super(PerfilPagamentoResponse.class, jsonUtils);
    }
}
