package com.braulioneta.gestionHotelera.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.braulioneta.gestionHotelera.model.Reservation;
import com.braulioneta.gestionHotelera.model.User;

public interface ReservationRepository extends JpaRepository <Reservation, Long> {
    //metodo personalizado que busque reservacion por usuario
    List<Reservation> findByUser(User user);

}
