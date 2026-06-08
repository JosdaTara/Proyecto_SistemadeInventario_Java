package uts.edu.java.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uts.edu.java.model.*;
import uts.edu.java.repository.FacturaRepository;
import uts.edu.java.repository.PedidoRepository;
import uts.edu.java.repository.ProductoRepository;
import uts.edu.java.repository.UsuarioRepository;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.*;

@Controller
@RequestMapping("/cliente")
public class ClienteController {

    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;
    private final PedidoRepository pedidoRepository;
    private final FacturaRepository facturaRepository;

    public ClienteController(UsuarioRepository usuarioRepository,
                             ProductoRepository productoRepository,
                             PedidoRepository pedidoRepository,
                             FacturaRepository facturaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.productoRepository = productoRepository;
        this.pedidoRepository = pedidoRepository;
        this.facturaRepository = facturaRepository;
    }

    // --- Dashboard ---

    @GetMapping("/dashboard")
    public String dashboard(Principal principal, Model model) {
        Usuario usuario = usuarioRepository.findByEmail(principal.getName()).orElseThrow();
        long misPedidos = pedidoRepository.countByUsuario(usuario);
        long enviados = pedidoRepository.countByUsuarioAndEstado(usuario, "ENVIADO");
        model.addAttribute("usuario", usuario.getNombre());
        model.addAttribute("totalProductos", productoRepository.count());
        model.addAttribute("misPedidos", misPedidos);
        model.addAttribute("enviados", enviados);
        model.addAttribute("productos", productoRepository.findTop6ByActivoTrueOrderByIdProductoDesc());
        return "cliente/dashboard";
    }

    // --- Productos / Catálogo ---

    @GetMapping("/productos")
    public String productos(Principal principal, Model model) {
        model.addAttribute("usuario", principal.getName());
        model.addAttribute("productos", productoRepository.findByActivoTrue());
        return "cliente/productos";
    }

    // --- Carrito de Compras (sesión) ---

    @SuppressWarnings("unchecked")
    @GetMapping("/carrito")
    public String verCarrito(HttpSession session, Model model) {
        List<Map<String, Object>> items = (List<Map<String, Object>>) session.getAttribute("carrito");
        if (items == null) items = new ArrayList<>();
        BigDecimal total = items.stream()
                .map(i -> ((BigDecimal) i.get("subtotal")))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        model.addAttribute("items", items);
        model.addAttribute("total", total);
        return "cliente/carrito";
    }

    @SuppressWarnings("unchecked")
    @PostMapping("/carrito/agregar")
    public String agregarCarrito(@RequestParam Integer productoId,
                                 @RequestParam(defaultValue = "1") Integer cantidad,
                                 HttpSession session, RedirectAttributes ra) {
        Optional<Producto> opt = productoRepository.findById(productoId);
        if (opt.isEmpty() || !opt.get().getActivo()) {
            ra.addFlashAttribute("error", "Producto no disponible");
            return "redirect:/cliente/productos";
        }
        Producto p = opt.get();
        if (cantidad > p.getStock()) {
            ra.addFlashAttribute("error", "Stock insuficiente (máx: " + p.getStock() + ")");
            return "redirect:/cliente/productos";
        }

        List<Map<String, Object>> items = (List<Map<String, Object>>) session.getAttribute("carrito");
        if (items == null) items = new ArrayList<>();

        Optional<Map<String, Object>> existente = items.stream()
                .filter(i -> i.get("productoId").equals(productoId))
                .findFirst();

        if (existente.isPresent()) {
            Map<String, Object> item = existente.get();
            int nuevaCant = (int) item.get("cantidad") + cantidad;
            if (nuevaCant > p.getStock()) {
                ra.addFlashAttribute("error", "Stock insuficiente en carrito");
                return "redirect:/cliente/productos";
            }
            item.put("cantidad", nuevaCant);
            item.put("subtotal", p.getPrecio().multiply(BigDecimal.valueOf(nuevaCant)));
        } else {
            Map<String, Object> item = new HashMap<>();
            item.put("productoId", p.getIdProducto());
            item.put("nombre", p.getNombre());
            item.put("precio", p.getPrecio());
            item.put("cantidad", cantidad);
            item.put("subtotal", p.getPrecio().multiply(BigDecimal.valueOf(cantidad)));
            items.add(item);
        }

        session.setAttribute("carrito", items);
        ra.addFlashAttribute("success", p.getNombre() + " agregado al carrito");
        return "redirect:/cliente/productos";
    }

    @SuppressWarnings("unchecked")
    @PostMapping("/carrito/actualizar")
    public String actualizarCarrito(@RequestParam Integer productoId,
                                    @RequestParam Integer cantidad,
                                    HttpSession session) {
        List<Map<String, Object>> items = (List<Map<String, Object>>) session.getAttribute("carrito");
        if (items == null) return "redirect:/cliente/carrito";

        if (cantidad <= 0) {
            items.removeIf(i -> i.get("productoId").equals(productoId));
        } else {
            items.stream()
                    .filter(i -> i.get("productoId").equals(productoId))
                    .findFirst()
                    .ifPresent(item -> {
                        item.put("cantidad", cantidad);
                        item.put("subtotal", ((BigDecimal) item.get("precio"))
                                .multiply(BigDecimal.valueOf(cantidad)));
                    });
        }
        if (items.isEmpty()) session.removeAttribute("carrito");
        else session.setAttribute("carrito", items);
        return "redirect:/cliente/carrito";
    }

