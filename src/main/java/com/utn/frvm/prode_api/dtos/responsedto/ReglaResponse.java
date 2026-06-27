package com.utn.frvm.prode_api.dtos.responsedto;


import java.util.List;

public record ReglaResponse(
    String                descripcionGeneral,
    List<ReglaPuntuacion> sistemaPuntuacion,
    String                cierrePrediciones,
    String                gruposPrivados,
    String                desempate
) {
    public record ReglaPuntuacion(String caso, int puntos, String ejemplo) {}

    public static ReglaResponse getInstance() {
        return new ReglaResponse(
            "El Prode Deportivo es un sistema de predicción donde los usuarios compiten " +
            "pronosticando resultados de partidos de fútbol. Cuantos más aciertos acumules, " +
            "más alto subirás en el ranking global y en los rankings de tus grupos privados.",

            List.of(
                new ReglaPuntuacion(
                    "Marcador exacto (ambos goles correctos)", 3,
                    "Pronosticás 2-1 y el resultado es 2-1 → 3 puntos"),
                new ReglaPuntuacion(
                    "Resultado correcto (victoria local / visitante / empate)", 1,
                    "Pronosticás 2-1 y el resultado es 3-0 → 1 punto (ambos indican victoria local)"),
                new ReglaPuntuacion(
                    "Pronóstico incorrecto", 0,
                    "Pronosticás 2-1 y el resultado es 0-2 → 0 puntos")
            ),

            "Las predicciones cierran 30 minutos antes del inicio del partido. " +
            "Una vez vencido el plazo, las predicciones quedan bloqueadas y no pueden " +
            "crearse ni modificarse.",

            "Cualquier usuario puede crear un grupo privado. Al crearlo recibirás un " +
            "código único de 8 caracteres que podés compartir con amigos para que se " +
            "unan. Cada grupo tiene su propio ranking independiente del ranking global. " +
            "Podés abandonar un grupo en cualquier momento (excepto si sos el administrador).",

            "En caso de empate en puntos, el desempate se resuelve por cantidad de " +
            "aciertos totales. Si persiste el empate, el criterio es el orden de registro."
        );
    }
}
