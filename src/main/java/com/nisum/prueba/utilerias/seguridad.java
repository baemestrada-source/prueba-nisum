package com.nisum.prueba.utilerias;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class seguridad {
    public static String gerarBCrypt(String clave) {
        if (clave == null) {
            return clave;
        }
        BCryptPasswordEncoder bCryptEncoder = new BCryptPasswordEncoder();
        return bCryptEncoder.encode(clave);
    }

    public static boolean claveValida(String clave, String claveEncoded) {
        BCryptPasswordEncoder bCryptEncoder = new BCryptPasswordEncoder();
        return bCryptEncoder.matches(clave, claveEncoded);
    }

}
