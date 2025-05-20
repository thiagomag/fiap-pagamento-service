package br.com.postechfiap.fiappagamentoservice.exception;


import br.com.postechfiap.fiappagamentoservice.dto.ResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ResponseError> handleEntityNotFoundException(EntityNotFoundException ex) {
        ResponseError errorResponse = new ResponseError(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseError> handleIllegalArgumentException(IllegalArgumentException ex) {
        ResponseError errorResponse = new ResponseError(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseError> handleGenericException(Exception ex) {
        ResponseError errorResponse = new ResponseError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                List.of(ex.getMessage() != null ? ex.getMessage() : "Erro desconhecido.")
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(PagamentoRejeitadoException.class)
    public ResponseEntity<ResponseError> handlePagamentoRejeitadoException(PagamentoRejeitadoException ex) {
        ResponseError errorResponse = new ResponseError(
                HttpStatus.BAD_REQUEST.value(),
                "Pagamento Rejeitado",
                List.of(ex.getMessage())
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
