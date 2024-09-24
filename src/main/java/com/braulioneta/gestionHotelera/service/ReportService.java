package com.braulioneta.gestionHotelera.service;

import java.util.List;
import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.braulioneta.gestionHotelera.DTO.ReportDTO;
import com.braulioneta.gestionHotelera.model.Hotel;
import com.braulioneta.gestionHotelera.model.Report;
import com.braulioneta.gestionHotelera.repository.ReportRepository;
import com.braulioneta.gestionHotelera.service.IService.IReportService;


@Service // indica que esta clase es un componente de servicio en Spring Framework.
public class ReportService implements IReportService {

    // Inyecta automáticamente una instancia de ReportRepository
    @Autowired
    ReportRepository reportRepository;

    // Inyecta automáticamente una instancia de HotelService
    @Autowired
    HotelService hotelService;

    /**
     * Devuelve una lista de todos los informes disponibles en la base de datos.
     *
     * @return List<Report> Lista de informes.
     */
    @Override
    public List<Report> listReports() {
        return reportRepository.findAll();
    }

    /**
     * Busca y devuelve un informe específico basado en su ID.
     *
     * @param id ID del informe que se busca.
     * @return Report El informe encontrado o null si no existe.
     */
    @Override
    public Report getReport(Long id) {
        return reportRepository.findById(id).orElse(null);
    }

    /**
     * Agrega un nuevo informe a la base de datos. Convierte un objeto ReportDTO
     * en una entidad Report válida, resolviendo las relaciones con otras entidades
     * como Hotel.
     *
     * @param reportDTO Objeto que contiene los datos necesarios para crear el informe.
     * @return Report El informe que se guardó en la base de datos.
     * @throws IllegalArgumentException Si hay un error al convertir las fechas.
     */
    @Override
    public Report addReport(ReportDTO reportDTO) {
        try {
            // Convierte la fecha de reporte de String a Timestamp
            Timestamp reportDate = Timestamp.valueOf(reportDTO.getReportDate());
            
            // Obtiene la información del hotel basado en el ID proporcionado
            Hotel hotel = hotelService.getHotel(reportDTO.getHotelId());

            // Crea una nueva instancia de Report con los datos proporcionados
            Report report = new Report(
                null, 
                reportDTO.getHotelName(),
                reportDate, 
                reportDTO.getTotalReservations(),
                reportDTO.getTotalRooms(), 
                reportDTO.getOccupiedRooms(),
                reportDTO.getOccupancyRate(), 
                reportDTO.getMostRequestedHotel(), 
                hotel
            ); 

            // Guarda el informe en la base de datos
            return reportRepository.save(report);
        } catch (Exception err) {
            throw new IllegalArgumentException("Error al parsear las fechas", err);
        }
    }

    /**
     * Elimina un informe existente de la base de datos.
     *
     * @param report El informe que se desea eliminar.
     */
    @Override
    public void deleteReport(Report report) {
        reportRepository.delete(report);
    }
}
