package uts.edu.java.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import uts.edu.java.model.Producto;
import uts.edu.java.repository.ProductoRepository;
import java.math.BigDecimal;

@Component
public class ProductDataInitializer implements CommandLineRunner {

    private final ProductoRepository productoRepository;

    public ProductDataInitializer(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    private Producto crear(String nombre, String desc, double precio, int stock, String cat, String imgSeed) {
        Producto p = new Producto(nombre, desc, BigDecimal.valueOf(precio), stock, cat);
        p.setImagenUrl("https://picsum.photos/seed/" + imgSeed + "/400/300");
        return p;
    }

    @Override
    public void run(String... args) {
        if (productoRepository.count() > 0) return;

        productoRepository.save(crear("Laptop Pro 15\"", "Laptop profesional con 16GB RAM, 512GB SSD y pantalla 4K", 2499.99, 15, "Electronica", "laptop"));
        productoRepository.save(crear("Audifonos Bluetooth", "Audifonos inalambricos con cancelacion de ruido activa", 199.99, 42, "Electronica", "headphones"));
        productoRepository.save(crear("Camiseta Algodon", "Camiseta de algodon organico, disponible en varios colores", 29.99, 120, "Ropa", "tshirt"));
        productoRepository.save(crear("Zapatos Deportivos", "Zapatillas ligeras con amortiguacion premium", 89.99, 55, "Ropa", "sneakers"));
        productoRepository.save(crear("Silla Ergonómica", "Silla de oficina ajustable con soporte lumbar", 459.99, 8, "Muebles", "chair"));
        productoRepository.save(crear("Escritorio Electrico", "Escritorio ajustable en altura con panel digital", 699.99, 5, "Muebles", "desk"));
        productoRepository.save(crear("Smartwatch X1", "Reloj inteligente con GPS, ritmo cardiaco y 10 dias de bateria", 349.99, 28, "Electronica", "smartwatch"));
        productoRepository.save(crear("Mochila Viajera", "Mochila impermeable de 40L con compartimento para laptop", 79.99, 63, "Accesorios", "backpack"));
    }
}
