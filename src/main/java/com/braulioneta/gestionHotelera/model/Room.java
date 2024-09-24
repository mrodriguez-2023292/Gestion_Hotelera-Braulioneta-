package com.braulioneta.gestionHotelera.model;

import com.braulioneta.gestionHotelera.utils.StatusRoom;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

public class Room {

    @Id// Identificador único de la habitación. Se genera automáticamente.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Tipo de habitación (por ejemplo, individual, doble, suite).
    // Este campo no puede estar vacío.
    @NotBlank
    private String type;

    // Capacidad máxima de la habitación (cantidad de personas que puede alojar).
    // Este campo es obligatorio.
    @NotNull
    private Integer capacity;

    // Precio por noche de la habitación.
    // Este campo es obligatorio.
    @NotNull
    private Float price;

    // Estado actual de la habitación, puede ser: AVAILABLE (disponible), RESERVED (reservada), MAINTENANCE (en mantenimiento).
    // Se utiliza una enumeración (enum) para representar estos estados.
    // Este campo es obligatorio y por defecto está en estado AVAILABLE.
    @Enumerated(EnumType.STRING)
    @NotNull
    private StatusRoom status = StatusRoom.AVAILABLE;

    // Se hace la llave foranea
    @NotBlank
    @ManyToOne //Por defecto tiene un Eager (población de datos)
    private Hotel hotelId;
}


