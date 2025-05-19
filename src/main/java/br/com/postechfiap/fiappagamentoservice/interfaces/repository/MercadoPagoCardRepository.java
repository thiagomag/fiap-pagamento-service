package br.com.postechfiap.fiappagamentoservice.interfaces.repository;

import br.com.postechfiap.fiappagamentoservice.entities.MercadoPagoCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MercadoPagoCardRepository extends JpaRepository<MercadoPagoCard, Long> {

    Optional<MercadoPagoCard> findByPerfilPagamento_Id(Long perfilPagamentoId);
}
