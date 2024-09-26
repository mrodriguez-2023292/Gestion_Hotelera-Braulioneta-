package com.braulioneta.gestionHotelera.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.braulioneta.gestionHotelera.DTO.InvoiceEventSaveDTO;
import com.braulioneta.gestionHotelera.model.Event;
import com.braulioneta.gestionHotelera.model.InvoiceEvent;
import com.braulioneta.gestionHotelera.repository.InvoiceEventRepository;
import com.braulioneta.gestionHotelera.service.IService.IInvoiceEventService;

// Clase que implementa los servicios relacionados con las facturas de eventos en el sistema
@Service
public class InvoiceEventService implements IInvoiceEventService {

    // Repositorio para acceder y manipular los datos de las facturas de eventos
    @Autowired
    InvoiceEventRepository invoiceEventRepository;

    // Servicio que maneja las operaciones relacionadas con eventos
    @Autowired
    EventService eventService;

    // Lista todas las facturas de eventos disponibles en el sistema
    @Override
    public List<InvoiceEvent> listInvoiceEvents() {
        return invoiceEventRepository.findAll();        
    }

    // Obtiene una factura de evento por su ID
    @Override
    public InvoiceEvent getInvoiceEvent(Long id) {
       return invoiceEventRepository.findById(id).orElse(null);
    }

    // Genera una nueva factura de evento utilizando un DTO
    @Override
    public InvoiceEvent generateInvoice(InvoiceEventSaveDTO invoiceEventSaveDTO) {
        try {
            // Obtener el evento asociado a la factura utilizando su ID
            Event event = eventService.getEvent(invoiceEventSaveDTO.getEventId());
            
            // Crear una nueva instancia de InvoiceEvent con los datos del DTO
            InvoiceEvent invoice = new InvoiceEvent(
                null,
                invoiceEventSaveDTO.getAmount(),
                invoiceEventSaveDTO.getDescription(),
                event
            );
            return invoiceEventRepository.save(invoice);
        } catch (Exception err) {
            throw new IllegalArgumentException("Error en el evento", err);
        }
    }
}