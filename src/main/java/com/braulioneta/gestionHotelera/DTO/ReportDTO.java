package com.braulioneta.gestionHotelera.DTO;

import java.sql.Date;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReportDTO {

    @NotBlank(message = "El nombre del hotel no debe ir vacío")
    private String hotel_name;

    @NotNull(message = "La fecha del reporte no debe ser vacía")
    @FutureOrPresent(message = "La fecha tiene que ser presente o futura")
    private Date report_date;

    @NotNull(message = "El total de reservaciones no debe ser vacío")
    @Min(value = 0, message = "El total de reservaciones debe ser un número positivo")
    private Long total_reservations;

    @NotNull(message = "La cantidad total de cuartos no debe ser vacía")
    @Min(value = 0, message = "El número total de cuartos debe ser positivo")
    private Long total_rooms;

    @NotNull(message = "El número de habitaciones ocupadas debe ser correcto")
    @Min(value = 0, message = "El número de habitaciones ocupadas debe ser positivo")
    private Long occupied_rooms;

    @NotNull(message = "El porcentaje de ocupación no puede ser vacío")
    @Min(value = 0, message = "La tasa de ocupación debe ser un valor positivo")
    @Max(value = 100, message = "La tasa de ocupación no puede ser mayor que 100%")
    private Float occupancy_rate;

    @NotBlank(message = "El hotel más requerido no puede estar vacío")
    private String most_requested_hotel;

}

