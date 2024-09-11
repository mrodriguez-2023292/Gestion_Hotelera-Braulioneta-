package com.braulioneta.gestionHotelera.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Genera automáticamente getters, setters, equals, hashCode y toString
@Entity // Indica que esta clase es una entidad JPA (una tabla en la base de datos)
@AllArgsConstructor // Crea un constructor con todos los atributos
@NoArgsConstructor // Crea un constructor sin parámetros

public class Hotel {
    
   
    // Identificador único del hotel. Se genera automáticamente al crear un nuevo hotel.
    @Id // Define la clave primaria de la entidad
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Se genera automáticamente (auto-incremento en la base de datos)
    private Long id;

    // Nombre del hotel. No puede estar vacío.
    @NotBlank // Valida que no esté vacío ni en blanco
    private String name;

    
    // Dirección del hotel. No puede estar vacía.
    @NotBlank // Valida que no esté vacía ni en blanco
    private String address;

    // Categoría del hotel (por ejemplo, 3 estrellas, 4 estrellas). No puede estar vacía.
    @NotBlank // Valida que no esté vacía ni en blanco
    private String category;
    
    // Teléfono del hotel. No puede estar vacío.
    @NotBlank // Valida que no esté vacío ni en blanco
    private String phone;

    // Correo de contacto del hotel. Debe ser un correo válido y único en la base de datos.
    @Email // Valida que sea una dirección de correo válida
    @NotBlank // Valida que no esté vacío ni en blanco
    @Column(unique = true) // El correo debe ser único en la base de datos
    private String email_contact;
}
