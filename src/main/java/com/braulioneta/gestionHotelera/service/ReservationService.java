package com.braulioneta.gestionHotelera.service;

import java.util.List;
import java.util.stream.Collectors;
import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.braulioneta.gestionHotelera.DTO.ReservationResponseDTO;
import com.braulioneta.gestionHotelera.DTO.ReservationSaveDTO;
import com.braulioneta.gestionHotelera.DTO.UserClearDTO;
import com.braulioneta.gestionHotelera.model.Event;
import com.braulioneta.gestionHotelera.model.Reservation;
import com.braulioneta.gestionHotelera.model.Room;
import com.braulioneta.gestionHotelera.model.User;
import com.braulioneta.gestionHotelera.repository.ReservationRepository;
import com.braulioneta.gestionHotelera.service.IService.IReservation;

@Service
public class ReservationService implements IReservation{

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    UserService userService;

    @Autowired
    RoomService roomService;

    @Autowired
    EventService  eventService;

    @Override
    public List<Reservation> listReservations(){
        return reservationRepository.findAll();
    }

    @Override
    public Reservation getReservation(Long id){
        return  reservationRepository.findById(id).orElse(null);

    }

    @Override
    public List<ReservationResponseDTO> myReservations(Long userId) {
        User user = userService.getUser(userId);
        List<Reservation> reservations = reservationRepository.findByUser(user);
        return reservations
            .stream()
            .map(reservation -> responseDTO(reservation))
            .collect(Collectors.toList());
    }

    

    @Override
    public Reservation findByIdReservation(Long id) {
        return reservationRepository.findById(id).orElse(null);
    }

    
    @Override
    public Reservation update(Long id, ReservationSaveDTO reservationDTO) {
       Reservation existingReservation = reservationRepository.findById(id)
       .orElseThrow(() -> new IllegalArgumentException("Reservacion no encontrada"));

       try {
        Timestamp start = Timestamp.valueOf(reservationDTO.getStartDate());
        Timestamp end = Timestamp.valueOf(reservationDTO.getEndDate());
        User user = userService.getUser(reservationDTO.getUserId());
        Room room = roomService.getRoom(reservationDTO.getRoomId());
        Event event = eventService.getEvent(reservationDTO.getEventId());

        existingReservation.setStartDate(start);
        existingReservation.setEndDate(end);
        existingReservation.setStatus(reservationDTO.getStatus());
        existingReservation.setDetails(reservationDTO.getDetails());
        existingReservation.setUser(user);
        existingReservation.setRoom(room);
        existingReservation.setEvent(event);
        
        return reservationRepository.save(existingReservation);
       } catch (Exception err) {
        throw new IllegalArgumentException("Error al parsear las fechas", err);
       }
    }
    


    @Override
    public void cancelReservation(Reservation reservation) {
       reservationRepository.delete(reservation);
    }
    
    private ReservationResponseDTO responseDTO(Reservation reservation){
        User user = reservation.getUser();
        UserClearDTO userDTO = new UserClearDTO(
            user.getName(),
            user.getSurname(),
            user.getUsername()
        );

        ReservationResponseDTO dto = new ReservationResponseDTO(
            reservation.getId(),
            reservation.getStatus(),
            reservation.getDetails(),
            userDTO,
            reservation.getRoom(),
            reservation.getEvent()
        );
        return dto;
    }

    @Override
    public Reservation save(ReservationSaveDTO reservationDto) {
        try {
            Timestamp start = Timestamp.valueOf(reservationDto.getStartDate());
            Timestamp end = Timestamp.valueOf(reservationDto.getEndDate());
            User user = userService.getUser(reservationDto.getUserId());
            Room room = roomService.getRoom(reservationDto.getRoomId());
            Event event = eventService.getEvent(reservationDto.getEventId());

            Reservation reservation = new Reservation(
                null,
                start,
                end,
                reservationDto.getStatus(),
                reservationDto.getDetails(),
                user,
                room,
                event
            );
            return reservationRepository.save(reservation);
                } catch (Exception err) {
                    throw new IllegalArgumentException("Error al parsear las fechas", err);
        }
        
    }

    

   


}
