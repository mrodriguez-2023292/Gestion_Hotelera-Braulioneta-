package com.braulioneta.gestionHotelera.model;

import com.braulioneta.gestionHotelera.utils.UserRoles;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Genera automáticamente getters, setters, equals, hashCode y toString
@Entity // Indica que esta clase es una entidad JPA (representa una tabla en la base de datos)
@AllArgsConstructor // Genera un constructor con todos los atributos de la clase
@NoArgsConstructor // Genera un constructor sin parámetros
public class User {

    // Identificador único del usuario. Se genera automáticamente.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nombre del usuario. Este campo no puede estar vacío.
    @NotBlank
    private String name;

    // Apellido del usuario. Este campo no puede estar vacío.
    @NotBlank
    private String surname;

    // Nombre de usuario único. Este campo no puede estar vacío y debe ser único en la base de datos.
    @NotBlank
    @Column(unique = true)
    private String username;

    // Correo electrónico del usuario. Debe ser un formato de email válido y único en la base de datos.
    @Email
    @NotBlank
    @Column(unique = true)
    private String email;

    // Contraseña del usuario. Este campo no puede estar vacío.
    @NotBlank
    private String password;

    // Número de teléfono del usuario. Este campo no puede estar vacío.
    @NotBlank
    private String phone; 

    // Rol del usuario. Utiliza la enumeración UserRoles, que define los tipos de roles disponibles.
    // Este campo no puede ser nulo.
    @Enumerated(EnumType.STRING)
    @NotNull
    private UserRoles userType;
}