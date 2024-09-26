package com.braulioneta.gestionHotelera.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Genera automáticamente getters, setters, equals, hashCode y toString
@Entity // Indica que esta clase es una entidad JPA (una tabla en la base de datos)
@AllArgsConstructor // Crea un constructor con todos los atributos
@NoArgsConstructor // Crea un constructor sin parámetros
public class AdditionalService {

    // Identificador único para cada servicio adicional
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nombre del servicio adicional, no puede estar en blanco
    @NotBlank
    private String name;

    // Descripción del servicio adicional, no puede estar en blanco
    @NotBlank
    private String description;

    // Precio del servicio adicional, debe ser un valor no nulo
    @NotNull
    private float price;

    // Evento asociado al servicio adicional, no puede ser nulo
    @NotNull
    @ManyToOne
    private Event event;

    // Habitación asociada al servicio adicional, no puede ser nula
    @NotNull
    @ManyToOne
    private Room room;
}