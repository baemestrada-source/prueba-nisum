package com.nisum.prueba.model;

import java.io.Serializable;
import java.util.Objects;
public class Phone implements Serializable{
     private static final long serialVersionUID = 1L;

    private String number;
    private String citycode;
    private String contrycode;


    public Phone(String number, String citycode,String contrycode) {
        this.number = number;
        this.citycode = citycode;
        this.contrycode = contrycode;
    }

    public String getNumber() {
        return number;
    }

    public String getCityCode() {
        return citycode;
    }

    public String getContryCode() {
        return contrycode;
    }

    
    @Override
    public String toString() {
        return "Phone{" + "number=" + number + ", citycode=" + citycode + ", contrycode=" + contrycode + "}";
    }

/*    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.number);
        hash = 97 * hash + Objects.hashCode(this.citycode);
        hash = 97 * hash + Objects.hashCode(this.contrycode);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Phone other = (Phone) obj;
        if (!Objects.equals(this.number, other.number)) {
            return false;
        }
        if (!Objects.equals(this.ddd, other.ddd)) {
            return false;
        }
        return true;
    }
    
    */
    
}
