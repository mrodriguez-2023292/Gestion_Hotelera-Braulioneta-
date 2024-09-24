package com.braulioneta.gestionHotelera.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.braulioneta.gestionHotelera.model.Report;

/**
 * ReportRepository es una interfaz que extiende JpaRepository, proporcionando
 * métodos CRUD (Crear, Leer, Actualizar, Eliminar) para gestionar los informes
 * (Report) en la base de datos.
 *
 * Al extender JpaRepository, no es necesario implementar manualmente métodos
 * comunes como guardar, buscar, eliminar, etc., ya que son proporcionados automáticamente
 * por Spring Data JPA.
 *
 * @param Report La entidad que se gestionará.
 * @param Long El tipo de dato para el identificador (ID) del informe.
 */
public interface ReportRepository extends JpaRepository<Report, Long> {
}
