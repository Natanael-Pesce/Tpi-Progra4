package com.utn.frvm.prode_api.dtos.requestdto;

import jakarta.validation.constraints.NotBlank;

public record UnirseGrupoRequest(

    @NotBlank(message = "El código de invitación es obligatorio")
    String codigoInvitacion

) {}
