package com.braulioneta.gestionHotelera.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.braulioneta.gestionHotelera.model.Room;

public interface RoomRepository extends JpaRepository<Room, Long>{
    public Room findByid(Long id);
}
