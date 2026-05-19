package uts.edu.java.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uts.edu.java.service.UsuarioService;

import java.security.Principal;

@Controller
public class AuthController {

    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
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
                            @RequestParam(defaultValue = "Cliente") String rol) {
        try {
            usuarioService.registrarUsuario(nombre, email, password, rol);
            return "redirect:/login?registro=exitoso";
        } catch (Exception e) {
            return "redirect:/registro?error=" + e.getMessage();
        }
    }

    @GetMapping("/dashboard")
    public String dashboard(Principal principal, Model model) {
        model.addAttribute("usuario", principal.getName());
        return "dashboard";
    }
}
