package uts.edu.java.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uts.edu.java.model.Rol;
import uts.edu.java.model.Usuario;
import uts.edu.java.repository.RolRepository;
import uts.edu.java.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository,
                          RolRepository rolRepository,
                          PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario registrarUsuario(String nombre, String email, String password, String rolNombre) {
        if (usuarioRepository.existsByEmail(email)) {
            throw new RuntimeException("El email ya esta registrado");
        }

        Rol rol = rolRepository.findByNombre(rolNombre)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + rolNombre));

        Usuario usuario = new Usuario(nombre, email, passwordEncoder.encode(password), rol);
        return usuarioRepository.save(usuario);
    }

    public Usuario actualizarUsuario(Integer id, String nombre, String email, String password, String rolNombre) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!usuario.getEmail().equals(email) && usuarioRepository.existsByEmail(email)) {
            throw new RuntimeException("El email ya esta registrado");
        }

        Rol rol = rolRepository.findByNombre(rolNombre)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + rolNombre));

        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setRol(rol);
        if (password != null && !password.isBlank()) {
            usuario.setPasswordHash(passwordEncoder.encode(password));
        }
        return usuarioRepository.save(usuario);
    }

    public void eliminarUsuario(Integer id) {
        usuarioRepository.deleteById(id);
    }
}
