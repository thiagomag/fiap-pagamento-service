package br.com.postechfiap.fiappagamentoservice.interfaces;

public interface CustomAdapter<Source, Destination> {

    Destination adapt(Source source);

    default Destination adapt(Source source, Object... args) {
        return adapt(source);
    }

}
