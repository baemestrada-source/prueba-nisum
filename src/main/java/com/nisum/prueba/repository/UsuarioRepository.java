package com.nisum.prueba.repository;


import com.nisum.prueba.model.Usuario;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    Usuario findByName(String name);
    Usuario findByEmail(String email);
    Usuario findById(UUID id);
    Usuario findByToken(String token);
            
} 
    