    @SuppressWarnings("unchecked")
    @PostMapping("/carrito/eliminar")
    public String eliminarCarrito(@RequestParam Integer productoId, HttpSession session) {
        List<Map<String, Object>> items = (List<Map<String, Object>>) session.getAttribute("carrito");
        if (items == null) return "redirect:/cliente/carrito";
        items.removeIf(i -> i.get("productoId").equals(productoId));
        if (items.isEmpty()) session.removeAttribute("carrito");
        else session.setAttribute("carrito", items);
        return "redirect:/cliente/carrito";
    }

    // --- Checkout ---

    @SuppressWarnings("unchecked")
    @PostMapping("/checkout")
    public String checkout(HttpSession session, Principal principal, RedirectAttributes ra) {
        try {
            List<Map<String, Object>> items = (List<Map<String, Object>>) session.getAttribute("carrito");
            if (items == null || items.isEmpty()) {
                ra.addFlashAttribute("error", "El carrito está vacío");
                return "redirect:/cliente/carrito";
            }

            Usuario usuario = usuarioRepository.findByEmail(principal.getName()).orElseThrow();
            Pedido pedido = new Pedido();
            pedido.setUsuario(usuario);
            pedido.setEstado("ENVIADO");

            BigDecimal total = BigDecimal.ZERO;
            List<DetallePedido> detalleList = new ArrayList<>();

            for (Map<String, Object> item : items) {
                Integer prodId = (Integer) item.get("productoId");
                Integer cantidad = (Integer) item.get("cantidad");
                Producto producto = productoRepository.findById(prodId).orElse(null);
                if (producto == null || cantidad > producto.getStock()) {
                    ra.addFlashAttribute("error", "Stock insuficiente para " + item.get("nombre"));
                    return "redirect:/cliente/carrito";
                }
                DetallePedido detalle = new DetallePedido(pedido, producto, cantidad, producto.getPrecio());
                detalleList.add(detalle);
                total = total.add(detalle.getSubtotal());

                producto.setStock(producto.getStock() - cantidad);
                productoRepository.save(producto);
            }

            pedido.setTotal(total);
            pedido.setDetalle(detalleList);
            pedido = pedidoRepository.saveAndFlush(pedido);

            Factura factura = new Factura();
            factura.setPedido(pedido);
            factura.setNumeroFactura("FAC-" + String.format("%05d", pedido.getIdPedido()));
            factura.setTotal(total);
            factura.setMetodoPago("EFECTIVO");
            facturaRepository.saveAndFlush(factura);

            session.removeAttribute("carrito");
            ra.addFlashAttribute("success", "Pedido #" + pedido.getIdPedido() + " creado exitosamente");
            return "redirect:/cliente/pedidos";
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Error al procesar pedido: " + e.getMessage());
            return "redirect:/cliente/carrito";
        }
    }

    // --- Mis Pedidos ---

    @GetMapping("/pedidos")
    public String pedidos(Principal principal, Model model) {
        Usuario usuario = usuarioRepository.findByEmail(principal.getName()).orElseThrow();
        List<Pedido> pedidos = pedidoRepository.findByUsuarioOrderByFechaPedidoDesc(usuario);
        model.addAttribute("usuario", principal.getName());
        model.addAttribute("pedidos", pedidos);
        return "cliente/pedidos";
    }

    @GetMapping("/pedido/{id}")
    public String verPedido(@PathVariable Integer id, Principal principal, Model model) {
        Optional<Pedido> opt = pedidoRepository.findById(id);
        if (opt.isEmpty()) return "redirect:/cliente/pedidos";
        Pedido pedido = opt.get();
        Usuario usuario = usuarioRepository.findByEmail(principal.getName()).orElseThrow();
        if (!pedido.getUsuario().getIdUsuario().equals(usuario.getIdUsuario())) {
            return "redirect:/cliente/pedidos";
        }
        model.addAttribute("usuario", principal.getName());
        model.addAttribute("pedido", pedido);
        return "cliente/pedido-detalle";
    }

    // --- Factura ---

    @GetMapping("/factura/{id}")
    public String verFactura(@PathVariable Integer id, Principal principal, Model model) {
        Optional<Pedido> opt = pedidoRepository.findById(id);
        if (opt.isEmpty()) return "redirect:/cliente/pedidos";
        Pedido pedido = opt.get();
        Usuario usuario = usuarioRepository.findByEmail(principal.getName()).orElseThrow();
        if (!pedido.getUsuario().getIdUsuario().equals(usuario.getIdUsuario())) {
            return "redirect:/cliente/pedidos";
        }
        Optional<Factura> factOpt = facturaRepository.findByPedido(pedido);
        if (factOpt.isEmpty()) return "redirect:/cliente/pedidos";
        model.addAttribute("usuario", principal.getName());
        model.addAttribute("factura", factOpt.get());
        return "cliente/factura";
    }

    // --- Perfil ---

    @GetMapping("/perfil")
    public String perfil(Principal principal, Model model) {
        Usuario usuario = usuarioRepository.findByEmail(principal.getName()).orElseThrow();
        model.addAttribute("nombre", usuario.getNombre());
        model.addAttribute("email", usuario.getEmail());
        model.addAttribute("fechaRegistro", usuario.getFechaRegistro());
        return "cliente/perfil";
    }
}
