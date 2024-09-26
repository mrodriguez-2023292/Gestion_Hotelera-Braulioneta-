package com.braulioneta.gestionHotelera.service.IService;

import java.util.List;

import com.braulioneta.gestionHotelera.DTO.AdditionalServiceDTO;
import com.braulioneta.gestionHotelera.model.AdditionalService;

public interface IAdditionalService {

    // Lista todos los servicios adicionales disponibles
    List<AdditionalService> listAdditionalServices();

    // Obtiene un servicio adicional por su ID
    AdditionalService getAdditionalService(Long id);

    // Agrega un nuevo servicio adicional a partir de un DTO
    AdditionalService addAdditionalService(AdditionalServiceDTO aServiceDTO);

    // Actualiza un servicio adicional existente con un DTO
    AdditionalService updateService(Long id, AdditionalServiceDTO uServiceDTO);
    
    // Elimina un servicio adicional
    void deleteService(AdditionalService additionalService);
    
}