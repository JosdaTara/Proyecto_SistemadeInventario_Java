package uts.edu.java.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uts.edu.java.model.Factura;
import uts.edu.java.model.Pedido;
import uts.edu.java.model.Usuario;
import uts.edu.java.repository.FacturaRepository;
import uts.edu.java.repository.PedidoRepository;
import uts.edu.java.repository.ProductoRepository;
import uts.edu.java.repository.UsuarioRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/facturas")
public class FacturaAdminController {

    private final FacturaRepository facturaRepository;
    private final PedidoRepository pedidoRepository;

    public FacturaAdminController(FacturaRepository facturaRepository,
                                  PedidoRepository pedidoRepository) {
        this.facturaRepository = facturaRepository;
        this.pedidoRepository = pedidoRepository;
    }

    @GetMapping
    public String listar(Model model) {
        List<Factura> facturas = facturaRepository.findAllByOrderByFechaEmisionDesc();
        model.addAttribute("facturas", facturas);
        return "admin/facturas";
    }

    @GetMapping("/{id}")
    public String detalle(@PathVariable Integer id, Model model) {
        Optional<Factura> opt = facturaRepository.findById(id);
        if (opt.isEmpty()) return "redirect:/admin/facturas";
        model.addAttribute("factura", opt.get());
        return "admin/factura-detalle";
    }
}
