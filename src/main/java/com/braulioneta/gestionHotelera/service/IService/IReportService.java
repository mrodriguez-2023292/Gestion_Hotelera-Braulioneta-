package com.braulioneta.gestionHotelera.service.IService;

import java.util.List;

import com.braulioneta.gestionHotelera.model.Report;

public interface IReportService {
    List<Report> listReports();

    Report getReport(Long id);

    Report addReport(Report report);

    void deleteReport(Report report);
    
}
