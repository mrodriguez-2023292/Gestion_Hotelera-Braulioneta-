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

@Service // indica que esta clase es un componente de servicio en Spring Framework.
public class EventService implements IEventService {

    // Inyecta automáticamente una instancia de EventRepository para gestionar los eventos
    @Autowired
    EventRepository eventRepository;

    // Inyecta automáticamente una instancia de HotelRepository para gestionar los hoteles
    @Autowired
    HotelRepository hotelRepository;

    // Inyecta automáticamente el servicio de Hotel para obtener información sobre los hoteles
    @Autowired
    HotelService hotelService;

    /**
     * Devuelve una lista de todos los eventos disponibles en la base de datos.
     *
     * @return List<Event> Lista de eventos.
     */
    @Override
    public List<Event> listEvents() {
        return eventRepository.findAll();
    }

    /**
     * Busca y devuelve un evento específico basado en su ID.
     *
     * @param id ID del evento que se busca.
     * @return Event El evento encontrado o null si no existe.
     */
    @Override
    public Event getEvent(Long id) {
        return eventRepository.findById(id).orElse(null);
    }

    /**
     * Agrega un nuevo evento a la base de datos. Convierte un objeto EventSaveDTO
     * en una entidad Event válida, resolviendo las relaciones con otras entidades como Hotel.
     *
     * @param eventDTO Objeto que contiene los datos necesarios para crear el evento.
     * @return Event El evento que se guardó en la base de datos.
     * @throws IllegalArgumentException Si hay un error al convertir las fechas.
     */
    @Override
    public Event addEvent(EventSaveDTO eventDTO) {
        try {
            // Convierte las fechas de String a Timestamp
            Timestamp startDate = Timestamp.valueOf(eventDTO.getStartDate());
            Timestamp endDate = Timestamp.valueOf(eventDTO.getEndDate());
            
            // Obtiene la información del hotel basado en el ID proporcionado
            Hotel hotel = hotelService.getHotel(eventDTO.getHotelId());

            // Crea una nueva instancia de Event con los datos proporcionados
            Event event = new Event(
                null,
                eventDTO.getName(),
                eventDTO.getDescription(),
                startDate,
                endDate,
                hotel
            );
            
            // Guarda el evento en la base de datos
            return eventRepository.save(event);
        } catch (Exception err) {
            throw new IllegalArgumentException("Error al parsear las fechas", err);
        }
    }

    /**
     * Actualiza un evento existente en la base de datos basado en su ID.
     *
     * @param id ID del evento a actualizar.
     * @param eventDTO Objeto que contiene los nuevos datos del evento.
     * @return Event El evento actualizado.
     * @throws IllegalArgumentException Si el evento no es encontrado o hay un error al convertir las fechas.
     */
    @Override
    public Event updateEvent(Long id, EventSaveDTO eventDTO) {
        // Busca el evento existente o lanza una excepción si no se encuentra
        Event existingEvent = eventRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Evento no encontrado"));

        try {
            // Convierte las fechas de String a Timestamp
            Timestamp startDate = Timestamp.valueOf(eventDTO.getStartDate());
            Timestamp endDate = Timestamp.valueOf(eventDTO.getEndDate());

            // Obtiene la información del hotel basado en el ID proporcionado
            Hotel hotel = hotelService.getHotel(eventDTO.getHotelId());

            // Actualiza los datos del evento
            existingEvent.setName(eventDTO.getName());
            existingEvent.setDescription(eventDTO.getDescription());
            existingEvent.setStartDate(startDate);
            existingEvent.setEndDate(endDate);
            existingEvent.setHotelId(hotel);

            // Guarda el evento actualizado en la base de datos
            return eventRepository.save(existingEvent);
        } catch (Exception err) {
            throw new IllegalArgumentException("Error al parsear las fechas", err);
        }
    }

    /**
     * Elimina un evento existente de la base de datos.
     *
     * @param event El evento que se desea eliminar.
     */
    @Override
    public void cancelEvent(Event event) {
        eventRepository.delete(event);
    }
}
