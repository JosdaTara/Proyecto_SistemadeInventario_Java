package uts.edu.java.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import uts.edu.java.model.Rol;
import uts.edu.java.repository.RolRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RolRepository rolRepository;

    public DataInitializer(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    @Override
    public void run(String... args) {
        if (rolRepository.count() == 0) {
            rolRepository.save(new Rol("Administrador"));
            rolRepository.save(new Rol("Cliente"));
            System.out.println("Roles por defecto creados: Administrador, Cliente");
        }
    }
}
