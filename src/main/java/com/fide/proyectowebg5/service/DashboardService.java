package com.fide.proyectowebg5.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fide.proyectowebg5.model.CanchaRanking;
import com.fide.proyectowebg5.model.Reserva;
import com.fide.proyectowebg5.repository.DashboardRepository;
import com.fide.proyectowebg5.repository.ReservaRepository;

@Service
public class DashboardService {

    private final DashboardRepository dashboardRepository;
    private final ReservaRepository reservaRepository;

    public DashboardService(
            DashboardRepository dashboardRepository,
            ReservaRepository reservaRepository) {

        this.dashboardRepository = dashboardRepository;
        this.reservaRepository = reservaRepository;
    }

    public Long totalUsuariosActivos() {
        return dashboardRepository.totalUsuariosActivos();
    }

    public Long totalCanchasActivas() {
        return dashboardRepository.totalCanchasActivas();
    }

    public Long totalReservas() {
        return dashboardRepository.totalReservas();
    }

    public BigDecimal totalIngresos() {
        return dashboardRepository.totalIngresos();
    }

    public Long contarReservasDia(java.sql.Date fecha) {
        return dashboardRepository.contarReservasDia(fecha);
    }

    public List<CanchaRanking> canchasMasReservadas() {

        return dashboardRepository.canchasMasReservadas()
                .stream()
                .limit(5)
                .toList();
    }

    public List<Reserva> proximasReservas() {

        LocalDate hoy = LocalDate.now();

        return reservaRepository.listar()
                .stream()
                .filter(reserva ->
                        reserva.getFechaReserva() != null
                                && !reserva.getFechaReserva().isBefore(hoy)
                                && reserva.getIdEstado() != null
                                && reserva.getIdEstado().equals(1L)
                )
                .sorted((primera, segunda) ->
                        primera.getFechaReserva()
                                .compareTo(segunda.getFechaReserva())
                )
                .limit(5)
                .toList();
    }
}