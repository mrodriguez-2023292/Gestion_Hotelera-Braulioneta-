package com.braulioneta.gestionHotelera.service.IService;

import java.util.List;

import com.braulioneta.gestionHotelera.DTO.EventSaveDTO;
import com.braulioneta.gestionHotelera.model.Event;

public interface IEventService {
    List<Event> listEvents();

    Event  getEvent(Long id);

    Event addEvent(EventSaveDTO eventDto);

    void cancelEvent(Event event);

    Event updateEvent(Long id, EventSaveDTO eventDTO);

}
