package com.braulioneta.gestionHotelera.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.braulioneta.gestionHotelera.model.Hotel;
import com.braulioneta.gestionHotelera.repository.HotelRepository;
import com.braulioneta.gestionHotelera.service.IService.IHotelService;

@Service
public class HotelService implements IHotelService{
    @Autowired
    HotelRepository hotelRepository;

    @Override
    public List<Hotel> listHotels(){
        return hotelRepository.findAll();
    }

    @Override
    public Hotel getHotel(Long id){
        return hotelRepository.findById(id).orElse(null);
    }

    @Override
    public Hotel register(Hotel hotel){
        return hotelRepository.save(hotel);
    }

    @Override
    public void eliminate(Hotel hotel) {
        hotelRepository.delete(hotel);
    }
}
