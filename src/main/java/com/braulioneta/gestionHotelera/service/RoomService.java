package com.braulioneta.gestionHotelera.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.braulioneta.gestionHotelera.model.Room;
import com.braulioneta.gestionHotelera.repository.RoomRepository;
import com.braulioneta.gestionHotelera.service.IService.IRoomService;

@Service // Indica que esta clase es un servicio que maneja la lógica de negocio para las habitaciones
public class RoomService implements IRoomService {

    @Autowired // Inyecta automáticamente el repositorio de habitaciones
    RoomRepository roomRepository;

    @Override
    // Devuelve una lista con todas las habitaciones desde la base de datos
    public List<Room> listRooms(){
        return roomRepository.findAll();
    }

    @Override
    // Busca una habitación por su ID y la devuelve, o devuelve null si no se encuentra
    public Room getRoom(Long id){
        return roomRepository.findById(id).orElse(null);
    }

    @Override
    // Guarda una habitación en la base de datos y la devuelve
    public Room saveRoom(Room room){
        return roomRepository.save(room);
    }

    @Override
    // Elimina una habitación de la base de datos
    public void eliminateRoom(Room room) {
        roomRepository.delete(room);
    }
}