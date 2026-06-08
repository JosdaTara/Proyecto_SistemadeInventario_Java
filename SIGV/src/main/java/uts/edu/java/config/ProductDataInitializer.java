package uts.edu.java.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import uts.edu.java.model.Producto;
import uts.edu.java.repository.ProductoRepository;
import java.math.BigDecimal;

@Component
@Order(2)
public class ProductDataInitializer implements CommandLineRunner {

    private final ProductoRepository productoRepository;

    public ProductDataInitializer(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    private Producto crear(String nombre, String desc, double precio, int stock, String cat) {
        return new Producto(nombre, desc, BigDecimal.valueOf(precio), stock, cat);
    }

    @Override
    public void run(String... args) {
        if (productoRepository.count() > 0) return;

        productoRepository.save(crear("Laptop Pro 15\"", "Laptop profesional con 16GB RAM, 512GB SSD y pantalla 4K", 4500000, 15, "Electronica"));
        productoRepository.save(crear("Audifonos Bluetooth", "Audifonos inalambricos con cancelacion de ruido activa", 350000, 42, "Electronica"));
        productoRepository.save(crear("Camiseta Algodon", "Camiseta de algodon organico, disponible en varios colores", 85000, 120, "Ropa"));
        productoRepository.save(crear("Zapatos Deportivos", "Zapatillas ligeras con amortiguacion premium", 280000, 55, "Ropa"));
        productoRepository.save(crear("Silla Ergonómica", "Silla de oficina ajustable con soporte lumbar", 1200000, 8, "Muebles"));
        productoRepository.save(crear("Escritorio Electrico", "Escritorio ajustable en altura con panel digital", 2200000, 5, "Muebles"));
        productoRepository.save(crear("Smartwatch X1", "Reloj inteligente con GPS, ritmo cardiaco y 10 dias de bateria", 890000, 28, "Electronica"));
        productoRepository.save(crear("Mochila Viajera", "Mochila impermeable de 40L con compartimento para laptop", 180000, 63, "Accesorios"));
    }
}
