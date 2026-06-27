package com.utn.frvm.prode_api.dtos.responsedto;

import java.util.List;

public record EstadisticasResponse(
    Long   usuarioId,
    String nombreCompleto,
    Integer puntajeTotal,
    Long    totalPredicciones,
    Long    totalAciertos,
    double  efectividadPorcentaje,
    int     mejorRacha,
    List<EvolucionItem> evolucionPorJornada
) {
    public record EvolucionItem(Long jornadaId, String jornadaNombre, int puntosEnJornada) {}
}
