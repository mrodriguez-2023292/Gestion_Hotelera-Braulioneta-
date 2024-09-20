package com.braulioneta.gestionHotelera.DTO;

import com.braulioneta.gestionHotelera.utils.Status;



import java.time.LocalDateTime;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReservationSaveDTO {
    @NotNull(message = "La fecha de inicio no puede ir vacia")
    private LocalDateTime startDate;

    @NotNull(message = "La feecha de finalizacion no puede ir vacia")
    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "El status no puede ir vacio")
    private Status status;

    @NotBlank(message = "Los detalles no puedes  ir vacios")
    private String details;

    @NotNull(message = "No existe el usuario")
    private Long userId;

    @NotNull(message = "El cuarto  no existe")
    private Long  roomId;

    @NotNull(message = "El evento no existe")
    private Long  eventId;



}
