package com.braulioneta.gestionHotelera.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.braulioneta.gestionHotelera.DTO.EventSaveDTO;
import com.braulioneta.gestionHotelera.model.Event;
import com.braulioneta.gestionHotelera.model.Hotel;
import com.braulioneta.gestionHotelera.repository.EventRepository;
import com.braulioneta.gestionHotelera.repository.HotelRepository;
import com.braulioneta.gestionHotelera.service.IService.IEventService;

@Service
public class EventService implements IEventService{

    @Autowired
    EventRepository eventRepository;
    
    @Autowired
    HotelRepository hotelRepository;

    @Autowired
    HotelService hotelService;

    @Override
    public List<Event> listEvents() {
       return eventRepository.findAll();
    }

    @Override
    public Event getEvent(Long id) {
        return eventRepository.findById(id).orElse(null);
    }

    @Override
    public Event addEvent(EventSaveDTO eventDTO) {
        try {
            //Convertir de tipo timestamp a String
            Timestamp startDate = Timestamp.valueOf(eventDTO.getStart());
            //Convertir la fecha de finalizacion en tipo String
            Timestamp endDate = Timestamp.valueOf(eventDTO.getEnd());
            //Obtenemos los hoteles o el hotel completo
            Hotel hotel = hotelService.getHotel(eventDTO.getHotelId());

            Event event = new Event(
                null,
                eventDTO.getName(),
                eventDTO.getDescription(),
                startDate,
                endDate,
                hotel
            );
            return eventRepository.save(event);
        } catch (Exception err) {
            throw new IllegalArgumentException("Error al parsear las fechas", err);
        }
       
    }

    @Override
    public Event updateEvent(Long id, EventSaveDTO eventDTO) {
        // Obtener el evento existente
        Event existingEvent = eventRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Evento no encontrado"));

        try {
            // Convertir de tipo timestamp a String
            Timestamp startDate = Timestamp.valueOf(eventDTO.getStart());
            // Convertir la fecha de finalización en tipo Timestamp
            Timestamp endDate = Timestamp.valueOf(eventDTO.getEnd());
            // Obtener el hotel asociado
            Hotel hotel = hotelService.getHotel(eventDTO.getHotelId());

            // Actualizar los campos del evento
            existingEvent.setName(eventDTO.getName());
            existingEvent.setDescription(eventDTO.getDescription());
            existingEvent.setStart(startDate);
            existingEvent.setEnd(endDate);
            existingEvent.setHotel(hotel);

            // Guardar el evento actualizado
            return eventRepository.save(existingEvent);
        } catch (Exception err) {
            throw new IllegalArgumentException("Error al parsear las fechas", err);
        }
    }



    @Override
    public void cancelEvent(Event event) {
        eventRepository.delete(event);
    }

}
