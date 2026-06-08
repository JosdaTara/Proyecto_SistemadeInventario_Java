package uts.edu.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uts.edu.java.model.Pedido;
import uts.edu.java.model.Usuario;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    List<Pedido> findByUsuarioOrderByFechaPedidoDesc(Usuario usuario);
    List<Pedido> findAllByOrderByFechaPedidoDesc();
    long countByEstado(String estado);
    long countByUsuario(Usuario usuario);
    long countByUsuarioAndEstado(Usuario usuario, String estado);
}
