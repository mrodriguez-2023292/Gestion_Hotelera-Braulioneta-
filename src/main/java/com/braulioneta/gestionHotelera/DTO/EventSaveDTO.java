package com.braulioneta.gestionHotelera.DTO;

import java.time.LocalDateTime;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * La clase EventSaveDTO (Data Transfer Object) se utiliza para transportar 
 * datos relacionados con la creación o actualización de un evento en el 
 * sistema de gestión hotelera. Facilita la transferencia de información 
 * entre la capa de presentación y la capa de servicio.
 *
 * Utiliza anotaciones de validación para garantizar que los datos cumplan 
 * con las reglas de negocio antes de ser procesados.
 * 
 * @Data: Genera automáticamente getters y setters.
 */
@Data
public class EventSaveDTO {

    /**
     * Nombre del evento.
     * No puede estar vacío.
     */
    @NotBlank(message = "El nombre del evento no puede ir vacío")
    private String name;

    /**
     * Descripción del evento.
     * No puede estar vacío.
     */
    @NotBlank(message = "La descripción del evento no puede ir vacía")
    private String description;

    /**
     * Fecha y hora de inicio del evento.
     * Debe ser una fecha presente o futura.
     * No puede ser nula.
     */
    @NotNull(message = "La fecha de inicio no puede ir vacía")
    @FutureOrPresent(message = "La fecha no puede ser del pasado, tiene que ser presente o futura")
    private LocalDateTime startDate;

    /**
     * Fecha y hora de finalización del evento.
     * Debe ser una fecha presente o futura.
     * No puede ser nula.
     */
    @NotNull(message = "La fecha de finalización no puede ir vacía")
    @FutureOrPresent(message = "La fecha no puede ser del pasado, tiene que ser presente")
    private LocalDateTime endDate;

    /**
     * Identificador del hotel donde se realiza el evento.
     * No puede ser nulo.
     */
    @NotNull(message = "No hay hotel para reservar")
    private Long hotelId;
}