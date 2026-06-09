package uts.edu.java.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uts.edu.java.model.Pedido;
import uts.edu.java.repository.PedidoRepository;

import java.util.List;
import java.util.Optional;



@Controller
@RequestMapping("/admin/pedidos")
public class PedidoAdminController {

    private final PedidoRepository pedidoRepository;

    public PedidoAdminController(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    @GetMapping
    public String listar(Model model) {
        List<Pedido> pedidos = pedidoRepository.findAllByOrderByFechaPedidoDesc();
        model.addAttribute("pedidos", pedidos);
        return "admin/pedidos";
    }

    @GetMapping("/{id}")
    public String detalle(@PathVariable Integer id, Model model) {
        Optional<Pedido> opt = pedidoRepository.findById(id);
        if (opt.isEmpty()) return "redirect:/admin/pedidos";
        model.addAttribute("pedido", opt.get());
        return "admin/pedido-detalle";
    }

    @PostMapping("/{id}/estado")
    public String cambiarEstado(@PathVariable Integer id,
                                @RequestParam String estado,
                                RedirectAttributes ra) {
        Optional<Pedido> opt = pedidoRepository.findById(id);
        if (opt.isEmpty()) return "redirect:/admin/pedidos";
        Pedido pedido = opt.get();
        pedido.setEstado(estado);
        pedidoRepository.save(pedido);
        ra.addFlashAttribute("success", "Pedido #" + id + " actualizado a " + estado);
        return "redirect:/admin/pedidos/{id}";
    }

}
