package uts.edu.java.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uts.edu.java.model.Pedido;
import uts.edu.java.model.Usuario;
import uts.edu.java.repository.PedidoRepository;
import uts.edu.java.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/clientes")
public class ClienteAdminController {

    private final UsuarioRepository usuarioRepository;
    private final PedidoRepository pedidoRepository;

    public ClienteAdminController(UsuarioRepository usuarioRepository,
                                  PedidoRepository pedidoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.pedidoRepository = pedidoRepository;
    }

    @GetMapping
    public String listar(Model model) {
        List<Usuario> clientes = usuarioRepository.findByRolNombreOrderByNombreAsc("Cliente");
        model.addAttribute("clientes", clientes);
        return "admin/clientes";
    }

    @GetMapping("/{id}")
    public String detalle(@PathVariable Integer id, Model model) {
        Optional<Usuario> opt = usuarioRepository.findById(id);
        if (opt.isEmpty()) return "redirect:/admin/clientes";
        Usuario cliente = opt.get();
        List<Pedido> pedidos = pedidoRepository.findByUsuarioOrderByFechaPedidoDesc(cliente);
        model.addAttribute("cliente", cliente);
        model.addAttribute("pedidos", pedidos);
        return "admin/cliente-detalle";
    }
}
