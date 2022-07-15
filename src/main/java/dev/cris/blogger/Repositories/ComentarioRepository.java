package dev.cris.blogger.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.cris.blogger.Models.Comentario;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    
    public List<Comentario> findByPublicacionId(Long publicacionId);
}
