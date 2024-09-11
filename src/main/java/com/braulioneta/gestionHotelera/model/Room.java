package com.braulioneta.gestionHotelera.model;

import com.braulioneta.gestionHotelera.utils.StatusRoom;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Room {

    // Identificador único de la habitación. Se genera automáticamente.
    @Id
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
}


