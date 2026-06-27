package com.utn.frvm.prode_api.utility;

public enum Resultado {
    LOCAL,
    VISITANTE,
    EMPATE;

        public static Resultado calcularDesde(int golesLocal, int golesVisitante) {
        if (golesLocal > golesVisitante) return LOCAL;
        if (golesVisitante > golesLocal) return VISITANTE;
        return EMPATE;
    }
}
