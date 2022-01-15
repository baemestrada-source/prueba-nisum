package com.nisum.prueba.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;


/**
 *
 * @author Byron Estrada
 */

public class UsuarioDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private UUID id;
    private LocalDateTime created;
    private LocalDateTime modified;
    private LocalDateTime last_login;
    private String token;
    private boolean isactive;

    public UsuarioDto() {
    }
   public UsuarioDto(UUID id, LocalDateTime created, LocalDateTime modified, LocalDateTime last_login, String token, boolean isactive) {
        this.id = id;
        this.created = created;
        this.modified = modified;
        this.last_login = last_login;
        this.token = token;
        this.isactive = isactive;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getModified() {
        return modified;
    }

    public void setModified(LocalDateTime modified) {
        this.modified = modified;
    }

    public LocalDateTime getLast_login() {
        return last_login;
    }

    public void setLast_login(LocalDateTime last_login) {
        this.last_login = last_login;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean getIsactive() {
        return isactive;
    }

    public void setIsactive(boolean isactive) {
        this.isactive = isactive;
    }
    

    @Override
    public String toString() {
        return "UsuarioDto{" + "id=" + id + ", created=" + created + ", modified=" + modified + ", last_login=" + last_login + ", token=" + token + ", isactive=" + isactive + '}';
    }

}