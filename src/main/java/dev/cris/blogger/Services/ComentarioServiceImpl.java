package dev.cris.blogger.Services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import dev.cris.blogger.DTO.ComentarioDTO;
import dev.cris.blogger.Exceptions.BlogAppException;
import dev.cris.blogger.Exceptions.ResourceNotFountException;
import dev.cris.blogger.Models.Comentario;
import dev.cris.blogger.Models.Publicacion;
import dev.cris.blogger.Repositories.ComentarioRepository;
import dev.cris.blogger.Repositories.PublicacionRepository;

@Service
public class ComentarioServiceImpl implements ComentarioService {

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private PublicacionRepository publicacionRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ComentarioDTO crearComentario(Long id, ComentarioDTO comentarioDTO) {
        Comentario comentario = modelMapper.map(comentarioDTO, Comentario.class);
        Publicacion publicacion = publicacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFountException("Publicacion", "id", id));
        comentario.setPublicacion(publicacion);
        Comentario comentarioNuevo = comentarioRepository.save(comentario);
        return modelMapper.map(comentarioNuevo, ComentarioDTO.class);
    }

    @Override
    public List<ComentarioDTO> obtenerComentarioPorPublicacionId(Long id) {
        List<Comentario> comentarios = comentarioRepository.findByPublicacionId(id);
        return comentarios.stream().map(comentario -> modelMapper.map(comentario, ComentarioDTO.class)).collect(Collectors.toList());
    }

    @Override
    public ComentarioDTO obtenerComentarioPorId(Long publicacionId, Long comentarioId) {
        Publicacion publicacion = publicacionRepository.findById(publicacionId)
                .orElseThrow(() -> new ResourceNotFountException("publicacion", "id", publicacionId));

        Comentario comentario = comentarioRepository.findById(comentarioId)
                .orElseThrow(() -> new ResourceNotFountException("comentario", "id", comentarioId));

        if (!comentario.getPublicacion().getId().equals(publicacion.getId())) {
            new BlogAppException(HttpStatus.BAD_REQUEST, "El comentario no pertenece a la publicacion");
        }
        return modelMapper.map(comentario, ComentarioDTO.class);
    }

    @Override
    public ComentarioDTO actualizarComentario(Long publicacionId, Long comentarioId, ComentarioDTO comentarioDTO) {
        Publicacion publicacion = publicacionRepository.findById(publicacionId)
                .orElseThrow(() -> new ResourceNotFountException("publicacion", "id", publicacionId));

        Comentario comentario = comentarioRepository.findById(comentarioId)
                .orElseThrow(() -> new ResourceNotFountException("comentario", "id", comentarioId));

        if (!comentario.getPublicacion().getId().equals(publicacion.getId())) {
            new BlogAppException(HttpStatus.BAD_REQUEST, "El comentario no pertenece a la publicacion");
        }
        comentario.setNombre(comentarioDTO.getNombre());
        comentario.setEmail(comentarioDTO.getEmail());
        comentario.setCuerpo(comentarioDTO.getCuerpo());
        comentarioRepository.save(comentario);
        return modelMapper.map(comentario, ComentarioDTO.class);
    }

    @Override
    public void eliminarComentario(Long publicacionId, Long comentarioId) {
        Publicacion publicacion = publicacionRepository.findById(publicacionId)
                .orElseThrow(() -> new ResourceNotFountException("publicacion", "id", publicacionId));

        Comentario comentario = comentarioRepository.findById(comentarioId)
                .orElseThrow(() -> new ResourceNotFountException("comentario", "id", comentarioId));

        if (!comentario.getPublicacion().getId().equals(publicacion.getId())) {
            new BlogAppException(HttpStatus.BAD_REQUEST, "El comentario no pertenece a la publicacion");
        }

        comentarioRepository.deleteById(comentarioId);
    }

}
