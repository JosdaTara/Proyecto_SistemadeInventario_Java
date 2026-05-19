package uts.edu.java.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uts.edu.java.model.Usuario;
import uts.edu.java.repository.ProductoRepository;
import uts.edu.java.repository.UsuarioRepository;

import java.security.Principal;

@Controller
@RequestMapping("/cliente")
public class ClienteController {

    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;

    public ClienteController(UsuarioRepository usuarioRepository,
                             ProductoRepository productoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.productoRepository = productoRepository;
    }

    @GetMapping("/dashboard")
    public String dashboard(Principal principal, Model model) {
        Usuario usuario = usuarioRepository.findByEmail(principal.getName()).orElseThrow();
        model.addAttribute("usuario", usuario.getNombre());
        model.addAttribute("totalProductos", productoRepository.count());
        model.addAttribute("productos", productoRepository.findByActivoTrue());
        return "cliente/dashboard";
    }

    @GetMapping("/productos")
    public String productos(Principal principal, Model model) {
        model.addAttribute("usuario", principal.getName());
        model.addAttribute("productos", productoRepository.findByActivoTrue());
        return "cliente/productos";
    }

    @GetMapping("/pedidos")
    public String pedidos(Principal principal, Model model) {
        model.addAttribute("usuario", principal.getName());
        return "cliente/pedidos";
    }

    @GetMapping("/perfil")
    public String perfil(Principal principal, Model model) {
        Usuario usuario = usuarioRepository.findByEmail(principal.getName()).orElseThrow();
        model.addAttribute("nombre", usuario.getNombre());
        model.addAttribute("email", usuario.getEmail());
        model.addAttribute("fechaRegistro", usuario.getFechaRegistro());
        return "cliente/perfil";
    }
}
