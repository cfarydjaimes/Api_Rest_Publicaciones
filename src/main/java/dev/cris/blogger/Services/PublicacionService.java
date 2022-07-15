package dev.cris.blogger.Services;


import dev.cris.blogger.DTO.PublicacionDTO;
import dev.cris.blogger.DTO.PublicacionRespuesta;


public interface PublicacionService {

    public PublicacionDTO crearPublicacion(PublicacionDTO publicacionDTO);

    public PublicacionRespuesta obtenerTodasLasPublicaciones(int nPaginas, int mPaginas, String ordenarPor, String sortDir);

    public PublicacionDTO obtenerPublicacionPorId(Long id);
 
    public PublicacionDTO actualizarPublicacion(PublicacionDTO publicacionDTO, Long id);

    public void eliminarPublicacion(Long id);

}
