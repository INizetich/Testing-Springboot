package org.example.testing.Controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.example.testing.Models.Persona;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class HTMLController {

    @GetMapping("/demo")
    public String demo(){
        return "demo";
    }

    @GetMapping({"", "/"})
    public String index() {
        return "redirect:/menu";
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
    public String listarPersonas(
            Model model,
            @RequestParam(defaultValue = "0") int page) {

        List<Persona> todasPersonas = PersonaController.obtenerPersonas();
        int size = 3; // Tamaño fijo de página

        // Calcular índices seguros
        int start = page * size;
        int end = Math.min(start + size, todasPersonas.size());

        // Obtener sublista para la página actual
        List<Persona> personasPagina = todasPersonas.subList(start, end);

        // Calcular total de páginas
        int totalPages = (int) Math.ceil((double) todasPersonas.size() / size);

        // Agregar atributos al modelo
        model.addAttribute("personas", personasPagina);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalPersonas", todasPersonas.size());

        return "listar-personas";
    }

    @DeleteMapping("/eliminar-persona/{dni}")
    @ResponseBody
    public ResponseEntity<Map<String, String>> eliminarPersona(
            @PathVariable String dni,
            HttpServletRequest request) {

        // Verificar si la solicitud viene de AJAX/JSON (protección CSRF básica)
        String requestedWith = request.getHeader("X-Requested-With");
        boolean isAjax = "XMLHttpRequest".equals(requestedWith);

        if (!isAjax) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "Acceso no autorizado"));
        }

        try {
            PersonaController.eliminarPersona(dni);
            return ResponseEntity.ok(Map.of("message", "Persona eliminada correctamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al eliminar la persona"));
        }
    }

    @GetMapping("/error-personas")
    public String manejarErrorPersonas(@RequestParam String mensaje, Model model) {
        model.addAttribute("mensajeError", mensaje);
        return "error-personas"; // Deberás crear esta vista
    }
}
