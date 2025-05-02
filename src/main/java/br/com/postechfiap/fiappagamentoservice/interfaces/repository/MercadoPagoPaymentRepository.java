package br.com.postechfiap.fiappagamentoservice.interfaces.repository;

import br.com.postechfiap.fiappagamentoservice.entities.MercadoPagoPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MercadoPagoPaymentRepository extends JpaRepository<MercadoPagoPayment, Long> {
}
