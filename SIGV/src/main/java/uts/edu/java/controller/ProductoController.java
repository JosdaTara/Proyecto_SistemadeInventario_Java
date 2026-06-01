package uts.edu.java.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uts.edu.java.model.Producto;
import uts.edu.java.repository.ProductoRepository;

import java.util.Optional;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoRepository productoRepository;

    public ProductoController(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("productos", productoRepository.findAllByOrderByNombreAsc());
        return "productos/listar";
    }

    @GetMapping("/nuevo")
    public String nuevoForm(Model model) {
        model.addAttribute("producto", new Producto());
        return "productos/form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Producto producto, RedirectAttributes ra) {
        productoRepository.save(producto);
        ra.addFlashAttribute("success", "Producto guardado exitosamente");
        return "redirect:/productos";
    }

    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Integer id, Model model) {
        Optional<Producto> opt = productoRepository.findById(id);
        if (opt.isEmpty()) return "redirect:/productos?error=no-encontrado";
        model.addAttribute("producto", opt.get());
        return "productos/form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id, RedirectAttributes ra) {
        productoRepository.deleteById(id);
        ra.addFlashAttribute("success", "Producto eliminado");
        return "redirect:/productos";
    }
}
