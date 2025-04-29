package br.com.postechfiap.fiappagamentoservice.interfaces.repository;

import br.com.postechfiap.fiappagamentoservice.entities.MercadoPagoCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MercadoPagoCardRepository extends JpaRepository<MercadoPagoCard, Long> {
}
