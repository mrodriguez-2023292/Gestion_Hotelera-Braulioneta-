package com.braulioneta.gestionHotelera.DTO;

import java.time.LocalDateTime;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Genera automáticamente getters, setters, equals, hashCode y toString
@AllArgsConstructor // Crea un constructor con todos los atributos
@NoArgsConstructor // Crea un constructor sin parámetros
public class ReportSaveDTO {

    // Nombre del hotel al que corresponde el reporte. Este campo no puede estar vacío.
    @NotBlank(message = "El nombre del hotel no debe ir vacío")
    private String hotelName;

    // Fecha en la que se genera el reporte. Este campo no puede estar vacío y debe ser presente o futura.
    @NotNull(message = "La fecha del reporte no debe ser vacía")
    @FutureOrPresent(message = "La fecha tiene que ser presente o futura")
    private LocalDateTime reportDate;

    // Total de reservaciones realizadas. Este campo no puede estar vacío y debe ser un número positivo.
    @NotNull(message = "El total de reservaciones no debe ser vacío")
    @Min(value = 0, message = "El total de reservaciones debe ser un número positivo")
    private Long totalReservations;

    // Total de cuartos disponibles en el hotel. Este campo no puede estar vacío y debe ser un número positivo.
    @NotNull(message = "La cantidad total de cuartos no debe ser vacía")
    @Min(value = 0, message = "El número total de cuartos debe ser positivo")
    private Long totalRooms;

    // Número de habitaciones ocupadas en el hotel. Este campo no puede estar vacío y debe ser un número positivo.
    @NotNull(message = "El número de habitaciones ocupadas debe ser correcto")
    @Min(value = 0, message = "El número de habitaciones ocupadas debe ser positivo")
    private Long occupiedRooms;

    // Tasa de ocupación del hotel. Este campo no puede estar vacío, debe ser un valor positivo y no mayor a 100.
    @NotNull(message = "El porcentaje de ocupación no puede ser vacío")
    @Min(value = 0, message = "La tasa de ocupación debe ser un valor positivo")
    @Max(value = 100, message = "La tasa de ocupación no puede ser mayor que 100%")
    private Float occupancyRate;

    // Hotel más solicitado durante el período del reporte. Este campo no puede estar vacío.
    @NotBlank(message = "El hotel más requerido no puede estar vacío")
    private String mostRequestedHotel;

    // Identificador del hotel asociado al reporte. Este campo no puede estar vacío.
    @NotNull(message = "No hay hotel para seleccionar")
    private Long hotelId;
}