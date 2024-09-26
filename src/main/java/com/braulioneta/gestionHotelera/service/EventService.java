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

// Clase que implementa los servicios relacionados con los eventos en el sistema
@Service
public class EventService implements IEventService {

    // Repositorio para acceder y manipular los datos de eventos
    @Autowired
    EventRepository eventRepository;

    // Repositorio para acceder y manipular los datos de hoteles
    @Autowired
    HotelRepository hotelRepository;

    // Servicio que maneja las operaciones relacionadas con hoteles
    @Autowired
    HotelService hotelService;

    // Lista todos los eventos disponibles en el sistema
    @Override
    public List<Event> listEvents() {
        return eventRepository.findAll();
    }

    // Obtiene un evento por su ID
    @Override
    public Event getEvent(Long id) {
        return eventRepository.findById(id).orElse(null);
    }

    // Agrega un nuevo evento utilizando un DTO
    @Override
    public Event addEvent(EventSaveDTO eventDTO) {
        try {
            // Convertir de tipo String a Timestamp para la fecha de inicio
            Timestamp startDate = Timestamp.valueOf(eventDTO.getStartDate());
            // Convertir de tipo String a Timestamp para la fecha de finalización
            Timestamp endDate = Timestamp.valueOf(eventDTO.getEndDate());
            // Obtener el hotel asociado al evento
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

    // Actualiza un evento existente utilizando su ID y un DTO
    @Override
    public Event updateEvent(Long id, EventSaveDTO eventDTO) {
        // Obtener el evento existente por su ID
        Event existingEvent = eventRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Evento no encontrado"));

        try {
            // Convertir de tipo String a Timestamp para la fecha de inicio
            Timestamp startDate = Timestamp.valueOf(eventDTO.getStartDate());
            // Convertir de tipo String a Timestamp para la fecha de finalización
            Timestamp endDate = Timestamp.valueOf(eventDTO.getEndDate());
            // Obtener el hotel asociado al evento
            Hotel hotel = hotelService.getHotel(eventDTO.getHotelId());

            // Actualizar los campos del evento existente
            existingEvent.setName(eventDTO.getName());
            existingEvent.setDescription(eventDTO.getDescription());
            existingEvent.setStartDate(startDate);
            existingEvent.setEndDate(endDate);
            existingEvent.setHotelId(hotel);

            // Guardar el evento actualizado en la base de datos
            return eventRepository.save(existingEvent);
        } catch (Exception err) {
            throw new IllegalArgumentException("Error al parsear las fechas", err);
        }
    }

    // Elimina un evento existente del sistema
    @Override
    public void deleteEvent(Event event) {
        eventRepository.delete(event);
    }

    // Busca un evento por su ID (método adicional)
    public Event findFieldById(Long id) {
       return eventRepository.findById(id).orElse(null);
    }
}