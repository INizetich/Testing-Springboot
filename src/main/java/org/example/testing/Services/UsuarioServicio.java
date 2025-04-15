package org.example.testing.Services;

import org.example.testing.Models.Interfaces.UsuarioRepository;
import org.example.testing.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class UsuarioServicio {
    private final UsuarioRepository repository;

    @Autowired
    public UsuarioServicio(UsuarioRepository repository){
        this.repository = repository;
    }

    public List<Usuario> listarPersonas(){
        return repository.findAll();
    }

    public Usuario agregarPersona(Usuario usuario) {
        if(usuario.getNombre() == null || usuario.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es requerido");
        }
        if(usuario.getDni().trim().isEmpty()) {
            throw new IllegalArgumentException("El dni no puede estar vacio");
        }
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime fechaActual = LocalDateTime.now();
        usuario.setFecha_creacion(fechaActual.format(formateador));


        return repository.save(usuario);
    }
}
