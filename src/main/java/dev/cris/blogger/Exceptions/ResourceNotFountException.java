package dev.cris.blogger.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFountException  extends RuntimeException{
    
    private static final long serialVersionUID = 1L;

    private String nombreRecurso;
    private String nombreCampo;
    private long valorCampo;

    public ResourceNotFountException(String nombreRecurso, String nombreCampo, long valorCampo) {
        super(String.format("%s No encontrado con: %s : '%s'", nombreRecurso, nombreCampo, valorCampo));
        this.nombreRecurso = nombreRecurso;
        this.nombreCampo = nombreCampo;
        this.valorCampo = valorCampo;
    }

    public String getNombreRecurso() {
        return nombreRecurso;
    }

    public void setNombreRecurso(String nombreRecurso) {
        this.nombreRecurso = nombreRecurso;
    }

    public String getNombreCampo() {
        return nombreCampo;
    }

    public void setNombreCampo(String nombreCampo) {
        this.nombreCampo = nombreCampo;
    }

    public long getValorCampo() {
        return valorCampo;
    }

    public void setValorCampo(long valorCampo) {
        this.valorCampo = valorCampo;
    }

    
}