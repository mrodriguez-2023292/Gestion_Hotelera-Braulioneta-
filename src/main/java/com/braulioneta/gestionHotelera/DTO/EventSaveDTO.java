package com.braulioneta.gestionHotelera.DTO;

import java.time.LocalDateTime;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data // Genera automáticamente getters, setters, equals, hashCode y toString
public class EventSaveDTO {

    // Nombre del evento. Este campo no puede estar vacío.
    @NotBlank(message = "El nombre del evento no puede ir vacío")
    private String name;

    // Descripción del evento. Este campo no puede estar vacío.
    @NotBlank(message = "La descripción del evento no puede ir vacía")
    private String description;

    // Fecha y hora de inicio del evento. Este campo no puede estar vacío y no puede ser en el pasado.
    @NotNull(message = "La fecha de inicio no puede ir vacía")
    @FutureOrPresent(message = "La fecha no puede ser del pasado, tiene que ser presente o futura")
    private LocalDateTime startDate;

    // Fecha y hora de finalización del evento. Este campo no puede estar vacío y no puede ser en el pasado.
    @NotNull(message = "La fecha de finalización no puede ir vacía")
    @FutureOrPresent(message = "La fecha no puede ser del pasado, tiene que ser presente")
    private LocalDateTime endDate;

    // Identificador del hotel asociado al evento. Este campo no puede estar vacío.
    @NotNull(message = "No hay hotel para reservar")
    private Long hotelId;
}