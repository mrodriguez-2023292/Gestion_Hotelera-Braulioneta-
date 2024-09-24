package com.braulioneta.gestionHotelera.service.IService;

import java.util.List;

import com.braulioneta.gestionHotelera.DTO.EventSaveDTO;
import com.braulioneta.gestionHotelera.model.Event;

/**
 * IEventService es una interfaz que define los métodos necesarios para gestionar
 * los eventos en el sistema de gestión hotelera. Esta interfaz garantiza que cualquier
 * clase que la implemente, como EventService, proporcione la funcionalidad básica
 * para manejar eventos.
 */
public interface IEventService {

    /**
     * Devuelve una lista de todos los eventos disponibles en la base de datos.
     *
     * @return List<Event> Una lista de eventos.
     */
    List<Event> listEvents();

    /**
     * Busca y devuelve un evento específico basado en su ID.
     *
     * @param id El ID del evento que se desea obtener.
     * @return Event El evento correspondiente o null si no se encuentra.
     */
    Event getEvent(Long id);

    /**
     * Agrega un nuevo evento a la base de datos.
     *
     * @param eventDto Un objeto EventSaveDTO que contiene los datos necesarios para crear un evento.
     * @return Event El evento que fue agregado a la base de datos.
     */
    Event addEvent(EventSaveDTO eventDto);

    /**
     * Actualiza un evento existente en la base de datos.
     *
     * @param id El ID del evento que se desea actualizar.
     * @param eventDTO Un objeto EventSaveDTO que contiene los nuevos datos del evento.
     * @return Event El evento actualizado.
     */
    Event updateEvent(Long id, EventSaveDTO eventDTO);

    /**
     * Elimina un evento existente de la base de datos.
     *
     * @param event El evento que se desea eliminar.
     */
    void cancelEvent(Event event);
}
