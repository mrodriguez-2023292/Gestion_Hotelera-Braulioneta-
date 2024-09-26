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

// Clase que implementa los servicios relacionados con los servicios adicionales en el sistema
@Service
public class AdditionalServicesService implements IAdditionalService {

    // Repositorio para acceder y manipular los datos de los servicios adicionales
    @Autowired
    AdditionalServiceRepository serviceRepository;

    // Servicio que maneja las operaciones relacionadas con eventos
    @Autowired
    EventService eventService;

    // Repositorio para acceder y manipular los datos de eventos
    @Autowired
    EventRepository eventRepository;

    // Servicio que maneja las operaciones relacionadas con habitaciones
    @Autowired
    RoomService roomService;

    // Repositorio para acceder y manipular los datos de habitaciones
    @Autowired
    RoomRepository roomRepository;

    // Lista todos los servicios adicionales disponibles
    @Override
    public List<AdditionalService> listAdditionalServices() {
        return serviceRepository.findAll();
    }

    // Obtiene un servicio adicional por su ID
    @Override
    public AdditionalService getAdditionalService(Long id) {
        return serviceRepository.findById(id).orElse(null);
    }

    // Agrega un nuevo servicio adicional utilizando un DTO
    @Override
    public AdditionalService addAdditionalService(AdditionalServiceDTO aServiceDTO) {
        try {
            Event event = eventService.getEvent(aServiceDTO.getEventId());
            Room room = roomService.getRoom(aServiceDTO.getRoomId());

            AdditionalService service = new AdditionalService(
                null,
                aServiceDTO.getName(),
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

    // Actualiza un servicio adicional existente utilizando su ID y un DTO
    @Override
    public AdditionalService updateService(Long id, AdditionalServiceDTO uServiceDTO) {
        AdditionalService existingService = serviceRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Servicio no encontrado")); 

        try {
            Event event = eventService.getEvent(uServiceDTO.getEventId());
            Room room = roomService.getRoom(uServiceDTO.getRoomId());

            existingService.setName(uServiceDTO.getName());
            existingService.setDescription(uServiceDTO.getDescription());
            existingService.setPrice(uServiceDTO.getPrice());
            existingService.setEvent(event);
            existingService.setRoom(room);

            return serviceRepository.save(existingService);

        } catch (Exception e) {
            throw new IllegalArgumentException("Error al colocar los datos", e);
        }
    }

    // Elimina un servicio adicional existente del sistema
    @Override
    public void deleteService(AdditionalService additionalService) {
        serviceRepository.delete(additionalService);
    }
}