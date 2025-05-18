package br.com.postechfiap.fiappagamentoservice.exception;

public class PagamentoRejeitadoException extends RuntimeException {
    public PagamentoRejeitadoException(String message) {
        super(message);
    }
}
