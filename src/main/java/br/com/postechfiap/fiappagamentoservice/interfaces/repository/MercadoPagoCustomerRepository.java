package br.com.postechfiap.fiappagamentoservice.interfaces.repository;

import br.com.postechfiap.fiappagamentoservice.entities.MercadoPagoCustomer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MercadoPagoCustomerRepository extends JpaRepository<MercadoPagoCustomer, String> {

    Optional<MercadoPagoCustomer> findByClienteId(final Long clienteId);
}
