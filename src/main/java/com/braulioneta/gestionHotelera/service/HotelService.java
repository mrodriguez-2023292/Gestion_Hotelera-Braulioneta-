package com.braulioneta.gestionHotelera.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.braulioneta.gestionHotelera.model.Hotel;
import com.braulioneta.gestionHotelera.repository.HotelRepository;
import com.braulioneta.gestionHotelera.service.IService.IHotelService;

@Service // Indica que esta clase es un servicio que maneja la lógica de negocio para los hoteles
public class HotelService implements IHotelService {

    @Autowired // Inyecta automáticamente el repositorio de hoteles
    HotelRepository hotelRepository;

    @Override
    // Devuelve una lista con todos los hoteles desde la base de datos
    public List<Hotel> listHotels(){
        return hotelRepository.findAll();
    }

    @Override
    // Busca un hotel por su ID y lo devuelve, o devuelve null si no se encuentra
    public Hotel getHotel(Long id){
        return hotelRepository.findById(id).orElse(null);
    }

    @Override
    // Guarda un hotel en la base de datos y lo devuelve
    public Hotel saveHotel(Hotel hotel){
        return hotelRepository.save(hotel);
    }

    @Override
    // Elimina un hotel de la base de datos
    public void eliminateHotel(Hotel hotel) {
        hotelRepository.delete(hotel);
    }
}