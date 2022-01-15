package com.nisum.prueba.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import com.nisum.prueba.model.Usuario;
import com.nisum.prueba.model.UsuarioDto;
import com.nisum.prueba.repository.UsuarioRepository;
import com.nisum.prueba.respuesta.RespError;
import com.nisum.prueba.servicio.UsuarioServicio;
import com.nisum.prueba.utilerias.seguridad;


@RestController
@RequestMapping("/api")
public class UsuarioController {
	
   private final Logger logger = LoggerFactory.getLogger(UsuarioController.class);
   private UsuarioDto usuarioDto;
    
   @Autowired
   UsuarioServicio servicio;
	
	@Autowired
	UsuarioRepository UsuarioRepo;
	
	@GetMapping("/usuarios")
	public ResponseEntity<List<Usuario>> getAllUser() {
		try {
			List<Usuario> list = UsuarioRepo.findAll();
			
			if (list.isEmpty() || list.size() == 0) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			
			return new ResponseEntity<>(list, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PostMapping("/usuarios")
	public ResponseEntity saveUser(@RequestBody Usuario usuario) {
		try {
			
			RespError respuesta = new RespError();
			
			if (usuario.getName().isEmpty() || usuario.getPassword().isEmpty()) {
	            respuesta.setMensagem("El campo nombre y/o password no pueden estar vacios!");
	            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(respuesta);
	        }
			
			if (!Usuario.checkEmail(usuario.getEmail())) {
				respuesta.setMensagem("Por favor ingrese un email valido!");
		        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(respuesta);
		    }

	        
			if (servicio.fyndByEmail(usuario.getEmail()) != null) {
	            respuesta.setMensagem("El correo ya registrado");
	            return ResponseEntity.badRequest().body(respuesta);
	        }
	
			
			
			//return new ResponseEntity<>(UsuarioRepo.save(usuario), HttpStatus.CREATED);
			return ResponseEntity.status(HttpStatus.CREATED).body(servicio.save(usuario));
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PostMapping("/access")
	public ResponseEntity access(@RequestBody Usuario user) {

	        //logger.debug("REST request validate access : {}", user);
	        Usuario usuario = servicio.fyndByEmail(user.getEmail());
	        RespError respuesta = new RespError();
	        if (usuario != null && (seguridad.claveValida(user.getPassword(), usuario.getPassword())) == true) {

	            usuarioDto = new UsuarioDto(usuario.getId(), usuario.getCreated(),
	                    usuario.getModified(), usuario.getLast_login(), usuario.getToken(), usuario.getIsactive());

	            return ResponseEntity.ok().body(usuarioDto);
	        } else {
	        	respuesta.setMensagem("Usuario y/o clave invalidos");
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(respuesta);
	        }

	    }

	    @PostMapping("/token")
	    public ResponseEntity token(@RequestHeader(value = "token") String token, @RequestParam String id) {

	        //logger.debug("REST request validate access : {}", token);
	    	RespError respuesta = new RespError();

	        if (token.isEmpty() || id.isEmpty()) {
	        	respuesta.setMensagem("Los campos token y/o id no pueden estar vacíos!");
	            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(respuesta);
	        }

	        Usuario user = servicio.validaToken(token);

	        if (user == null) {
	            respuesta.setMensagem("No esta autorizado");
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(respuesta);
	        }

	        UUID uuid = UUID.fromString(id);

	        Usuario userUUid = servicio.findByUUID(uuid);

	        if (userUUid == null) {
	            respuesta.setMensagem("No esta autorizado");
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(respuesta);
	        }

	        if (userUUid.getToken().equals(token)) {

	            LocalDateTime dataNow = LocalDateTime.now();
	            LocalDateTime lastLogin = userUUid.getLast_login();
	            Duration duracao = Duration.between(dataNow, lastLogin);

	            if (duracao.toMinutes() > 30) {
	            	respuesta.setMensagem("Session invalida");
	                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(respuesta);
	            } else {

	                usuarioDto = new UsuarioDto(userUUid.getId(), userUUid.getCreated(),
	                        userUUid.getModified(), userUUid.getLast_login(), userUUid.getToken(), userUUid.getIsactive());
	                return ResponseEntity.ok().body(usuarioDto);
	            }

	        } else {
	            respuesta.setMensagem("No esta autorizado");
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(respuesta);

	        }
	    }
}