package com.utn.frvm.prode_api.repostories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import com.utn.frvm.prode_api.models.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findById(int usuarioId);

    Optional<Usuario> findByCorreo(String correo);

    boolean existByCorreo(String correo);
}