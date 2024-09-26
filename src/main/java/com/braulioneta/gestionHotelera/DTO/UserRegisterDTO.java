package com.braulioneta.gestionHotelera.DTO;

import com.braulioneta.gestionHotelera.utils.UserRoles;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data // Genera automáticamente getters, setters, equals, hashCode y toString
public class UserRegisterDTO {

    // Nombre del usuario que se registrará
    @NotBlank(message="El nombre del usuario no puede ir vacio")
    private String name;

    // Apellido del usuario que se registrará
    @NotBlank(message="El apellido del usuario no puede ir vacio")
    private String surname;

    // Nombre de usuario único para autenticación
    @NotBlank(message="El nombre del usuario no puede ir vacio")
    private String username;

    // Correo electrónico del usuario, debe ser válido
    @Email
    @NotBlank(message="El correo no puede ir vacio")
    private String email;

    // Contraseña del usuario para el registro
    @NotBlank(message="La contrasena no pude ir vacia")
    private String password;
    
    // Número telefónico del usuario
    @NotBlank(message="El numero telefonico no puede ir vacio")
    private String phone; 

    // Rol del usuario en el sistema, representado por la enumeración UserRoles
    @Enumerated(EnumType.STRING)
    private UserRoles userType;
}