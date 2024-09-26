package com.braulioneta.gestionHotelera.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.braulioneta.gestionHotelera.DTO.RoomSaveDTO;
import com.braulioneta.gestionHotelera.model.Hotel;
import com.braulioneta.gestionHotelera.model.Room;
import com.braulioneta.gestionHotelera.repository.HotelRepository;
import com.braulioneta.gestionHotelera.repository.RoomRepository;
import com.braulioneta.gestionHotelera.service.IService.IRoomService;

// Clase que implementa los servicios relacionados con las habitaciones en el sistema
@Service // Indica que esta clase es un servicio que maneja la lógica de negocio para las habitaciones
public class RoomService implements IRoomService {

    // Inyecta automáticamente el repositorio de habitaciones
    @Autowired 
    RoomRepository roomRepository;

    @Autowired
    HotelRepository hotelRepository;

    @Autowired
    HotelService hotelService;

    // Devuelve una lista con todas las habitaciones desde la base de datos
    @Override
    public List<Room> listRooms() {
        return roomRepository.findAll();
    }

    // Busca una habitación por su ID y la devuelve, o devuelve null si no se encuentra
    @Override
    public Room getRoom(Long id) {
        return roomRepository.findById(id).orElse(null);
    }

    // Guarda una habitación en la base de datos y la devuelve
    @Override
    public Room addRoom(RoomSaveDTO roomDto) {
        try {
            // Obtiene la información del hotel basado en el ID proporcionado
            Hotel hotel = hotelService.getHotel(roomDto.getHotelId());

            // Crea una nueva instancia de Room con los datos proporcionados
            Room newRoom = new Room(
                null,
                roomDto.getType(),
                roomDto.getCapacity(),
                roomDto.getPrice(),
                roomDto.getStatus(),
                hotel
            );
            
            // Guarda la habitación en la base de datos
            return roomRepository.save(newRoom);
        } catch (Exception err) {
            throw new IllegalArgumentException("Error al parsear", err);
        }
    }

    // Actualiza una habitación existente utilizando su ID y un DTO
    @Override
    public Room updateRoom(Long id, RoomSaveDTO roomDto) {
        // Busca la habitación existente o lanza una excepción si no se encuentra
        Room existingRoom = roomRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Habitacion no encontrada"));

        try {
            // Obtiene la información del hotel basado en el ID proporcionado
            Hotel hotel = hotelService.getHotel(roomDto.getHotelId());

            // Actualiza los datos de la habitación existente
            existingRoom.setType(roomDto.getType());
            existingRoom.setCapacity(roomDto.getCapacity());
            existingRoom.setPrice(roomDto.getPrice());
            existingRoom.setStatus(roomDto.getStatus());
            existingRoom.setHotelId(hotel);

            // Guarda la habitación actualizada en la base de datos
            return roomRepository.save(existingRoom);
        } catch (Exception err) {
            throw new IllegalArgumentException("Error al parsear", err);
        }
    }

    // Elimina una habitación de la base de datos
    @Override
    public void deleteRoom(Room room) {
        roomRepository.delete(room);
    }
}