package org.example.testing;

import org.example.testing.Models.Usuario;
import org.example.testing.Services.UsuarioServicio;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Scanner;


@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);

    }

    @Bean
    public CommandLineRunner run(UsuarioServicio servicio) {
        return args -> {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Ingrese nombre");
            String nombre = scanner.nextLine();
            System.out.println("Ingrese apellido");
            String apellido = scanner.nextLine();
            System.out.println("Ingrese dni");
            String dni = scanner.nextLine();
            System.out.println("Ingrese el email: ");
            String email = scanner.nextLine();

            servicio.agregarPersona(new Usuario(nombre, apellido, dni, email));
            System.out.println("Cargado.");
            System.exit(0);
        };
    }
}
