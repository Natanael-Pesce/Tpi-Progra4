package com.utn.frvm.prode_api.dtos.responsedto;

public record RankingItemResponse(
    int posicion,
    Long usuarioId,
    String nombre,
    String apellido,
    Integer puntaje,
    Long aciertos,
    boolean esMiPosicion
) {}