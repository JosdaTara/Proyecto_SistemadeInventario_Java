package uts.edu.java.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uts.edu.java.repository.PedidoRepository;
import uts.edu.java.repository.ProductoRepository;
import uts.edu.java.repository.UsuarioRepository;

import java.util.List;

@Controller
@RequestMapping("/admin/reportes")
public class ReporteController {

    private final PedidoRepository pedidoRepository;
    private final ProductoRepository productoRepository;

    public ReporteController(PedidoRepository pedidoRepository,
                             ProductoRepository productoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.productoRepository = productoRepository;
    }

    @GetMapping
    public String reportes(Model model) {
        long totalPedidos = pedidoRepository.count();
        long enviados = pedidoRepository.countByEstado("ENVIADO");
        long entregados = pedidoRepository.countByEstado("ENTREGADO");
        long cancelados = pedidoRepository.countByEstado("CANCELADO");
        long stockBajo = productoRepository.countByStockLessThanEqual(5);
        long totalProductos = productoRepository.count();

        model.addAttribute("totalPedidos", totalPedidos);
        model.addAttribute("enviados", enviados);
        model.addAttribute("entregados", entregados);
        model.addAttribute("cancelados", cancelados);
        model.addAttribute("stockBajo", stockBajo);
        model.addAttribute("totalProductos", totalProductos);
        model.addAttribute("productos", productoRepository.findByActivoTrue());
        return "admin/reportes";
    }
}
