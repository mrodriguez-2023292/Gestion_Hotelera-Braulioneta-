package com.braulioneta.gestionHotelera.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.braulioneta.gestionHotelera.model.AdditionalService;
import com.braulioneta.gestionHotelera.model.Event;

import java.util.List;


public interface AdditionalServiceRepository extends JpaRepository<AdditionalService, Long>{
    List<AdditionalService> findByEvent(Event event);
}
