package com.braulioneta.gestionHotelera.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.braulioneta.gestionHotelera.model.Event;
import com.braulioneta.gestionHotelera.repository.EventRepository;
import com.braulioneta.gestionHotelera.service.IService.IEventService;

@Service
public class EventService implements IEventService{

    @Autowired
    EventRepository eventRepository;

    @Override
    public List<Event> listEvents() {
       return eventRepository.findAll();
    }

    @Override
    public Event getEvent(Long id) {
        return eventRepository.findById(id).orElse(null);
    }

    @Override
    public Event addEvent(Event event) {
       return eventRepository.save(event);
    }

    @Override
    public void cancelEvent(Event event) {
        eventRepository.delete(event);
    }

}
