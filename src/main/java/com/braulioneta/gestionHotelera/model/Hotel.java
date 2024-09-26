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

    // Identificador único para cada hotel
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nombre del hotel, no puede estar en blanco
    @NotBlank
    private String name;

    // Dirección del hotel, no puede estar en blanco
    @NotBlank
    private String address;

    // Categoría del hotel (ej. 5 estrellas), no puede estar en blanco
    @NotBlank
    private String category;
    
    // Número de teléfono del hotel, no puede estar en blanco
    @NotBlank
    private String phone;

    // Correo electrónico de contacto del hotel, debe ser válido y único en la base de datos
    @Email
    @NotBlank
    @Column(unique = true)
    private String emailContact;
}