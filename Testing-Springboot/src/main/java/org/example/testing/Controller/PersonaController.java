package org.example.testing.Controller;


import jakarta.servlet.http.HttpServletRequest;
import org.example.testing.Models.Persona;
import org.example.testing.Models.PersonaDAO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;


@Controller
@RequestMapping("api/personas")
public class PersonaController {

    private static PersonaDAO personaDAO = new PersonaDAO();


    @PostMapping("/guardar")
    public RedirectView guardarPersona(HttpServletRequest request) {
        // Obtener datos del form directamente del request
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String dni = request.getParameter("dni");
        String email = request.getParameter("email");

        // Crear objeto Usuario (asumiendo que el ID es autoincremental, se setea en 0 o se ignora)
        Persona persona = new Persona(nombre, apellido, dni, email);

        // Insertar en la base de datos
        personaDAO.insertarPersona(persona);

        // Redirigir después del POST (puede ser una página de éxito, por ahora a "/")
        return new RedirectView("/menu");
    }

    public static boolean eliminarPersona(String dni) {
        return personaDAO.eliminarPersona(dni);
    }

    public static List<Persona> obtenerPersonas(){
        List<Persona> listaPersonas = personaDAO.obtenerTodasLasPersonas();

        return listaPersonas;
    }


}
