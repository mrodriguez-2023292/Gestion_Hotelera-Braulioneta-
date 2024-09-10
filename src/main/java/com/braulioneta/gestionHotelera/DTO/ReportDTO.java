package com.braulioneta.gestionHotelera.DTO;

import java.sql.Date;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ReportDTO {
    @NotBlank(message = "El nombre del hotel no debe ir vacio")
    private String hotel_name;
    @NotBlank(message = "La fecha del reporte no debe ir vacia")
    private Date report_date;
    @NotBlank(message = "El total no debe ir vacio")
    private Long total_reservations;
    @NotBlank(message = "La cantidad total de los cuartos no puede ser vacia")
    private Long total_rooms;
    @NotBlank(message = "Las habitaciones ocupadas deben ser correctas")
    private Long occupied_rooms;
    @NotBlank(message = "El procentaje no puede ser vacio")
    private Float occupancy_rate;
    @NotBlank(message = "Los hoteles mas requeridos no pueden ser vacios")
    private String most_requested_hotel;
 
}
