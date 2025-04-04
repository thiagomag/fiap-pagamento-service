package br.com.postechfiap.fiappagamentoservice.interfaces;

public interface UseCase<Input, Output> {

    Output execute(Input entry);
}
