package com.braulioneta.gestionHotelera.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.braulioneta.gestionHotelera.model.Room;
import com.braulioneta.gestionHotelera.repository.RoomRepository;
import com.braulioneta.gestionHotelera.service.IService.IRoomService;

@Service
public class RoomService implements IRoomService{
    @Autowired
    RoomRepository roomRepository;

    @Override
    public List<Room> listRooms(){
        return roomRepository.findAll();
    }

    @Override
    public Room getRoom(Long id){
        return roomRepository.findById(id).orElse(null);
    }

    @Override
    public Room register(Room room){
        return roomRepository.save(room);
    }

    @Override
    public void eliminate(Room room) {
        roomRepository.delete(room);
    }
}
