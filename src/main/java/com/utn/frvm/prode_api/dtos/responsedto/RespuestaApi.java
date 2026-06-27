package com.utn.frvm.prode_api.dtos.responsedto;

import lombok.Builder;

@Builder
public record RespuestaApi<T>(
    boolean success,
    String message,
    T data
) {
    public static <T> RespuestaApi<T> ok(T data) {
        return new RespuestaApi<>(true, "OK", data);
    }

    public static <T> RespuestaApi<T> ok(String message, T data) {
        return new RespuestaApi<>(true, message, data);
    }

    public static <T> RespuestaApi<T> error(String message) {
        return new RespuestaApi<>(false, message, null);
    }
}