package com.braulioneta.gestionHotelera.service.IService;

import java.util.List;

import com.braulioneta.gestionHotelera.DTO.RoomDTO;
import com.braulioneta.gestionHotelera.model.Room;

public interface IRoomService {
    List<Room> listRooms();

    Room getRoom(Long id);

    Room reserved(RoomDTO roomDTO);

    Room update(Long id, RoomDTO roomDTO);

    void eliminate(Room room);
}
