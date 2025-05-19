package br.com.postechfiap.fiappagamentoservice.interfaces.repository;

import br.com.postechfiap.fiappagamentoservice.entities.PerfilPagamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PerfilPagamentoRepository extends JpaRepository<PerfilPagamento, Long> {

    List<PerfilPagamento> findPerfilPagamentoByClienteId(Long clienteId);

}
