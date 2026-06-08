package uts.edu.java.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uts.edu.java.repository.PedidoRepository;
import uts.edu.java.repository.ProductoRepository;
import uts.edu.java.repository.UsuarioRepository;
import uts.edu.java.service.UsuarioService;

import java.security.Principal;

@Controller
public class AuthController {

    private final UsuarioService usuarioService;
    private final ProductoRepository productoRepository;
    private final UsuarioRepository usuarioRepository;
    private final PedidoRepository pedidoRepository;
    private final AuthenticationManager authenticationManager;

    public AuthController(UsuarioService usuarioService,
                          ProductoRepository productoRepository,
                          UsuarioRepository usuarioRepository,
                          PedidoRepository pedidoRepository,
                          AuthenticationManager authenticationManager) {
        this.usuarioService = usuarioService;
        this.productoRepository = productoRepository;
        this.usuarioRepository = usuarioRepository;
        this.pedidoRepository = pedidoRepository;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/")
    public String root() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registro")
    public String registroForm() {
        return "registro";
    }

    @PostMapping("/registro-guardar")
    public String registrar(@RequestParam String nombre,
                            @RequestParam String email,
                            @RequestParam String password,
                            HttpServletRequest request) {
        try {
            usuarioService.registrarUsuario(nombre, email, password, "Cliente");

            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password));
            SecurityContext sc = SecurityContextHolder.getContext();
            sc.setAuthentication(auth);
            request.getSession(true).setAttribute(
                    HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, sc);

            return "redirect:/cliente/dashboard";
        } catch (Exception e) {
            return "redirect:/registro?error=" + e.getMessage();
        }
    }

    @GetMapping("/dashboard")
    public String dashboard(Principal principal, Model model) {
        model.addAttribute("usuario", principal.getName());
        model.addAttribute("totalProductos", productoRepository.count());
        model.addAttribute("totalClientes", usuarioRepository.countByRolNombre("Cliente"));
        model.addAttribute("totalPedidos", pedidoRepository.count());
        model.addAttribute("stockBajo", productoRepository.countByStockLessThanEqual(5));
        return "dashboard";
    }
}
