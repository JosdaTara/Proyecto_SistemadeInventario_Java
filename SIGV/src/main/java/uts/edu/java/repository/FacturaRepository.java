package uts.edu.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uts.edu.java.model.Factura;
import uts.edu.java.model.Pedido;
import java.util.List;
import java.util.Optional;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Integer> {
    Optional<Factura> findByPedido(Pedido pedido);
    boolean existsByPedido(Pedido pedido);
    List<Factura> findAllByOrderByFechaEmisionDesc();
}
