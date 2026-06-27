package com.utn.frvm.prode_api.services;

import com.utn.frvm.prode_api.config.exeptions.BadRequestExeption;
import com.utn.frvm.prode_api.models.Usuario;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Servicio auxiliar que expone el usuario autenticado en el request actual.
 * <p>
 * Gracias a {@link com.utn.frvm.prode_api.config.JwtAuthenticationFilter}, el token JWT
 * ya fue validado antes de llegar al controlador y el {@code Usuario} fue almacenado
 * en el {@code SecurityContextHolder}. Este servicio simplemente lo recupera.
 */
@Service
public class SesionService {

    /**
     * Retorna el {@link Usuario} autenticado en el request actual.
     *
     * @throws BadRequestExeption si no hay sesión activa (no debería ocurrir en rutas protegidas)
     */
    public Usuario usuarioActual() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()
                || !(auth.getPrincipal() instanceof Usuario usuario)) {
            throw new BadRequestExeption("Sesion invalida. Inicia sesion nuevamente.");
        }
        return usuario;
    }

    /**
     * Retorna el ID del {@link Usuario} autenticado en el request actual.
     */
    public Long usuarioIdActual() {
        return usuarioActual().getIdUsuario();
    }

    // ── Métodos de compatibilidad con los controladores existentes ───────────
    // El parámetro "authorization" ya no se usa; la autenticación la hizo el filtro JWT.

    /** @deprecated Usar {@link #usuarioActual()} directamente. */
    public Usuario usuarioDesdeHeader(String authorization) {
        return usuarioActual();
    }

    /** @deprecated Usar {@link #usuarioIdActual()} directamente. */
    public Long usuarioIdDesdeHeader(String authorization) {
        return usuarioIdActual();
    }
}
