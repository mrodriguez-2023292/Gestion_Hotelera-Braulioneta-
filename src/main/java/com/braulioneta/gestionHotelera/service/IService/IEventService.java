package com.braulioneta.gestionHotelera.service.IService;

import java.util.List;

import com.braulioneta.gestionHotelera.DTO.EventSaveDTO;
import com.braulioneta.gestionHotelera.model.Event;

public interface IEventService {
    
    // Lista todos los eventos disponibles
    List<Event> listEvents();

    // Obtiene un evento específico por su ID
    Event getEvent(Long id);

    // Agrega un nuevo evento utilizando un DTO
    Event addEvent(EventSaveDTO eventDto);

    // Actualiza un evento existente utilizando su ID y un DTO
    Event updateEvent(Long id, EventSaveDTO eventDTO);
    
    // Elimina un evento existente
    void deleteEvent(Event event);
}