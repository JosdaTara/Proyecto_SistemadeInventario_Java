package uts.edu.java.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uts.edu.java.model.Pedido;
import uts.edu.java.model.Usuario;
import uts.edu.java.repository.PedidoRepository;
import uts.edu.java.repository.UsuarioRepository;
import uts.edu.java.service.UsuarioService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/clientes")
public class ClienteAdminController {

    private final UsuarioRepository usuarioRepository;
    private final PedidoRepository pedidoRepository;
    private final UsuarioService usuarioService;

    public ClienteAdminController(UsuarioRepository usuarioRepository,
                                  PedidoRepository pedidoRepository,
                                  UsuarioService usuarioService) {
        this.usuarioRepository = usuarioRepository;
        this.pedidoRepository = pedidoRepository;
        this.usuarioService = usuarioService;
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

    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Integer id, Model model) {
        Optional<Usuario> opt = usuarioRepository.findById(id);
        if (opt.isEmpty()) return "redirect:/admin/clientes";
        model.addAttribute("cliente", opt.get());
        return "admin/cliente-form";
    }

    @PostMapping("/guardar")
    public String guardar(@RequestParam Integer id,
                          @RequestParam String nombre,
                          @RequestParam String email,
                          @RequestParam(required = false) String password) {
        try {
            usuarioService.actualizarUsuario(id, nombre, email, password, "Cliente");
            return "redirect:/admin/clientes?updated";
        } catch (Exception e) {
            return "redirect:/admin/clientes/editar/" + id + "?error=" + e.getMessage();
        }
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id) {
        usuarioService.eliminarUsuario(id);
        return "redirect:/admin/clientes?deleted";
    }
}
