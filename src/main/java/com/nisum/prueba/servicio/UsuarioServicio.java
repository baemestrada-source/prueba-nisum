package com.nisum.prueba.servicio;

import com.nisum.prueba.model.Usuario;
import com.nisum.prueba.model.UsuarioDto;
import com.nisum.prueba.repository.UsuarioRepository;
import com.nisum.prueba.utilerias.seguridad;
import java.io.Serializable;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServicio implements Serializable {

    private static final long serialVersionUID = 1L;
    private UsuarioDto usuarioDto;

    @Autowired
    private UsuarioRepository repository;

    public UsuarioDto save(Usuario usuario) {
        String claveEncoded = seguridad.gerarBCrypt(usuario.getPassword());         
        
        Usuario newUsuario = new Usuario(usuario.getName(), usuario.getEmail(), claveEncoded, usuario.getPhones(),usuario.getIsactive());
        
        Usuario user = repository.save(newUsuario);
        
        Usuario userUpdate = createToken(user);
         usuarioDto = new UsuarioDto(userUpdate.getId(), userUpdate.getCreated(), 
                                     userUpdate.getModified(), userUpdate.getLast_login(), userUpdate.getToken(),userUpdate.getIsactive());
         
         
        return usuarioDto;      
    }

    public Usuario fyndByEmail(String email) {
        return repository.findByEmail(email);
    }

    public Usuario createToken(Usuario usuario) {        
        String claveEncoded = seguridad.gerarBCrypt(usuario.getId().toString());
        usuario.setToken(claveEncoded);
        Usuario user = repository.save(usuario);
        return user;
    }

    public Usuario validaToken(String token) {        
        return repository.findByToken(token);
    }
    public Usuario findByUUID(UUID id) {        
        return repository.findById(id);
    }
    
    
    
    
}