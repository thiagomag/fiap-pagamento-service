package br.com.postechfiap.fiappagamentoservice.client.clienteService.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(callSuper=false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClienteResponse {

    private Long id;
    private String nome;
    private String sobrenome;
    private String email;
    private String telefone;
    private String cpf;
    private String dataNascimento;
    private EnderecoResponse endereco;


}
