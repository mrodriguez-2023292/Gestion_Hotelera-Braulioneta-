package com.braulioneta.gestionHotelera.service;

import java.util.List;
import java.util.stream.Collectors;
import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.braulioneta.gestionHotelera.DTO.EventReservationResponseDTO;
import com.braulioneta.gestionHotelera.DTO.EventReservationSaveDTO;
import com.braulioneta.gestionHotelera.DTO.UserClearDTO;
import com.braulioneta.gestionHotelera.model.Event;
import com.braulioneta.gestionHotelera.model.EventReservation;
import com.braulioneta.gestionHotelera.model.User;
import com.braulioneta.gestionHotelera.repository.EventReservationRepository;
import com.braulioneta.gestionHotelera.service.IService.IEventReservation;

// Clase que implementa los servicios relacionados con los servicios adicionales en el sistema
@Service
public class EventReservationService implements IEventReservation {

    // Repositorio para acceder y manipular los datos de las reservaciones de eventos
    @Autowired
    EventReservationRepository reservationRepository;

    // Servicio que maneja las operaciones relacionadas con usuarios
    @Autowired
    UserService userService;

    // Servicio que maneja las operaciones relacionadas con eventos
    @Autowired
    EventService eventService;

    // Lista todas las reservaciones de eventos disponibles
    @Override
    public List<EventReservation> listReservations() {
        return reservationRepository.findAll();
    }

    // Obtiene una reservación de evento por su ID
    @Override
    public EventReservation getReservation(Long id) {
        return reservationRepository.findById(id).orElse(null);
    }

    // Lista las reservaciones de eventos de un usuario específico
    @Override
    public List<EventReservationResponseDTO> myReservations(Long userId) {
        User user = userService.getUser(userId);
        List<EventReservation> reservations = reservationRepository.findByUser(user);
        return reservations
            .stream()
            .map(reservation -> responseDTO(reservation))
            .collect(Collectors.toList());
    }

    // Encuentra una reservación por su ID
    @Override
    public EventReservation findByIdReservation(Long id) {
        return reservationRepository.findById(id).orElse(null);
    }

    // Agrega una nueva reservación de evento utilizando un DTO
    @Override
    public EventReservation addEventReservation(EventReservationSaveDTO reservationDto) {
        try {
            Timestamp start = Timestamp.valueOf(reservationDto.getStartDate());
            Timestamp end = Timestamp.valueOf(reservationDto.getEndDate());
            User user = userService.getUser(reservationDto.getUserId());
            Event event = eventService.getEvent(reservationDto.getEventId());

            EventReservation reservation = new EventReservation(
                null,
                start,
                end,
                reservationDto.getStatus(),
                reservationDto.getDetails(),
                user,
                event
            );
            return reservationRepository.save(reservation);
        } catch (Exception err) {
            throw new IllegalArgumentException("Error al parsear las fechas", err);
        }
    }

    // Actualiza una reservación de evento existente utilizando su ID y un DTO
    @Override
    public EventReservation updateEventReservation(Long id, EventReservationSaveDTO reservationDTO) {
       EventReservation existingReservation = reservationRepository.findById(id)
       .orElseThrow(() -> new IllegalArgumentException("Reservacion no encontrada"));

       try {
            Timestamp start = Timestamp.valueOf(reservationDTO.getStartDate());
            Timestamp end = Timestamp.valueOf(reservationDTO.getEndDate());
            User user = userService.getUser(reservationDTO.getUserId());
            Event event = eventService.getEvent(reservationDTO.getEventId());

            existingReservation.setStartDate(start);
            existingReservation.setEndDate(end);
            existingReservation.setStatus(reservationDTO.getStatus());
            existingReservation.setDetails(reservationDTO.getDetails());
            existingReservation.setUser(user);
            existingReservation.setEventId(event);
            
            return reservationRepository.save(existingReservation);
       } catch (Exception err) {
            throw new IllegalArgumentException("Error al parsear las fechas", err);
       }
    }

    // Elimina una reservación de evento existente del sistema
    @Override
    public void deleteReservation(EventReservation reservation) {
       reservationRepository.delete(reservation);
    }
    
    // Convierte una reservación de evento a un DTO para la respuesta
    private EventReservationResponseDTO responseDTO(EventReservation reservation) {
        User user = reservation.getUser();
        UserClearDTO userDTO = new UserClearDTO(
            user.getName(),
            user.getSurname(),
            user.getUsername()
        );

        EventReservationResponseDTO dto = new EventReservationResponseDTO(
            reservation.getId(),
            reservation.getStatus(),
            reservation.getDetails(),
            userDTO,
            reservation.getEventId()
        );
        return dto;
    }
}