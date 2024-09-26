package com.braulioneta.gestionHotelera.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.braulioneta.gestionHotelera.DTO.RoomReservationResponseDTO;
import com.braulioneta.gestionHotelera.DTO.RoomReservationSaveDTO;
import com.braulioneta.gestionHotelera.DTO.UserClearDTO;
import com.braulioneta.gestionHotelera.model.Room;
import com.braulioneta.gestionHotelera.model.RoomReservation;
import com.braulioneta.gestionHotelera.model.User;
import com.braulioneta.gestionHotelera.repository.RoomReservationRepository;
import com.braulioneta.gestionHotelera.service.IService.IRoomReservation;

// Clase que implementa los servicios relacionados con las reservaciones de habitaciones en el sistema
@Service
public class RoomReservationService implements IRoomReservation {
    
    // Repositorio para acceder y manipular los datos de las reservaciones de habitaciones
    @Autowired
    RoomReservationRepository reservationRepository;

    // Servicio que maneja las operaciones relacionadas con usuarios
    @Autowired
    UserService userService;

    // Servicio que maneja las operaciones relacionadas con habitaciones
    @Autowired
    RoomService roomService;

    // Lista todas las reservaciones de habitaciones disponibles en el sistema
    @Override
    public List<RoomReservation> listReservations() {
        return reservationRepository.findAll();
    }

    // Obtiene una reservación de habitación por su ID
    @Override
    public RoomReservation getReservation(Long id) {
        return reservationRepository.findById(id).orElse(null);
    }

    // Lista las reservaciones de habitaciones de un usuario específico
    @Override
    public List<RoomReservationResponseDTO> myReservations(Long userId) {
        User user = userService.getUser(userId);
        List<RoomReservation> reservations = reservationRepository.findByUser(user);
        return reservations
            .stream()
            .map(reservation -> responseDTO(reservation))
            .collect(Collectors.toList());
    }

    // Encuentra una reservación por su ID
    @Override
    public RoomReservation findByIdReservation(Long id) {
        return reservationRepository.findById(id).orElse(null);
    }

    // Agrega una nueva reservación de habitación utilizando un DTO
    @Override
    public RoomReservation addRoomReservation(RoomReservationSaveDTO reservationDto) {
        try {
            // Convertir de tipo String a Timestamp para la fecha de inicio
            Timestamp start = Timestamp.valueOf(reservationDto.getStartDate());
            // Convertir de tipo String a Timestamp para la fecha de finalización
            Timestamp end = Timestamp.valueOf(reservationDto.getEndDate());
            // Obtener el usuario asociado a la reservación utilizando su ID
            User user = userService.getUser(reservationDto.getUserId());
            // Obtener la habitación asociada a la reservación utilizando su ID
            Room room = roomService.getRoom(reservationDto.getRoomId());

            RoomReservation reservation = new RoomReservation(
                null,
                start,
                end,
                reservationDto.getStatus(),
                reservationDto.getDetails(),
                user,
                room
            );
            return reservationRepository.save(reservation);
        } catch (Exception err) {
            throw new IllegalArgumentException("Error al parsear las fechas", err);
        }
        
    }

    // Actualiza una reservación existente utilizando su ID y un DTO
    @Override
    public RoomReservation updateRoomReservation(Long id, RoomReservationSaveDTO reservationDTO) {
       RoomReservation existingReservation = reservationRepository.findById(id)
       .orElseThrow(() -> new IllegalArgumentException("Reservacion no encontrada"));

       try {
            // Convertir de tipo String a Timestamp para la fecha de inicio
            Timestamp start = Timestamp.valueOf(reservationDTO.getStartDate());
            // Convertir de tipo String a Timestamp para la fecha de finalización
            Timestamp end = Timestamp.valueOf(reservationDTO.getEndDate());
            // Obtener el usuario asociado a la reservación utilizando su ID
            User user = userService.getUser(reservationDTO.getUserId());
            // Obtener la habitación asociada a la reservación utilizando su ID
            Room room = roomService.getRoom(reservationDTO.getRoomId());

            existingReservation.setStartDate(start);
            existingReservation.setEndDate(end);
            existingReservation.setStatus(reservationDTO.getStatus());
            existingReservation.setDetails(reservationDTO.getDetails());
            existingReservation.setUser(user);
            existingReservation.setRoomId(room);
            
            return reservationRepository.save(existingReservation);
       } catch (Exception err) {
           throw new IllegalArgumentException("Error al parsear las fechas", err);
       }
    }

    // Elimina una reservación existente del sistema
    @Override
    public void deleteReservation(RoomReservation reservation) {
       reservationRepository.delete(reservation);
    }
    
    // Convierte una reservación a un DTO para la respuesta 
    private RoomReservationResponseDTO responseDTO(RoomReservation reservation) {
        User user = reservation.getUser();
        UserClearDTO userDTO = new UserClearDTO(
            user.getName(),
            user.getSurname(),
            user.getUsername()
        );

        RoomReservationResponseDTO dto = new RoomReservationResponseDTO(
            reservation.getId(),
            reservation.getStatus(),
            reservation.getDetails(),
            userDTO,
            reservation.getRoomId()
        );
        return dto;
    }
}