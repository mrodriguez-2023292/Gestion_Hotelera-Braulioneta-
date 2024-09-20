package com.braulioneta.gestionHotelera.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.braulioneta.gestionHotelera.DTO.RoomDTO;
import com.braulioneta.gestionHotelera.model.Hotel;
import com.braulioneta.gestionHotelera.model.Room;
import com.braulioneta.gestionHotelera.repository.HotelRepository;
import com.braulioneta.gestionHotelera.repository.RoomRepository;
import com.braulioneta.gestionHotelera.service.IService.IRoomService;



@Service
public class RoomService implements IRoomService{
    @Autowired
    RoomRepository roomRepository;

    @Autowired
    HotelService hotelService;

    @Autowired
    HotelRepository hotelRepository;

    @Override
    public List<Room> listRooms(){
        return roomRepository.findAll();
    }

    @Override
    public Room getRoom(Long id){
        return roomRepository.findById(id).orElse(null);
    }

    @Override
    public Room reserved(RoomDTO roomDTO) {
        try {
            Hotel hotel = hotelService.getHotel(roomDTO.getHotelId());
            Room room = new Room(
                null,
                roomDTO.getType(),
                roomDTO.getCapacity(),
                roomDTO.getPrice(),
                roomDTO.getStatus(),
                hotel
            );
            return roomRepository.save(room);
        } catch (Exception err) {
            throw new IllegalArgumentException("Error al elegir el hotel", err);
        }
    }


    @Override
    public void eliminate(Room room) {
        roomRepository.delete(room);
    }

    @Override
    public Room update(Long id, RoomDTO roomDTO) {
        try {
            // Buscar la habitación por su ID
            Room existingRoom = roomRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Habitación no encontrada con ID: " + id));

            // Obtener el hotel relacionado, si es necesario
            Hotel hotel = hotelService.getHotel(roomDTO.getHotelId());

            // Actualizar los campos de la habitación con los datos del DTO
            existingRoom.setType(roomDTO.getType());
            existingRoom.setCapacity(roomDTO.getCapacity());
            existingRoom.setPrice(roomDTO.getPrice());
            existingRoom.setStatus(roomDTO.getStatus());
            existingRoom.setHotel(hotel);  // Asumiendo que puedes actualizar el hotel de la habitación

            // Guardar la habitación actualizada en la base de datos
            return roomRepository.save(existingRoom);

        } catch (Exception err) {
            throw new IllegalArgumentException("Error al actualizar la habitación", err);
        }
    }

    public Room findFieldById(Long id) {
       return  roomRepository.findByid(id).orElse(null);
        
    }

    
    
}
