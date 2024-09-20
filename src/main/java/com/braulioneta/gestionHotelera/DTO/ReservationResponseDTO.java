package com.braulioneta.gestionHotelera.DTO;

import java.sql.Timestamp;

import com.braulioneta.gestionHotelera.model.Event;
import com.braulioneta.gestionHotelera.model.Room;
import com.braulioneta.gestionHotelera.utils.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationResponseDTO {
    private Long id;
    private Status status;
    private String details;
    private UserClearDTO user;
    private Room room;
    private Event event;
}
