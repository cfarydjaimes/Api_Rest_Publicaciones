package dev.cris.blogger.Controllers;

import java.util.List;

import javax.validation.Valid;

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

import dev.cris.blogger.DTO.ComentarioDTO;
import dev.cris.blogger.Services.ComentarioService;

@RestController
@RequestMapping("/api/")
public class ComentarioController {

    @Autowired
    private ComentarioService comentarioService;

    @PostMapping("/publicaciones/{id}/comentarios")
    public ResponseEntity<ComentarioDTO> guardarComentario(@PathVariable Long id,
            @Valid @RequestBody ComentarioDTO comentarioDTO) {
        return new ResponseEntity<>(comentarioService.crearComentario(id, comentarioDTO), HttpStatus.OK);
    }

    @GetMapping("/publicaciones/{id}/comentarios")
    public List<ComentarioDTO> listarComentariosPorPublicacion(@PathVariable Long id) {
        return comentarioService.obtenerComentarioPorPublicacionId(id);
    }

    @GetMapping("/publicaciones/{publicacionId}/comentarios/{comentarioId}")
    public ResponseEntity<ComentarioDTO> obtenerComentarioPorId(@PathVariable Long publicacionId,
            @PathVariable Long comentarioId) {
        return new ResponseEntity<>(comentarioService.obtenerComentarioPorId(publicacionId, comentarioId),
                HttpStatus.OK);
    }

    @PutMapping("/publicaciones/{publicacionId}/comentarios/{comentarioId}")
    public ResponseEntity<ComentarioDTO> actualizarComentario(@PathVariable Long publicacionId,
            @PathVariable Long comentarioId, @Valid @RequestBody ComentarioDTO comentarioDTO) {
        return new ResponseEntity<>(comentarioService.actualizarComentario(publicacionId, comentarioId, comentarioDTO),
                HttpStatus.OK);
    }

    @DeleteMapping("/publicaciones/{publicacionId}/comentarios/{comentarioId}")
    public ResponseEntity<String> eliminarComentario(@PathVariable Long publicacionId,
            @PathVariable Long comentarioId) {
        comentarioService.eliminarComentario(publicacionId, comentarioId);
        return new ResponseEntity<>("Comentario eliminado exitosamente", HttpStatus.OK);
    }
}
