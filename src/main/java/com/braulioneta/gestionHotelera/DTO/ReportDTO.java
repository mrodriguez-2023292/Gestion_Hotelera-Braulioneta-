package com.braulioneta.gestionHotelera.DTO;

import java.time.LocalDateTime;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * La clase ReportDTO (Data Transfer Object) es un objeto que se utiliza para 
 * transportar datos relacionados con un informe en el sistema de gestión hotelera. 
 * Facilita la transferencia de datos entre la capa de presentación y la capa de 
 * servicio.
 *
 * Utiliza anotaciones de validación para garantizar que los datos cumplen con 
 * las reglas de negocio antes de ser procesados.
 * 
 * @Data: Genera automáticamente getters y setters.
 */
@Data
public class ReportDTO {

    /**
     * Nombre del hotel al que corresponde el informe.
     * No debe estar vacío.
     */
    @NotBlank(message = "El nombre del hotel no debe ir vacío")
    private String hotelName;

    /**
     * Fecha y hora en la que se genera el informe.
     * Debe ser una fecha presente o futura.
     * No debe ser nula.
     */
    @NotNull(message = "La fecha del reporte no debe ser vacía")
    @FutureOrPresent(message = "La fecha tiene que ser presente o futura")
    private LocalDateTime reportDate;

    /**
     * Total de reservas realizadas en el hotel.
     * No debe ser nulo y debe ser un número positivo.
     */
    @NotNull(message = "El total de reservaciones no debe ser vacío")
    @Min(value = 0, message = "El total de reservaciones debe ser un número positivo")
    private Long totalReservations;

    /**
     * Cantidad total de cuartos disponibles en el hotel.
     * No debe ser nula y debe ser un número positivo.
     */
    @NotNull(message = "La cantidad total de cuartos no debe ser vacía")
    @Min(value = 0, message = "El número total de cuartos debe ser positivo")
    private Long totalRooms;

    /**
     * Número de habitaciones ocupadas en el hotel.
     * No debe ser nulo y debe ser un número positivo.
     */
    @NotNull(message = "El número de habitaciones ocupadas debe ser correcto")
    @Min(value = 0, message = "El número de habitaciones ocupadas debe ser positivo")
    private Long occupiedRooms;

    /**
     * Tasa de ocupación del hotel, expresada como un porcentaje.
     * Debe estar entre 0 y 100, no puede ser nula.
     */
    @NotNull(message = "El porcentaje de ocupación no puede ser vacío")
    @Min(value = 0, message = "La tasa de ocupación debe ser un valor positivo")
    @Max(value = 100, message = "La tasa de ocupación no puede ser mayor que 100%")
    private Float occupancyRate;

    /**
     * Nombre del hotel más solicitado en el informe.
     * No puede estar vacío.
     */
    @NotBlank(message = "El hotel más requerido no puede estar vacío")
    private String mostRequestedHotel;

    /**
     * Identificador del hotel asociado al informe.
     * No puede ser nulo y debe ser un valor válido.
     */
    @NotBlank(message = "No hay hotel para reservar")
    private Long hotelId;
}