package com.utn.frvm.prode_api.controller;

import com.utn.frvm.prode_api.dtos.responsedto.RespuestaApi;
import com.utn.frvm.prode_api.dtos.responsedto.ReglaResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reglas")
public class ReglaController {

    @GetMapping
    public ResponseEntity<RespuestaApi<ReglaResponse>> getReglas() {
        return ResponseEntity.ok(RespuestaApi.ok(ReglaResponse.getInstance()));
    }
}

