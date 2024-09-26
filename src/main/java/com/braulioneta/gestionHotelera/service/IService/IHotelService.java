package com.braulioneta.gestionHotelera.service.IService;

import java.util.List;

import com.braulioneta.gestionHotelera.DTO.HotelSaveDTO;
import com.braulioneta.gestionHotelera.model.Hotel;

public interface IHotelService {
    
    // Lista todos los hoteles disponibles
    List<Hotel> listHotels();

    // Obtiene un hotel por su ID
    Hotel getHotel(Long id);

    // Guarda un hotel en la base de datos
    Hotel addHotel(Hotel hotel);

    // Actualiza un hotel existente utilizando su ID y un DTO
    Hotel updateHotel(Long id, HotelSaveDTO hotelDTO);
    
    // Elimina un hotel de la base de datos
    void deleteHotel(Hotel hotel);
}