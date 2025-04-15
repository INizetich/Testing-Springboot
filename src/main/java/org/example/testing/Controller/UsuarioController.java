package org.example.testing.Controller;

import jakarta.validation.Valid;
import org.example.testing.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.example.testing.Services.UsuarioServicio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/persona")
public class UsuarioController {


    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("/listar")
    public List<Usuario> obtenerPersonas() {
        return usuarioServicio.listarPersonas();
    }

    @PostMapping("/agregar")
    public ResponseEntity<?> agregarPersona(@Valid @RequestBody Usuario nuevoUsuario, BindingResult result) {
        if (result.hasErrors()) {
            // Manejar errores de validación
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(err -> {
                errores.put(err.getField(), err.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(errores);
        }

        Usuario usuarioGuardado = usuarioServicio.agregarPersona(nuevoUsuario);
        return ResponseEntity.ok(usuarioGuardado);
    }
}
