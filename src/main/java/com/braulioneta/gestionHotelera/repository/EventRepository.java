package com.braulioneta.gestionHotelera.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.braulioneta.gestionHotelera.model.Event;

/**
 * EventRepository es una interfaz que extiende JpaRepository, proporcionando
 * métodos CRUD (Crear, Leer, Actualizar, Eliminar) para gestionar los eventos
 * en la base de datos.
 *
 * Además de los métodos estándar, incluye un método personalizado `findFieldById`
 * que permite encontrar un evento específico basado en su ID, aunque el nombre del 
 * método no es típico y puede generar confusión.
 *
 * Se recomienda renombrar el método para seguir las convenciones de nomenclatura
 * estándar de Spring Data JPA.
 *
 * @param Event La entidad que se gestionará.
 * @param Long El tipo de dato para el identificador (ID) del evento.
 */
public interface EventRepository extends JpaRepository<Event, Long> {
    
    /**
     * Encuentra un evento por su ID.
     * 
     * @param id El ID del evento.
     * @return Event El evento correspondiente.
     */
    public Event findFieldById(Long id);
}
