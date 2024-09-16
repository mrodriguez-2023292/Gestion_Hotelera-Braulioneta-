package com.braulioneta.gestionHotelera.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.braulioneta.gestionHotelera.model.AdditionalService;
import com.braulioneta.gestionHotelera.model.Event;
import com.braulioneta.gestionHotelera.model.Room;
import com.braulioneta.gestionHotelera.DTO.AdditionalServiceDTO;
import com.braulioneta.gestionHotelera.repository.AdditionalServiceRepository;
import com.braulioneta.gestionHotelera.repository.EventRepository;
import com.braulioneta.gestionHotelera.repository.RoomRepository;
import com.braulioneta.gestionHotelera.service.IService.IAdditionalService;

@Service
public class AdditionalServicesService implements IAdditionalService {

    @Autowired
    AdditionalServiceRepository serviceRepository;

    @Autowired
    EventService eventService;

    @Autowired
    EventRepository eventRepository;

    @Autowired
    RoomService roomService;

    @Autowired
    RoomRepository roomRepository;

    @Override
    public List<AdditionalService> listAdditionalServices() {
        return serviceRepository.findAll();
    }

    @Override
    public AdditionalService getAdditionalService(Long id) {
        return serviceRepository.findById(id).orElse(null);
    }

    @Override
    public AdditionalService addAdditionalService(AdditionalServiceDTO aServiceDTO) {
        try {
            Event event = eventService.getEvent(aServiceDTO.getEvent());
            Room room = roomService.getRoom(aServiceDTO.getRoom());

            AdditionalService service = new AdditionalService(
                null,
                aServiceDTO.getService_name(),
                aServiceDTO.getDescription(),
                aServiceDTO.getPrice(),
                event, 
                room
            );
            return serviceRepository.save(service);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error al colocar los datos", e);
        }
    }

    @Override
    public void deleteService(AdditionalService additionalService) {
        serviceRepository.delete(additionalService);
    }

    @Override
    public AdditionalService updateService(Long id, AdditionalServiceDTO uServiceDTO) {

        AdditionalService existingService = serviceRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Servicio no encontrado")); 

        try {
            Event event = eventService.getEvent(uServiceDTO.getEvent());
            Room room = roomService.getRoom(uServiceDTO.getRoom());

            existingService.setService_name(uServiceDTO.getService_name());
            existingService.setDescription(uServiceDTO.getDescription());
            existingService.setPrice(uServiceDTO.getPrice());
            existingService.setEvent(event);
            existingService.setRoom(room);

            return serviceRepository.save(existingService);

        } catch (Exception e) {
            throw new IllegalArgumentException("Error al colocar los datos", e);
        }
    }
}
