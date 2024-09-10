package com.braulioneta.gestionHotelera.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.braulioneta.gestionHotelera.model.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
    public Hotel findByname(String name);
}
