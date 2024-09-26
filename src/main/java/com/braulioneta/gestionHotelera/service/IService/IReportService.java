package com.braulioneta.gestionHotelera.service.IService;

import java.util.List;

import com.braulioneta.gestionHotelera.DTO.ReportSaveDTO;
import com.braulioneta.gestionHotelera.model.Report;

public interface IReportService {
    
    // Lista todos los informes disponibles
    List<Report> listReports();

    // Obtiene un informe por su ID
    Report getReport(Long id);

    // Agrega un nuevo informe utilizando un DTO
    Report addReport(ReportSaveDTO reportDTO);

    // Elimina un informe existente
    void deleteReport(Report report);
}