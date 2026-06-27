package com.utn.frvm.prode_api.controller;

import com.utn.frvm.prode_api.dtos.requestdto.LoginRequest;
import com.utn.frvm.prode_api.dtos.requestdto.RegistroRequest;
import com.utn.frvm.prode_api.dtos.responsedto.RespuestaApi;
import com.utn.frvm.prode_api.dtos.responsedto.AuthResponse;
import com.utn.frvm.prode_api.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/registro")
    public ResponseEntity<RespuestaApi<AuthResponse>> registrar(@Valid @RequestBody RegistroRequest req) {
        AuthResponse response = authService.registrar(req);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(RespuestaApi.ok("Cuenta creada exitosamente", response));
    }

    @PostMapping("/login")
    public ResponseEntity<RespuestaApi<AuthResponse>> login(@Valid @RequestBody LoginRequest req) {
        return ResponseEntity.ok(RespuestaApi.ok(authService.login(req)));
    }
}
