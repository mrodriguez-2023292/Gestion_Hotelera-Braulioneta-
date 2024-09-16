package com.braulioneta.gestionHotelera.service.IService;

import java.util.List;

import com.braulioneta.gestionHotelera.DTO.AdditionalServiceDTO;
import com.braulioneta.gestionHotelera.model.AdditionalService;

public interface IAdditionalService {
    List<AdditionalService> listAdditionalServices();

    AdditionalService getAdditionalService(Long id);

    AdditionalService addAdditionalService(AdditionalServiceDTO aServiceDTO);

    void deleteService(AdditionalService additionalService);

    AdditionalService updateService(Long id, AdditionalServiceDTO uServiceDTO);
}
