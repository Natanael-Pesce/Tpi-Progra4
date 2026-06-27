package com.utn.frvm.prode_api.services;

import com.utn.frvm.prode_api.dtos.requestdto.LoginRequest;
import com.utn.frvm.prode_api.dtos.requestdto.RegistroRequest;
import com.utn.frvm.prode_api.dtos.responsedto.AuthResponse;
import com.utn.frvm.prode_api.config.exeptions.BadRequestExeption;
import com.utn.frvm.prode_api.models.Usuario;
import com.utn.frvm.prode_api.utility.Rol;
import com.utn.frvm.prode_api.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponse registrar(RegistroRequest req) {
        if (usuarioRepo.existsByCorreo(req.correo())) {
            throw new BadRequestExeption("El correo ya esta registrado en el sistema");
        }

        Usuario usuario = Usuario.builder()
                .nombre(req.nombre())
                .apellido(req.apellido())
                .correo(req.correo())
                .contrasena(passwordEncoder.encode(req.contrasena()))
                .rol(Rol.USUARIO)
                .puntos(0)
                .build();

        usuarioRepo.save(usuario);
        return crearRespuesta(jwtService.generarToken(usuario), usuario);
    }

    public AuthResponse login(LoginRequest req) {
        Usuario usuario = usuarioRepo.findByCorreo(req.correo())
                .orElseThrow(() -> new BadRequestExeption("Credenciales incorrectas"));

        if (!passwordEncoder.matches(req.contrasena(), usuario.getContrasena())) {
            throw new BadRequestExeption("Credenciales incorrectas");
        }

        return crearRespuesta(jwtService.generarToken(usuario), usuario);
    }

    private AuthResponse crearRespuesta(String token, Usuario usuario) {
        return new AuthResponse(
                token,
                "Bearer",
                usuario.getIdUsuario(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getCorreo(),
                usuario.getRol());
    }
}
