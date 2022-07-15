package dev.cris.blogger.Services;

import java.util.List;

import dev.cris.blogger.DTO.ComentarioDTO;

public interface ComentarioService {
    
    public ComentarioDTO crearComentario(Long id, ComentarioDTO comentarioDTO);

    public List<ComentarioDTO> obtenerComentarioPorPublicacionId(Long id);

    public ComentarioDTO obtenerComentarioPorId(Long publicacionId, Long comentarioId);

    public ComentarioDTO actualizarComentario(Long publicacioId, Long comentarioId, ComentarioDTO comentarioDTO);

    public void eliminarComentario(Long publicacioId, Long comentarioId);
}
