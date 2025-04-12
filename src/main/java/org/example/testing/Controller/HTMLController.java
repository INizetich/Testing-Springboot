package org.example.testing.Controller;

import org.springframework.ui.Model;
import org.example.testing.Models.Persona;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HTMLController {

    @GetMapping({"", "/"})
    public String index() {
        return "redirect:/menu-persona";
    }

    @GetMapping("/menu")
    public String mostrarMenu() {
        return "menu-persona";
    }

    @GetMapping("/formulario")
    public String mostrarFormulario() {
        return "formulario-persona";
    }

    @GetMapping("/eliminar-persona")
    public String mostrarEliminar() {
        return "eliminar-persona";
    }

    @GetMapping("/buscar-persona")
    public String mostrarBuscar() {
        return "buscar-persona";
    }

    @GetMapping("/listar-personas")
    public String listarPersonas(Model model) {
        List<Persona> personas = PersonaController.obtenerPersonas();
        model.addAttribute("personas", personas);
        return "listar-personas";
    }

}
