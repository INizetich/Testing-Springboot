package org.example.testing.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id_usuario")
    private Long id;

    @NotBlank(message = "El campo no puede estar vacio.")
    private String nombre;

    @NotBlank(message = "El campo no puede estar vacio.")
    private String apellido;

    @Column(unique = true)
    private String dni;

    @NotBlank(message = "El campo no puede estar vacio.")
    @Email(message = "Debe ingresar un Email valido.")
    @Column(unique = true)
    private String email;

    private String fecha_creacion;

    @OneToOne(mappedBy = "usuario")
    private Credencial credencial;


    public Usuario(String nombre, String apellido, String dni, String email) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.email = email;

    }

}
