package com.braulioneta.gestionHotelera.service.IService;

import java.util.List;

import com.braulioneta.gestionHotelera.model.Hotel;

public interface IHotelService {
    List<Hotel> listHotels();

    // Obtiene un hotel por su ID
    Hotel getHotel(Long id);

    // Guarda un hotel en la base de datos
    Hotel saveHotel(Hotel hotel);

    // Elimina un hotel de la base de datos
    void eliminateHotel(Hotel hotel);
}
