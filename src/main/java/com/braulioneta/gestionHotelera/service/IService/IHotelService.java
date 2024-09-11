package com.braulioneta.gestionHotelera.service.IService;

import java.util.List;

import com.braulioneta.gestionHotelera.model.Hotel;

public interface IHotelService {
    List<Hotel> listHotels();

    Hotel getHotel(Long id);

    Hotel saveHotel(Hotel hotel);

    void eliminateHotel(Hotel hotel);
}
