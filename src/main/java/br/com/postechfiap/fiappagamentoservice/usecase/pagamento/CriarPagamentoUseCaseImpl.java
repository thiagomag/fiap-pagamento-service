package br.com.postechfiap.fiappagamentoservice.usecase.pagamento;

import br.com.postechfiap.fiappagamentoservice.client.clienteService.ClienteServiceClient;
import br.com.postechfiap.fiappagamentoservice.client.clienteService.dto.ClienteResponse;
import br.com.postechfiap.fiappagamentoservice.controller.dto.request.PagamentoRequest;
import br.com.postechfiap.fiappagamentoservice.controller.dto.response.PagamentoResponse;
import br.com.postechfiap.fiappagamentoservice.entities.MercadoPagoCustomer;
import br.com.postechfiap.fiappagamentoservice.entities.Pagamento;
import br.com.postechfiap.fiappagamentoservice.enuns.MetodoPagamentoEnum;
import br.com.postechfiap.fiappagamentoservice.enuns.StatusPagamentoEnum;
import br.com.postechfiap.fiappagamentoservice.interfaces.repository.PagamentoRepository;
import br.com.postechfiap.fiappagamentoservice.interfaces.usecases.*;
import br.com.postechfiap.fiappagamentoservice.usecase.mercadoPago.dto.PagamentoContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CriarPagamentoUseCaseImpl implements CriarPagamentoUseCase {

    private final CriarOuAtualizarClienteUseCase criarOuAtualizarClienteUseCase;
    private final CriarPerfilPagamentoUseCase criarPerfilPagamentoUseCase;
    private final MercadoPagoCreatePaymentUseCase mercadoPagoCreatePaymentUseCase;
    private final AtualizarPagamentoUseCase atualizarPagamentoUseCase;
    private final ClienteServiceClient clienteServiceClient;
    private final PagamentoRepository pagamentoRepository;

    @Override
    public PagamentoResponse execute(PagamentoRequest pagamentoRequest) {
        final var customerResponse = clienteServiceClient.getCliente(pagamentoRequest.getIdCliente());
        final var mercadoPagoCustomer = criarOuAtualizarClienteUseCase.execute(customerResponse);
        var pagamentoContext = criarPerfilPagamentoUseCase.execute(buildaPagamentoContext(pagamentoRequest, customerResponse, mercadoPagoCustomer));
        final var pagamento = criarESalvarPagamento(pagamentoContext);
        pagamentoContext.setPagamento(pagamento);
        pagamentoContext = mercadoPagoCreatePaymentUseCase.execute(pagamentoContext);
        return atualizarPagamentoUseCase.execute(pagamentoContext);
    }

    private PagamentoContext buildaPagamentoContext(PagamentoRequest pagamentoRequest, ClienteResponse customerResponse, MercadoPagoCustomer mercadoPagoCustomer) {
        return PagamentoContext.builder()
                .clienteResponse(customerResponse)
                .mercadoPagoCustomer(mercadoPagoCustomer)
                .perfilPagamentoRequest(pagamentoRequest.getPerfilPagamento())
                .pagamentoRequest(pagamentoRequest)
                .build();
    }

    private Pagamento criarESalvarPagamento(PagamentoContext pagamentoContext) {
        final var perfilPagamento = pagamentoContext.getPerfilPagamento();
        final var pagamentoRequest = pagamentoContext.getPagamentoRequest();
        final var pagamento = Pagamento.builder()
                .clienteId(pagamentoRequest.getIdCliente())
                .valor(pagamentoRequest.getValor())
                .status(StatusPagamentoEnum.PROCESSANDO)
                .parcelas(1)
                .pedidoId(pagamentoRequest.getIdPedido().toString())
                .metodoPagamento(MetodoPagamentoEnum.CARTAO_CREDITO)
                .perfilPagamento(perfilPagamento)
                .build();
        return pagamentoRepository.save(pagamento);
    }
}
