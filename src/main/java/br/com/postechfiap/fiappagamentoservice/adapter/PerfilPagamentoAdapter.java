package br.com.postechfiap.fiappagamentoservice.adapter;

import br.com.postechfiap.fiappagamentoservice.controller.dto.request.PerfilPagamentoRequest;
import br.com.postechfiap.fiappagamentoservice.entities.PerfilPagamento;
import br.com.postechfiap.fiappagamentoservice.enuns.StatusBasicoEnum;
import br.com.postechfiap.fiappagamentoservice.utils.JsonUtils;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PerfilPagamentoAdapter extends AbstractAdapter<PerfilPagamentoRequest, PerfilPagamento> {

    public PerfilPagamentoAdapter(JsonUtils jsonUtils) {
        super(PerfilPagamento.class, jsonUtils);
    }

    @Override
    protected ModelMapper getModelMapper() {
        if (this.modelMapper == null) {
            this.modelMapper = super.getModelMapper();

            this.modelMapper.typeMap(PerfilPagamentoRequest.class, PerfilPagamento.class)
                    .addMappings(mapping -> {
                        mapping.map(PerfilPagamentoRequest::getDataValidade, PerfilPagamento::setDataValidade);
                        mapping.map(PerfilPagamentoRequest::getNomeTitularCartao, PerfilPagamento::setNomeTitularCartao);
                        mapping.using(toPrimeirosNumero()).map(PerfilPagamentoRequest::getNumeroCartao, PerfilPagamento::setPrimeirosNumerosCartao);
                        mapping.using(toUltimosNumeros()).map(PerfilPagamentoRequest::getNumeroCartao, PerfilPagamento::setUltimosNumerosCartao);
                        mapping.map(perfilPagamentoRequest -> StatusBasicoEnum.ATIVO, PerfilPagamento::setStatus);
                    });
        }

        return this.modelMapper;
    }

    private Converter<String, String> toPrimeirosNumero() {
        return context -> {
            String numeroCartao = context.getSource();
            if (numeroCartao != null) {
                return numeroCartao.substring(0, 4);
            }
            return null;
        };
    }


    private Converter<String, String> toUltimosNumeros() {
        return context -> {
            String numeroCartao = context.getSource();
            if (numeroCartao != null) {
                return numeroCartao.substring(numeroCartao.length() - 4);
            }
            return null;
        };
    }
}
