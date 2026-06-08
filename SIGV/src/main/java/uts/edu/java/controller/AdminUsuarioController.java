package uts.edu.java.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uts.edu.java.repository.UsuarioRepository;
import uts.edu.java.service.UsuarioService;

@Controller
@RequestMapping("/admin/usuarios")
public class AdminUsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;

    public AdminUsuarioController(UsuarioService usuarioService,
                                  UsuarioRepository usuarioRepository) {
        this.usuarioService = usuarioService;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("usuarios", usuarioRepository.findAll());
        return "admin/usuarios/listar";
    }

    @GetMapping("/nuevo")
    public String nuevoForm() {
        return "admin/usuarios/form";
    }

    @PostMapping("/guardar")
    public String guardar(@RequestParam String nombre,
                          @RequestParam String email,
                          @RequestParam String password,
                          @RequestParam String rol) {
        try {
            usuarioService.registrarUsuario(nombre, email, password, rol);
            return "redirect:/admin/usuarios?success";
        } catch (Exception e) {
            return "redirect:/admin/usuarios/nuevo?error=" + e.getMessage();
        }
    }
}
