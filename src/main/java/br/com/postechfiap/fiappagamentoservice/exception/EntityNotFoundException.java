package br.com.postechfiap.fiappagamentoservice.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String entityName, String genero) {
        super(entityName + " não encontrad" + genero + ".");
    }
}
