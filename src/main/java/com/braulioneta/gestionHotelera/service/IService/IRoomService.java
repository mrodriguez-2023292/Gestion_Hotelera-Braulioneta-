package com.braulioneta.gestionHotelera.service.IService;

import java.util.List;

import com.braulioneta.gestionHotelera.model.Room;

public interface IRoomService {
    List<Room> listRooms();

    Room getRoom(Long id);

    Room saveRoom(Room room);

    void eliminateRoom(Room room);
}
