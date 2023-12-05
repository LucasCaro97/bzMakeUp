package com.gestion.fibrolaser.entidades;

import com.gestion.fibrolaser.repositorios.RolRepository;
import com.gestion.fibrolaser.repositorios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DataInicializador {

    private final RolRepository rolRepositorio;
    private final UsuarioRepository usuarioRepositorio;
    private final BCryptPasswordEncoder passwordEncoder;


    @Autowired
    public DataInicializador(RolRepository rolRepositorio, UsuarioRepository usuarioRepositorio, BCryptPasswordEncoder passwordEncoder) {
        this.rolRepositorio = rolRepositorio;
        this.usuarioRepositorio = usuarioRepositorio;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void inicializarDatos() {
        if (rolRepositorio.count() == 0) {
            Rol rol = new Rol();
            rol.setName("ADMIN");
            rolRepositorio.save(rol);
        }

        if (usuarioRepositorio.count() == 0) {
            Usuario usuarioAdmin = new Usuario();
            usuarioAdmin.setUsername("bzagert");
            usuarioAdmin.setPassword(passwordEncoder.encode("Vencedores123"));
            usuarioAdmin.setNombreCompleto("Maria Belen Zagert");
            usuarioAdmin.setTelefono(3751444031l);
            usuarioAdmin.setCiudad("Eldorado, Misiones");
            usuarioAdmin.setRol(rolRepositorio.findById(1).get());
            usuarioRepositorio.save(usuarioAdmin);
        }
    }

}
