package uts.edu.java.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uts.edu.java.model.Rol;
import uts.edu.java.model.Usuario;
import uts.edu.java.repository.RolRepository;
import uts.edu.java.repository.UsuarioRepository;

@Component
@Order(1)
public class DataInitializer implements CommandLineRunner {

    private final RolRepository rolRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(RolRepository rolRepository,
                           UsuarioRepository usuarioRepository,
                           PasswordEncoder passwordEncoder) {
        this.rolRepository = rolRepository;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        Rol adminRol = rolRepository.findByNombre("Administrador").orElseGet(() -> {
            Rol r = rolRepository.save(new Rol("Administrador"));
            System.out.println("Rol Administrador creado");
            return r;
        });
        if (rolRepository.findByNombre("Cliente").isEmpty()) {
            rolRepository.save(new Rol("Cliente"));
            System.out.println("Rol Cliente creado");
        }

        if (!usuarioRepository.existsByEmail("admin@admin.com")) {
            Usuario admin = new Usuario("Administrador", "admin@admin.com",
                    passwordEncoder.encode("admin123"), adminRol);
            usuarioRepository.save(admin);
            System.out.println("Admin creado: admin@admin.com / admin123");
        }
    }
}
