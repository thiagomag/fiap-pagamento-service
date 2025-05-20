package br.com.postechfiap.fiappagamentoservice.controller;

import br.com.postechfiap.fiappagamentoservice.controller.dto.request.PerfilPagamentoRequest;
import br.com.postechfiap.fiappagamentoservice.controller.dto.response.PerfilPagamentoResponse;
import br.com.postechfiap.fiappagamentoservice.interfaces.usecases.AdicionarCartaoUseCase;
import br.com.postechfiap.fiappagamentoservice.interfaces.usecases.DeletarCartaoClienteUseCase;
import br.com.postechfiap.fiappagamentoservice.interfaces.usecases.ListarCartoesClienteUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cartao")
@RequiredArgsConstructor
@Tag(name = "Cartão", description = "API de Cartões")
public class CartaoController {

    private final AdicionarCartaoUseCase adicionarCartaoUseCase;
    private final ListarCartoesClienteUseCase listarCartoesClienteUseCase;
    private final DeletarCartaoClienteUseCase deletarCartaoClienteUseCase;

    @PostMapping("/adicionar/{clienteId}")
    public ResponseEntity<PerfilPagamentoResponse> adicionarCartao(@PathVariable Long clienteId,
                                                                   @RequestBody PerfilPagamentoRequest cartaoRequest) {
        cartaoRequest.setIdCliente(clienteId);
        return ResponseEntity.status(HttpStatus.CREATED).body(adicionarCartaoUseCase.execute(cartaoRequest));
    }

    @GetMapping("/listar/{clienteId}")
    public ResponseEntity<List<PerfilPagamentoResponse>> listarCartoes(@PathVariable Long clienteId) {
        return ResponseEntity.ok(listarCartoesClienteUseCase.execute(clienteId));
    }

    @DeleteMapping("/deletar/{perfilPagamentoId}")
    public ResponseEntity<Void> deletarCartao(@PathVariable Long perfilPagamentoId) {
        deletarCartaoClienteUseCase.execute(perfilPagamentoId);
        return ResponseEntity.noContent().build();
    }


}
