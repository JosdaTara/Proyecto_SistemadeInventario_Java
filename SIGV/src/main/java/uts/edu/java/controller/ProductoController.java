package uts.edu.java.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uts.edu.java.model.Producto;
import uts.edu.java.repository.ProductoRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoRepository productoRepository;
    private static final String UPLOAD_DIR = "uploads/productos/";

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
    public String guardar(@ModelAttribute Producto producto,
                          @RequestParam("imagenFile") MultipartFile imagenFile,
                          RedirectAttributes ra) {
        if (!imagenFile.isEmpty()) {
            try {
                File dir = new File(UPLOAD_DIR);
                if (!dir.exists()) dir.mkdirs();

                String ext = "";
                String originalName = imagenFile.getOriginalFilename();
                if (originalName != null && originalName.contains(".")) {
                    ext = originalName.substring(originalName.lastIndexOf("."));
                }
                String fileName = UUID.randomUUID().toString() + ext;
                Path path = Paths.get(UPLOAD_DIR + fileName);
                Files.copy(imagenFile.getInputStream(), path);

                producto.setImagenUrl("/img/productos/" + fileName);
            } catch (IOException e) {
                ra.addFlashAttribute("error", "Error al subir imagen: " + e.getMessage());
            }
        }
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
