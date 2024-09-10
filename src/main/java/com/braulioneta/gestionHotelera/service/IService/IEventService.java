package com.braulioneta.gestionHotelera.service.IService;

import java.util.List;

import com.braulioneta.gestionHotelera.model.Event;

public interface IEventService {
    List<Event> listEvents();

    Event  getEvent(Long id);

    Event addEvent(Event event);

    void cancelEvent(Event event);

}
