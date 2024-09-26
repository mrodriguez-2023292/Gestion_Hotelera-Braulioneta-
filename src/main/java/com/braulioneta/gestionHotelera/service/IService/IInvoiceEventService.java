package com.braulioneta.gestionHotelera.service.IService;

import java.util.List;

import com.braulioneta.gestionHotelera.DTO.InvoiceEventSaveDTO;
import com.braulioneta.gestionHotelera.model.InvoiceEvent;

public interface IInvoiceEventService {

    // Lista todas las facturas de eventos disponibles
    List<InvoiceEvent> listInvoiceEvents();

    // Obtiene una factura de evento por su ID
    InvoiceEvent getInvoiceEvent(Long id);

    // Genera una nueva factura de evento utilizando un DTO
    InvoiceEvent generateInvoice(InvoiceEventSaveDTO invoiceEventSaveDTO);
}