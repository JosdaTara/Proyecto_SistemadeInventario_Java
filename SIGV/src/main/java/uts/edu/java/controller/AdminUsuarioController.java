package uts.edu.java.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uts.edu.java.model.Usuario;
import uts.edu.java.repository.UsuarioRepository;
import uts.edu.java.service.UsuarioService;

import java.util.Optional;

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

    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Integer id, Model model) {
        Optional<Usuario> opt = usuarioRepository.findById(id);
        if (opt.isEmpty()) {
            return "redirect:/admin/usuarios";
        }
        model.addAttribute("usuario", opt.get());
        return "admin/usuarios/form";
    }

    @PostMapping("/guardar")
    public String guardar(@RequestParam(required = false) Integer id,
                          @RequestParam String nombre,
                          @RequestParam String email,
                          @RequestParam(required = false) String password,
                          @RequestParam String rol) {
        try {
            if (id != null) {
                usuarioService.actualizarUsuario(id, nombre, email, password, rol);
                return "redirect:/admin/usuarios?updated";
            } else {
                usuarioService.registrarUsuario(nombre, email, password, rol);
                return "redirect:/admin/usuarios?success";
            }
        } catch (Exception e) {
            String redirect = (id != null) ? "/admin/usuarios/editar/" + id : "/admin/usuarios/nuevo";
            return "redirect:" + redirect + "?error=" + e.getMessage();
        }
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id) {
        usuarioService.eliminarUsuario(id);
        return "redirect:/admin/usuarios?deleted";
    }
}
