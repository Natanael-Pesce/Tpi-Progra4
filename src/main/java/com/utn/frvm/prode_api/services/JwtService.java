package com.utn.frvm.prode_api.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.utn.frvm.prode_api.config.exeptions.BadRequestExeption;
import com.utn.frvm.prode_api.models.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JwtService {

    private static final String HMAC_SHA256 = "HmacSHA256";

    private final ObjectMapper objectMapper;

    @Value("${app.jwt.secret}")
    private String secret;

    @Value("${app.jwt.expiration-hours}")
    private long expirationHours;

    public String generarToken(Usuario usuario) {
        Map<String, Object> header = new LinkedHashMap<>();
        header.put("alg", "HS256");
        header.put("typ", "JWT");

        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("sub", usuario.getCorreo());       // correo como subject
        payload.put("rol", usuario.getRol().name());
        payload.put("exp", Instant.now().plusSeconds(expirationHours * 3600).getEpochSecond());

        String datos = base64Json(header) + "." + base64Json(payload);
        return datos + "." + firmar(datos);
    }

    public String extraerCorreo(String token) {
        try {
            return (String) obtenerPayload(token).get("sub");
        } catch (Exception e) {
            return null;
        }
    }

    public boolean esTokenValido(String token, UserDetails userDetails) {
        String correo = extraerCorreo(token);
        return correo != null && correo.equals(userDetails.getUsername());
    }

    private Map<String, Object> obtenerPayload(String token) {
        String[] partes = token.split("\\.");
        if (partes.length != 3) {
            throw new BadRequestExeption("Sesion invalida. Inicia sesion nuevamente.");
        }

        String datos = partes[0] + "." + partes[1];
        String firmaEsperada = firmar(datos);
        if (!MessageDigest.isEqual(
                firmaEsperada.getBytes(StandardCharsets.UTF_8),
                partes[2].getBytes(StandardCharsets.UTF_8))) {
            throw new BadRequestExeption("Sesion invalida. Inicia sesion nuevamente.");
        }

        try {
            byte[] json = Base64.getUrlDecoder().decode(partes[1]);
            Map<String, Object> payload = objectMapper.readValue(
                    json, new TypeReference<Map<String, Object>>() {});

            Object exp = payload.get("exp");
            if (exp instanceof Number numero && numero.longValue() < Instant.now().getEpochSecond()) {
                throw new BadRequestExeption("Sesion vencida. Inicia sesion nuevamente.");
            }

            return payload;
        } catch (BadRequestExeption e) {
            throw e;
        } catch (Exception e) {
            throw new BadRequestExeption("Sesion invalida. Inicia sesion nuevamente.");
        }
    }

    private String base64Json(Map<String, Object> datos) {
        try {
            return Base64.getUrlEncoder().withoutPadding()
                    .encodeToString(objectMapper.writeValueAsBytes(datos));
        } catch (Exception e) {
            throw new BadRequestExeption("No se pudo generar la sesion");
        }
    }

    private String firmar(String datos) {
        try {
            Mac mac = Mac.getInstance(HMAC_SHA256);
            SecretKeySpec key = new SecretKeySpec(
                    secret.getBytes(StandardCharsets.UTF_8), HMAC_SHA256);
            mac.init(key);
            return Base64.getUrlEncoder().withoutPadding()
                    .encodeToString(mac.doFinal(datos.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            throw new BadRequestExeption("No se pudo validar la sesion");
        }
    }
}
