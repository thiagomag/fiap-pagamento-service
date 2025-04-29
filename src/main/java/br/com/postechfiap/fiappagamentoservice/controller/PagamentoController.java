package br.com.postechfiap.fiappagamentoservice.controller;

import br.com.postechfiap.fiappagamentoservice.controller.dto.request.PagamentoRequest;
import br.com.postechfiap.fiappagamentoservice.controller.dto.response.PagamentoResponse;
import br.com.postechfiap.fiappagamentoservice.interfaces.usecases.CriarPagamentoUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pagamentos")
@RequiredArgsConstructor
@Tag(name = "Pagamento", description = "API de Pagamentos")
public class PagamentoController {

    private final CriarPagamentoUseCase criarPagamentoUseCase;

    @PostMapping("/compra")
    @Operation(summary = "Criar Pagamento", description = "Cria um novo pagamento")
    public PagamentoResponse criarPagamento(@Valid @RequestBody PagamentoRequest pagamentoRequest) {
        return criarPagamentoUseCase.execute(pagamentoRequest);
    }
}
